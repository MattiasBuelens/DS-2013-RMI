package rental;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CompanyRegistryImpl implements CompanyRegistry {

	private Map<String, Company> companies = new HashMap<>();

	@Override
	public Set<Company> getAllCompanies() {
		return new HashSet<>(companies.values());
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
