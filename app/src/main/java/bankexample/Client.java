package bankexample;

import java.rmi.*;

public class Client {
  public static void main(String[] args) {
    try {
      //System.setSecurityManager(new RMISecurityManager());
      System.out.println("Looking up server...");
      AccountManager server = (AccountManager) 
      Naming.lookup("//"+args[0]+"/AccountManager");
      System.out.println("Server bound...");
      String name = "Jack B. Quick";
      Account account = server.open(name);
      float balance = account.balance();
      System.out.println ("The balance in " + name +
			  "'s account is $" + balance);
    } catch (Exception e) {
      e.printStackTrace();
    } 
  } 
}
