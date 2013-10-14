package rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import rental.impl.CarRentalCompany;

public class RentalServer {

	public static void main(String[] args) throws ReservationException,
			NumberFormatException, IOException {
		// TODO Use an RMISecurityManager for the next session
		System.setSecurityManager(null);

		String host = (args.length < 1) ? null : args[0];

		// Create company
		List<Car> cars = loadData("hertz.csv");
		CarRentalCompany company = new CarRentalCompany("Hertz", cars);

		// Stub company
		rental.CarRentalCompany stub = (rental.CarRentalCompany) UnicastRemoteObject
				.exportObject(company, 0);

		try {
			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(host);
			registry.bind(company.getName(), stub);
		} catch (AlreadyBoundException e) {
			System.err.println("Already bound");
			System.exit(-1);
		}
		System.out.println("Server ready");
	}

	public static List<Car> loadData(String datafile)
			throws ReservationException, NumberFormatException, IOException {

		List<Car> cars = new LinkedList<Car>();

		int nextuid = 0;

		// open file
		BufferedReader in = new BufferedReader(new FileReader(datafile));
		try {
			// while next line exists
			while (in.ready()) {
				// read line
				String line = in.readLine();
				// if comment: skip
				if (line.startsWith("#"))
					continue;
				// tokenize on ,
				StringTokenizer csvReader = new StringTokenizer(line, ",");
				// create new car type from first 5 fields
				CarType type = new CarType(csvReader.nextToken(),
						Integer.parseInt(csvReader.nextToken()),
						Float.parseFloat(csvReader.nextToken()),
						Double.parseDouble(csvReader.nextToken()),
						Boolean.parseBoolean(csvReader.nextToken()));
				System.out.println(type);
				// create N new cars with given type, where N is the 5th field
				for (int i = Integer.parseInt(csvReader.nextToken()); i > 0; i--) {
					cars.add(new Car(nextuid++, type));
				}
			}
		} finally {
			in.close();
		}

		return cars;
	}
}