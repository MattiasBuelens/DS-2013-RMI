package rental;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Use an RMISecurityManager for the next session
		System.setSecurityManager(null);

		// Registry host
		String host = (args.length < 1) ? null : args[0];
		Registry registry = LocateRegistry.getRegistry(host);

		// Create company registry
		CompanyRegistryImpl companyRegistry = new CompanyRegistryImpl();

		// Bind stub to registry
		CompanyRegistry stub = (CompanyRegistry) UnicastRemoteObject
				.exportObject(companyRegistry, 0);
		try {
			registry.bind(CompanyRegistry.class.getSimpleName(), stub);
		} catch (AlreadyBoundException e) {
			System.err.println("Company registry already bound");
			System.exit(-1);
		}

		System.out.println("Company registry ready");
	}

}
