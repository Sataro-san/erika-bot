package kg.shsatarov.erikabot.schedulers;

import kg.shsatarov.erikabot.entities.ActivityBalanceRate;
import kg.shsatarov.erikabot.entities.UserBalance;
import kg.shsatarov.erikabot.services.ActivityBalanceRateService;
import kg.shsatarov.erikabot.services.UserBalanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
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

            Guild guild = jdaInstance.getGuildById(guildId);
            log.info("Guild {} {} [START] rewarding members...", guild.getId(), guild.getName());
            rewardGuildMembers(guild, guildUserBalances.get(guildId));
            log.info("Guild {} {} [END] rewarding members...", guild.getId(), guild.getName());

        }

        log.info("End of ActivityReward");

    }

    private void rewardGuildMembers(Guild guild, List<UserBalance> userBalances) {



        for (UserBalance userBalance : userBalances) {
            Member member = guild.getMemberById(userBalance.getDiscordUserId());

            //reward only if online and in voice channel
            if ((OnlineStatus.ONLINE.equals(member.getOnlineStatus()) || OnlineStatus.DO_NOT_DISTURB.equals(member.getOnlineStatus()))
                    && member.getVoiceState().inAudioChannel()) {

                AudioChannelUnion memberVoiceChannel = member.getVoiceState().getChannel();
                if (memberVoiceChannel.getMembers().size() > 1) {
                    rewardGuildMember(member);
                } else {
                    log.info("User {} {} : voice channel members count < 2", member.getId(), member.getUser().getName());
                }

            } else {
                log.info("User {} {} is not ONLINE or not in voice channel", member.getId(), member.getUser().getName());
            }

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


        if (memberActivity.asRichPresence() == null) {
            log.warn("User {} {} - couldn't get memberActivity.asRichPresence() is null", member.getId(), member.getUser().getName());
            return;
        }

        Optional<ActivityBalanceRate> activityBalanceRateOptional = activityBalanceRateService.getByApplicationId(memberActivity.asRichPresence().getApplicationId());

        if (activityBalanceRateOptional.isEmpty()) {
            log.info("User {} {} : current activity {} with id {} is not supported for BalanceReward", member.getId(), member.getUser().getName(), memberActivity.getName(), memberActivity.asRichPresence().getApplicationId());
            return;
        }

        ActivityBalanceRate activityBalanceRate = activityBalanceRateOptional.get();


        try {
            userBalanceService.addBalanceToUser(member, activityBalanceRate.getRate());
            log.info("User {} {} : successfully issued {} KBS for activity {}", member.getId(), member.getUser().getName(), activityBalanceRate.getRate(), memberActivity.getName());

        } catch (Exception e) {
            log.warn("User {} {} : issuance error {} KBS for activity {}, error: {}", member.getId(), member.getUser().getName(), activityBalanceRate.getRate(), memberActivity.getName(), e.getMessage());

        }


    }

}
