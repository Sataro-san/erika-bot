package kg.shsatarov.erikabot.schedulers;

import kg.shsatarov.erikabot.entities.ActivityBalanceRate;
import kg.shsatarov.erikabot.entities.UserBalance;
import kg.shsatarov.erikabot.services.ActivityBalanceRateService;
import kg.shsatarov.erikabot.services.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityBalanceScheduler {
    private final UserBalanceService userBalanceService;
    private final ActivityBalanceRateService activityBalanceRateService;
    private final JDA jdaInstance;

    @Scheduled(cron = "*/30 * * * * *")
    public void rewardActivity() {

        log.info("Starting ActivityReward");


        Map<String, List<UserBalance>> guildUserBalances = userBalanceService.getAll().stream()
                .collect(Collectors.groupingBy(UserBalance::getDiscordGuildId));

        for (String guildId : guildUserBalances.keySet()) {
            rewardGuildMembers(guildId, guildUserBalances.get(guildId));
        }

        log.info("End of ActivityReward");

    }

    private void rewardGuildMembers(String guildId, List<UserBalance> userBalances) {

        Guild guild = jdaInstance.getGuildById(guildId);

        for (UserBalance userBalance : userBalances) {
            Member member = guild.getMemberById(userBalance.getDiscordUserId());
            rewardGuildMember(member);
        }

    }

    private void rewardGuildMember(Member member) {

        Optional<Activity> memberActivityOptional = member.getActivities().stream()
                .filter(currentActivity -> Activity.ActivityType.PLAYING.equals(currentActivity.getType()))
                .findFirst();

        if (memberActivityOptional.isEmpty()) {
            log.info("no PLAYING activities for user {} with id {}", member.getUser().getName(), member.getId());
            return;
        }

        Activity memberActivity = memberActivityOptional.get();

        Optional<ActivityBalanceRate> activityBalanceRateOptional = activityBalanceRateService.getByApplicationId(memberActivity.asRichPresence().getApplicationId());

        if (activityBalanceRateOptional.isEmpty()) {
            log.info("User {} {} : current activity {} with id {} is not supported for BalanceReward", member.getId(), member.getUser().getName(), memberActivity.getName(), memberActivity.asRichPresence().getApplicationId());
            return;
        }

        ActivityBalanceRate activityBalanceRate = activityBalanceRateOptional.get();

        userBalanceService.addBalanceToUser(member.getId(), activityBalanceRate.getRate());
        log.info("User {} {} : successfully issued {} KBS for activity {}", member.getId(), member.getUser().getName(), activityBalanceRate.getRate(), memberActivity.getName());

    }

}
