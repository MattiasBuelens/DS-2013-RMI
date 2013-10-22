package rental.session;

import rental.CompanyRegistry;

public abstract class Session {

	private final CompanyRegistry registry;
	private final String clientName;

	public Session(CompanyRegistry registry, String clientName) {
		this.registry = registry;
		this.clientName = clientName;
	}

	protected CompanyRegistry getRegistry() {
		return registry;
	}

	protected String getClientName() {
		return clientName;
	}

}
