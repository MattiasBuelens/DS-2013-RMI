package rental.session;

import java.rmi.RemoteException;
import java.util.Map;
import java.util.Set;

import rental.CarType;
import rental.Company;
import rental.CompanyRegistry;

public class ManagerSessionImpl extends Session implements ManagerSession {

	public ManagerSessionImpl(CompanyRegistry registry, String managerName) {
		super(registry, managerName);
	}

	@Override
	public Set<String> getAllCompanies() throws RemoteException {
		return getRegistry().getAllCompanies().keySet();
	}

	@Override
	public void registerCompany(String companyName, Company company)
			throws RemoteException {
		getRegistry().registerCompany(companyName, company);
	}

	@Override
	public void unregisterCompany(String companyName) throws RemoteException {
		getRegistry().unregisterCompany(companyName);
	}

	@Override
	public int getNumberOfReservationsBy(String clientName)
			throws RemoteException {
		int nbReservations = 0;
		for (Company company : getRegistry().getAllCompanies().values()) {
			nbReservations += company.getNumberOfReservationsBy(clientName);
		}
		return nbReservations;
	}

	@Override
	public int getNumberOfReservationsForCarType(String carRentalCompanyName,
			String carType) throws RemoteException {
		Company company = getRegistry().getCompany(carRentalCompanyName);
		return company.getNumberOfReservationsForCarType(carType);
	}

	@Override
	public String getMostPopularCarRentalCompany() throws RemoteException {
		String mostPopular = null;
		int maxReservations = Integer.MIN_VALUE;
		for (Map.Entry<String, Company> entry : getRegistry().getAllCompanies()
				.entrySet()) {
			String companyName = entry.getKey();
			int nbReservations = entry.getValue().getNumberOfReservations();
			if (nbReservations > maxReservations) {
				mostPopular = companyName;
				maxReservations = nbReservations;
			}
		}
		return mostPopular;
	}

	@Override
	public CarType getMostPopularCarTypeIn(String carRentalCompanyName)
			throws RemoteException {
		CarType mostPopular = null;
		int maxReservations = Integer.MIN_VALUE;
		Company company = getRegistry().getCompany(carRentalCompanyName);
		for (CarType carType : company.getAllCarTypes()) {
			int nbReservations = company
					.getNumberOfReservationsForCarType(carType.getName());
			if (nbReservations > maxReservations) {
				mostPopular = carType;
				maxReservations = nbReservations;
			}
		}
		return mostPopular;
	}

}
