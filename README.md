# bank_example_rmi
This piece of code implements a toy system organized in three cooperating executable components, AccountFactory, AccountManager and Client. A Client interacts with an AccountManager to open a back account. In turn, the AccountManager relies on a set of AccountFactory components to instantiate an Account for the client.  The Client then can interact directly with the instantiated Account to receive the account balance. 
Account, AccountFactory and AccountManager instances are all remote objects that can be reached through RMI (Remote Method Invocation). 
Account and AccountFactory instances run within the same process, while AccountManager and Client instances run on different processes. 
 
