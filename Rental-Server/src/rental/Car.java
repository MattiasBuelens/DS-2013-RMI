package rental;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import rental.CarType;
import rental.Reservation;

public class Car {

	private int id;
	private CarType type;
	private List<Reservation> reservations;

	/***************
	 * CONSTRUCTOR *
	 ***************/

	public Car(int uid, CarType type) {
		this.id = uid;
		this.type = type;
		this.reservations = new ArrayList<Reservation>();
	}

	/******
	 * ID *
	 ******/

	public int getId() {
		return id;
	}

	/************
	 * CAR TYPE *
	 ************/

	public CarType getType() {
		return type;
	}

	/****************
	 * RESERVATIONS
	 ****************/

	public boolean isAvailable(Date start, Date end) throws RemoteException {
		if (!start.before(end))
			throw new IllegalArgumentException("Illegal given period");

		for (Reservation reservation : reservations) {
			if (reservation.getEndDate().before(start)
					|| reservation.getStartDate().after(end))
				continue;
			return false;
		}
		return true;
	}

	public List<Reservation> getReservations() {
		return Collections.unmodifiableList(reservations);
	}

	public List<Reservation> getReservationsBy(String client)
			throws RemoteException {
		List<Reservation> clientReservations = new ArrayList<>();
		for (Reservation reservation : getReservations()) {
			if (reservation.getCarRenter().equals(client)) {
				clientReservations.add(reservation);
			}
		}
		return clientReservations;
	}

	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}

	public void removeReservation(Reservation reservation) {
		// equals-method for Reservation is required!
		reservations.remove(reservation);
	}
}