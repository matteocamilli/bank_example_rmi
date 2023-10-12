package bankexample;

import java.rmi.*;

public interface AccountFactory extends Remote {
  public Account createAccount(String userName, int balance) 
       throws RemoteException;
  public int getLoad() throws RemoteException;
}
