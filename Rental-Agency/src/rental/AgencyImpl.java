package rental;

import java.rmi.RemoteException;
import java.util.Collection;

import rental.session.ManagerSession;
import rental.session.ManagerSessionImpl;
import rental.session.ReservationSession;
import rental.session.ReservationSessionImpl;

public class AgencyImpl implements Agency {

	private final CompanyRegistry companyRegistry;

	public AgencyImpl(CompanyRegistry companyRegistry) {
		this.companyRegistry = companyRegistry;
	}

	protected CompanyRegistry getCompanyRegistry() {
		return companyRegistry;
	}

	@Override
	public ReservationSession createReservationSession(String clientName) {
		return new ReservationSessionImpl(this, clientName);
	}

	@Override
	public ManagerSession createManagerSession(String managerName) {
		return new ManagerSessionImpl(this, managerName);
	}

	@Override
	public Collection<Company> getAllCompanies() throws RemoteException {
		return companyRegistry.getAllCompanies();
	}

	@Override
	public Company getCompany(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerCompany(String name, Company company) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterCompany(String name) {
		// TODO Auto-generated method stub
		
	}

}
