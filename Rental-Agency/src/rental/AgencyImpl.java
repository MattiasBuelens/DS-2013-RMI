package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

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
	public ReservationSession createReservationSession(String clientName)
			throws RemoteException {
		ReservationSession session = new ReservationSessionImpl(this,
				clientName);
		return (ReservationSession) UnicastRemoteObject
				.exportObject(session, 0);
	}

	@Override
	public ManagerSession createManagerSession(String managerName)
			throws RemoteException {
		ManagerSession ms = new ManagerSessionImpl(this, managerName);
		return (ManagerSession) UnicastRemoteObject.exportObject(ms, 0);
	}

	@Override
	public Set<Company> getAllCompanies() throws RemoteException {
		return companyRegistry.getAllCompanies();
	}

	@Override
	public Company getCompany(String name) throws RemoteException {
		return companyRegistry.getCompany(name);
	}

	@Override
	public void registerCompany(String name, Company company)
			throws RemoteException {
		companyRegistry.registerCompany(name, company);
	}

	@Override
	public void unregisterCompany(String name) throws RemoteException {
		companyRegistry.unregisterCompany(name);
	}

}
