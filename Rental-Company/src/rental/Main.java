package rental;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import rental.session.ManagerSession;

public class Main {

	public static void main(String[] args) throws Exception {
		System.setSecurityManager(null);

		// Company name
		String companyName = (args.length < 1) ? "Hertz" : args[0];
		// Registry host
		String host = (args.length < 2) ? null : args[1];
		Registry registry = LocateRegistry.getRegistry(host);

		// Create company
		List<Car> cars = loadData(companyName.toLowerCase() + ".csv");
		CompanyImpl company = new CompanyImpl(companyName, cars);

		// Lookup agency
		Agency agency = null;
		try {
			agency = (Agency) registry.lookup(Agency.class.getSimpleName());
		} catch (NotBoundException e) {
			System.err.println("Agency not bound");
			System.exit(-1);
		}

		// Bind stub to agency
		Company stub = (Company) UnicastRemoteObject.exportObject(company, 0);
		ManagerSession ms = agency.createManagerSession(companyName);
		ms.registerCompany(companyName, stub);

		System.out.println("Company server " + companyName + " ready");
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