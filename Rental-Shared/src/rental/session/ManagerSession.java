package rental.session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import rental.CarType;
import rental.Company;

public interface ManagerSession extends Remote {

	public Set<Company> getAllCompanies() throws RemoteException;

	public void registerCompany(String companyName, Company company)
			throws RemoteException;

	public void unregisterCompany(String companyName) throws RemoteException;

	public int getNumberOfReservationsBy(String clientName)
			throws RemoteException;

	public int getNumberOfReservationsForCarType(String carRentalCompanyName,
			String carType) throws RemoteException;

	public String getMostPopularCarRentalCompany() throws RemoteException;

	public CarType getMostPopularCarTypeIn(String carRentalCompanyName)
			throws RemoteException;

}
