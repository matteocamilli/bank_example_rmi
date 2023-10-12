package bankexample;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class AccountImpl extends
        UnicastRemoteObject implements Account {
    private float _balance;

    public AccountImpl(float balance) throws RemoteException {
        _balance = balance;
    }

    public float balance() throws RemoteException {
        return _balance;
    }
}
