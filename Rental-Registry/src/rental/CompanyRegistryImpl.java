package rental;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CompanyRegistryImpl implements CompanyRegistry {

	private Map<String, Company> companies = new HashMap<>();

	@Override
	public Collection<Company> getAllCompanies() {
		return new HashSet<Company>(companies.values());
	}

	@Override
	public Company getCompany(String companyName) {
		return companies.get(companyName);
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
