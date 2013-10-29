package rental.session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import rental.CarType;
import rental.Quote;
import rental.Reservation;
import rental.ReservationConstraints;
import rental.ReservationException;

public interface ReservationSession extends Remote {

	public String getRenterName() throws RemoteException;

	public Set<CarType> getAvailableCarTypes(Date start, Date end)
			throws RemoteException;

	public Set<Quote> getCurrentQuotes() throws RemoteException;

	public Quote addQuote(ReservationConstraints constraints,
			String carRentalName) throws RemoteException, ReservationException;

	public List<Reservation> confirmQuotes() throws RemoteException,
			ReservationException;

}
