package rental;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface CompanyRegistry extends Remote {

	public Map<String, Company> getAllCompanies() throws RemoteException;

	public Company getCompany(String companyName) throws RemoteException;

	public void registerCompany(String companyName, Company company)
			throws RemoteException;

	public void unregisterCompany(String companyName) throws RemoteException;

}
