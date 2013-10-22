package rental;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CompanyRegistryImpl implements CompanyRegistry {

	private Map<String, Company> companies = new HashMap<>();

	@Override
	public Map<String, Company> getAllCompanies() {
		return Collections.unmodifiableMap(companies);
	}

	@Override
	public Company getCompany(String companyName) {
		return getAllCompanies().get(companyName);
	}

	@Override
	public void registerCompany(String companyName, Company company) {
		companies.put(companyName, company);
	}

	@Override
	public void unregisterCompany(String companyName) {
		companies.remove(companyName);
	}

}
