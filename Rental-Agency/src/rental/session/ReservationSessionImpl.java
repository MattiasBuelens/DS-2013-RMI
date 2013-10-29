package rental.session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rental.Agency;
import rental.CarType;
import rental.Company;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSessionImpl extends Session implements
		ReservationSession {

	private final Set<Quote> quotes = new HashSet<>();

	public ReservationSessionImpl(Agency agency, String clientName) {
		super(agency, clientName);
	}

	@Override
	public String getRenterName() throws RemoteException {
		return getClientName();
	}

	@Override
	public Set<CarType> getAvailableCarTypes(Date start, Date end)
			throws RemoteException {
		Set<CarType> availableTypes = new HashSet<CarType>();
		for (Company company : getAgency().getAllCompanies()) {
			availableTypes.addAll(company.getAvailableCarTypes(start, end));
		}
		return availableTypes;
	}

	@Override
	public Set<Quote> getCurrentQuotes() {
		return Collections.unmodifiableSet(quotes);
	}

	@Override
	public Quote addQuote(ReservationConstraints constraints,
			String carRentalName) throws RemoteException, ReservationException {
		Company company = getAgency().getCompany(carRentalName);
		Quote quote = company.createQuote(constraints, getClientName());
		quotes.add(quote);
		return quote;
	}

	@Override
	public List<Reservation> confirmQuotes() throws RemoteException,
			ReservationException {
		List<Reservation> reservations = new ArrayList<>();
		try {
			// Place reservations
			for (Quote quote : getCurrentQuotes()) {
				Company company = getAgency().getCompany(
						quote.getRentalCompany());
				Reservation reservation = company.confirmQuote(quote);
				reservations.add(reservation);
			}
		} catch (ReservationException e) {
			// Roll back
			for (Reservation reservation : reservations) {
				Company company = getAgency().getCompany(
						reservation.getRentalCompany());
				company.cancelReservation(reservation);
			}
			// Re-throw
			throw e;
		}
		return reservations;
	}

}
