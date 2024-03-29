package kg.shsatarov.erikabot.services.activities;

import kg.shsatarov.erikabot.entities.activities.ActivityBalanceRate;

import java.util.List;
import java.util.Optional;

public interface ActivityBalanceRateService {

    ActivityBalanceRate saveActivityBalanceRate(ActivityBalanceRate activityBalanceRate);

    List<ActivityBalanceRate> getAllByDiscordGuildId(String discordGuildId);

    Optional<ActivityBalanceRate> getByApplicationIdAndGuildId(String applicationId, String guildId);

    void reloadCache();

}
