package rental.session;

import rental.Agency;

public abstract class Session {

	private final Agency agency;
	private final String clientName;

	public Session(Agency agency, String clientName) {
		this.agency = agency;
		this.clientName = clientName;
	}

	protected Agency getAgency() {
		return agency;
	}

	protected String getClientName() {
		return clientName;
	}

}
