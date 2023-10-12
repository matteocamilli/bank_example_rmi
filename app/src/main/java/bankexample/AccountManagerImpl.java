package bankexample;

import java.util.*;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class AccountManagerImpl extends
       UnicastRemoteObject implements AccountManager {

  private Dictionary _accounts = new Hashtable();
  private String hostName;
  private Vector factoryList;
 
  public AccountManagerImpl(String _hostName) throws RemoteException {
    hostName = _hostName;
    factoryList = new Vector();
  }

  public void addFactory(String hostName) {
    try {
      AccountFactory fct = (AccountFactory) 
	Naming.lookup("//"+hostName+"/AccountFactory");
      factoryList.addElement(fct);
    } catch (Exception e) {
      System.out.println("Error: Factory unreachable");
      e.printStackTrace();
    }
  }

  public AccountFactory selectFactory() {
    int minLoad, load;
    int index=0;
    AccountFactory f;
    try {
      Enumeration e = factoryList.elements(); 
      f = (AccountFactory) e.nextElement();
      minLoad = f.getLoad();
      index=factoryList.indexOf(f);
      while(e.hasMoreElements()) {
	f = (AccountFactory) e.nextElement();
	load = f.getLoad();
	if(minLoad > load)
	  {
	    minLoad = load;
	    index=factoryList.indexOf(f);
	  }
      }
    } catch (Exception e) {
      	e.printStackTrace();
    }
    return (AccountFactory) factoryList.elementAt(index);
  }
 
  public Account open(String name) throws RemoteException {
    AccountFactory factory;
    /* ...Security checks... */
    Account account = (Account) _accounts.get(name);
    if(account == null) {
      factory = selectFactory();
      try {
	account = factory.createAccount(name, 0);
	_accounts.put(name, account);
      } catch (Exception e) {
	e.printStackTrace();
      }
    }
    return account;
  }

    
  public static void main(String[] args) {
    String _hostName;
    try {
      //System.setSecurityManager(new RMISecurityManager());
      _hostName = InetAddress.getLocalHost().getHostName(); 
      AccountManagerImpl server = new AccountManagerImpl(_hostName); 
      server.addFactory(args[0]);
      Naming.rebind("//"+_hostName+"/AccountManager", server);
      System.out.println("Server bound on "+_hostName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
