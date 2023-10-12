package bankexample;

import java.rmi.*;

public interface AccountManager extends Remote {
    public Account open(String name) throws RemoteException;
}
