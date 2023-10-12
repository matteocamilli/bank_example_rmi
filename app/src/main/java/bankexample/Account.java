package bankexample;

import java.rmi.*;

public interface Account extends Remote {
    public float balance() throws RemoteException;
}
