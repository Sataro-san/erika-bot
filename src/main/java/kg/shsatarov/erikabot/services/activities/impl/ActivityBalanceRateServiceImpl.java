package kg.shsatarov.erikabot.services.activities.impl;

import jakarta.annotation.PostConstruct;
import kg.shsatarov.erikabot.entities.activities.ActivityBalanceRate;
import kg.shsatarov.erikabot.repositories.activities.ActivityBalanceRateRepository;
import kg.shsatarov.erikabot.services.activities.ActivityBalanceRateService;
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
    public ActivityBalanceRate saveActivityBalanceRate(ActivityBalanceRate activityBalanceRate) {
        return activityBalanceRateRepository.save(activityBalanceRate);
    }

    @Override
    public List<ActivityBalanceRate> getAllByDiscordGuildId(String discordGuildId) {
        return activityBalanceRates.stream()
                .filter(activityRate -> activityRate.getDiscordGuildId().equals(discordGuildId))
                .toList();
    }

    @Override
    public Optional<ActivityBalanceRate> getByApplicationIdAndGuildId(String applicationId, String guildId) {
        return activityBalanceRates.stream()
                .filter(activityBalanceRate -> activityBalanceRate.getActivityDictionary().getDiscordApplicationId().equals(applicationId) && activityBalanceRate.getDiscordGuildId().equals(guildId))
                .findAny();
    }

    @Override
    public void reloadCache() {
        activityBalanceRates = activityBalanceRateRepository.findAll();
    }
}
