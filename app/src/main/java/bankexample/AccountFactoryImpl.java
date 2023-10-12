package bankexample;

import java.rmi.registry.LocateRegistry;
import java.util.*;
import java.net.InetAddress;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class AccountFactoryImpl extends
       UnicastRemoteObject implements AccountFactory {

    public static  java.rmi.registry.Registry REGISTRY = null;
    private String hostName;
    private int load;

    public AccountFactoryImpl(String _hostName) throws RemoteException {
        hostName = _hostName;
        load = 0;
    }

    public Account createAccount(String userName, int balance)
           throws RemoteException {
        Account account = new AccountImpl(balance);
        try {
            Naming.rebind("//"+hostName+"/Account"+userName, account);
            System.out.println("Created " + userName +
                      "'s account: " + account);
        } catch (Exception e) {
            e.printStackTrace();
        }
        load++;
        return account;
    }
    public int getLoad() throws RemoteException {
    return load;
  }

  public static void main(String[] args) {
    String _hostName;
    try {
      //System.setSecurityManager(new RMISecurityManager());
        REGISTRY = LocateRegistry.createRegistry(1099);
        _hostName = InetAddress.getLocalHost().getHostName();
        AccountFactoryImpl server = new AccountFactoryImpl(_hostName);
        Naming.rebind("//"+_hostName+"/AccountFactory", server);
        System.out.println("Factory bound on "+_hostName);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
