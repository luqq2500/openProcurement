package address.spi;

import address.model.AddressRule;

import java.util.List;

public interface AddressCountryRuleRepository {
    public List<AddressRule> addressCountryRules();
}
