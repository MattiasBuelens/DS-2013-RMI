package rental.session;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.Company;
import rental.CompanyRegistry;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public class ReservationSessionImpl extends Session implements
		ReservationSession {

	private final Set<Quote> quotes = new HashSet<>();

	public ReservationSessionImpl(CompanyRegistry registry, String clientName) {
		super(registry, clientName);
	}

	@Override
	public String getRenterName() throws RemoteException {
		return getClientName();
	}

	@Override
	public Set<CarType> getAvailableCarTypes(Date start, Date end)
			throws RemoteException {
		Set<CarType> availableTypes = new HashSet<CarType>();
		for (Company company : getRegistry().getAllCompanies().values()) {
			availableTypes.addAll(company.getAvailableCarTypes(start, end));
		}
		return availableTypes;
	}

	@Override
	public Set<Quote> getCurrentQuotes() {
		return Collections.unmodifiableSet(quotes);
	}

	@Override
	public Quote addQuote(Date start, Date end, String carType,
			String carRentalName) throws RemoteException, ReservationException {
		Company company = getRegistry().getCompany(carRentalName);
		ReservationConstraints constraints = new ReservationConstraints(start,
				end, carType);
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
				Company company = getRegistry().getCompany(
						quote.getRentalCompany());
				Reservation reservation = company.confirmQuote(quote);
				reservations.add(reservation);
			}
		} catch (ReservationException e) {
			// Roll back
			for (Reservation reservation : reservations) {
				Company company = getRegistry().getCompany(
						reservation.getRentalCompany());
				company.cancelReservation(reservation);
			}
			// Re-throw
			throw e;
		}
		return reservations;
	}

}
