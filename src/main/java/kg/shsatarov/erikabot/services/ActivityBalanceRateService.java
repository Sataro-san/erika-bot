package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.entities.ActivityBalanceRate;

import java.util.List;
import java.util.Optional;

public interface ActivityBalanceRateService {

    List<ActivityBalanceRate> getAll();

    Optional<ActivityBalanceRate> getByApplicationId(String applicationId);

    void reloadCache();

}
