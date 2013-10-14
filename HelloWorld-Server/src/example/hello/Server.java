package example.hello;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Hello {

	public String sayHello() {
		return "Hello, world!";
	}

	public static void main(String args[]) {
		try {
			Server obj = new Server();
			Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			// Registry registry = LocateRegistry.getRegistry();
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.bind("Hello", stub);

			System.out.println("Server ready");
		} catch (AlreadyBoundException e) {
			System.err.println("Already bound");
			System.exit(-1);
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
			System.exit(-1);
		}
	}

}