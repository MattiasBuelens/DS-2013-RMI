package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

import rental.session.ManagerSession;
import rental.session.ReservationSession;

public interface Agency extends Remote {

	public ReservationSession createReservationSession(String clientName)
			throws RemoteException;

	public ManagerSession createManagerSession(String managerName) throws RemoteException;

	public Collection<Company> getAllCompanies() throws RemoteException ;
	
	public Company getCompany(String name) throws RemoteException ;
	
	public void registerCompany(String name, Company company) throws RemoteException ;
	
	public void unregisterCompany(String name) throws RemoteException ;
	
}
