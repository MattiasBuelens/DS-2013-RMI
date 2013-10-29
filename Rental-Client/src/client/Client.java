package client;

import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.Agency;
import rental.CarType;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.session.ManagerSession;
import rental.session.ReservationSession;

public class Client extends
		AbstractScriptedTripTest<ReservationSession, ManagerSession> {

	private final Agency agency;

	/********
	 * MAIN *
	 ********/

	public static void main(String[] args) throws Exception {
		System.setSecurityManager(null);

		// Script file
		String scriptFile = (args.length < 1) ? "trips" : args[0];
		// Registry host
		String host = (args.length < 1) ? null : args[1];
		Registry registry = LocateRegistry.getRegistry(host);

		// Lookup agency
		Agency agency = null;
		try {
			agency = (Agency) registry.lookup(Agency.class.getSimpleName());
		} catch (NotBoundException e) {
			System.err.println("Agency not bound");
			System.exit(-1);
		}

		// Run client
		try {
			Client client = new Client(scriptFile, agency);
			client.run();
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/

	public Client(String scriptFile, Agency agency) {
		super(scriptFile);
		this.agency = agency;
	}

	@Override
	protected ReservationSession getNewReservationSession(String name)
			throws Exception {
		return agency.createReservationSession(name);
	}

	@Override
	protected ManagerSession getNewManagerSession(String name) throws Exception {
		return agency.createManagerSession(name);
	}

	@Override
	protected void checkForAvailableCarTypes(ReservationSession session,
			Date start, Date end) throws Exception {
		Set<CarType> carTypes = session.getAvailableCarTypes(start, end);
		System.out.println("Available car types between " + start + " and "
				+ end + ":");
		for (CarType carType : carTypes) {
			System.out.println("* " + carType);
		}
	}

	@Override
	protected void addQuoteToSession(ReservationSession session, Date start,
			Date end, String carType, String carRentalName) throws Exception {
		ReservationConstraints constraints = new ReservationConstraints(start,
				end, carType);
		session.addQuote(constraints, carRentalName);
	}

	@Override
	protected List<Reservation> confirmQuotes(ReservationSession session)
			throws Exception {
		return session.confirmQuotes();
	}

	@Override
	protected int getNumberOfReservationsBy(ManagerSession ms, String clientName)
			throws Exception {
		return ms.getNumberOfReservationsBy(clientName);
	}

	@Override
	protected int getNumberOfReservationsForCarType(ManagerSession ms,
			String carRentalCompanyName, String carType) throws Exception {
		return ms.getNumberOfReservationsForCarType(carRentalCompanyName,
				carType);
	}

	@Override
	protected String getMostPopularCarRentalCompany(ManagerSession ms)
			throws Exception {
		return ms.getMostPopularCarRentalCompany();
	}

	@Override
	protected CarType getMostPopularCarTypeIn(ManagerSession ms,
			String carRentalCompanyName) throws Exception {
		return ms.getMostPopularCarTypeIn(carRentalCompanyName);
	}

}