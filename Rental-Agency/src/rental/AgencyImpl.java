package rental;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
