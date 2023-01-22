package kg.shsatarov.erikabot.services.impl;

import jakarta.annotation.PostConstruct;
import kg.shsatarov.erikabot.entities.ActivityBalanceRate;
import kg.shsatarov.erikabot.repositories.ActivityBalanceRateRepository;
import kg.shsatarov.erikabot.services.ActivityBalanceRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityBalanceRateServiceImpl implements ActivityBalanceRateService {

    private final ActivityBalanceRateRepository activityBalanceRateRepository;

    private List<ActivityBalanceRate> activityBalanceRates;

    @PostConstruct
    private void initialize() {
        reloadCache();
    }

    @Override
    public List<ActivityBalanceRate> getAll() {
        return activityBalanceRates;
    }

    @Override
    public Optional<ActivityBalanceRate> getByApplicationId(String applicationId) {
        return activityBalanceRates.stream()
                .filter(activityBalanceRate -> activityBalanceRate.getDiscordApplicationId().equals(applicationId))
                .findAny();
    }

    @Override
    public void reloadCache() {
        activityBalanceRates = activityBalanceRateRepository.findAll();
    }
}
