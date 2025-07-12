package administrator.spi;

import java.util.HashMap;
import java.util.List;

public interface AdministratorRoleRuleRepository {
    HashMap<String, List<String>> administratorRoleRules();
}
