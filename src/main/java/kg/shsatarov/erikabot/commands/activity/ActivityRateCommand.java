package kg.shsatarov.erikabot.commands.activity;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.activities.ActivityBalanceRate;
import kg.shsatarov.erikabot.entities.activities.ActivityDictionary;
import kg.shsatarov.erikabot.services.activities.ActivityBalanceRateService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityRateCommand implements ExecutableCommand {

    private final ActivityBalanceRateService activityBalanceRateService;

    @Override
    public String getName() {
        return "activity-rates";
    }

    @Override
    public String getDescription() {
        return "Тарификация по Активностям";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        List<ActivityBalanceRate> activityRates = activityBalanceRateService.getAllByDiscordGuildId(slashCommandEvent.getGuild().getId());

        StringBuilder activityRatesStringBuilder = new StringBuilder();


        for (ActivityBalanceRate activityRate : activityRates) {

            ActivityDictionary activityDictionary = activityRate.getActivityDictionary();
            activityRatesStringBuilder.append(StringFormatter.format("**{}** : ***{}*** :dollar:\n", activityDictionary.getApplicationName(), activityRate.getRate()));
        }

        slashCommandEvent
                .reply(StringFormatter.format("{}\n:video_game: Начисления по Активностям:\n{}\n*Валюта начисляется каждую Минуту*", slashCommandEvent.getMember().getAsMention(), activityRatesStringBuilder.toString()))
                .queue();
    }
}
