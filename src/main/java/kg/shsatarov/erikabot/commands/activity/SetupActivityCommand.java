package kg.shsatarov.erikabot.commands.activity;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.activities.ActivityBalanceRate;
import kg.shsatarov.erikabot.entities.activities.ActivityDictionary;
import kg.shsatarov.erikabot.exceptions.DiscordBotException;
import kg.shsatarov.erikabot.services.activities.ActivityBalanceRateService;
import kg.shsatarov.erikabot.services.activities.ActivityDictionaryService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupActivityCommand implements ExecutableCommand {

    private String applicationIdOption = "application-id";
    private String rateOption = "rate";

    private final ActivityBalanceRateService activityBalanceRateService;
    private final ActivityDictionaryService activityDictionaryService;

    @Override
    public String getName() {
        return "setup-activity";
    }

    @Override
    public String getDescription() {
        return "Создать начисление по активности";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        String applicationId = Optional.ofNullable(slashCommandEvent.getOption(applicationIdOption))
                .map(OptionMapping::getAsString)
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "Не задано значение для опции {}", applicationIdOption));

        Double rate = Optional.ofNullable(slashCommandEvent.getOption(rateOption))
                .map(OptionMapping::getAsDouble)
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "Не задано значение для опции {}", rateOption));

        Optional<ActivityDictionary> activityDiscordOptional = activityDictionaryService.getByDiscordApplicationId(applicationId);

        if (activityDiscordOptional.isEmpty()) {
            slashCommandEvent
                    .reply("Активность не найдена в справочнике :no_entry_sign:")
                    .queue();
            return;
        }

        ActivityBalanceRate activityBalanceRate;
        Optional<ActivityBalanceRate> activityBalanceRateOptional = activityBalanceRateService.getByApplicationIdAndGuildId(applicationId, slashCommandEvent.getGuild().getId());

        if (activityBalanceRateOptional.isPresent()) {
            activityBalanceRate = activityBalanceRateOptional.get();
        } else {
            activityBalanceRate = new ActivityBalanceRate();
        }

        activityBalanceRate.setActivityDictionary(activityDiscordOptional.get());
        activityBalanceRate.setDiscordGuildId(slashCommandEvent.getGuild().getId());
        activityBalanceRate.setRate(new BigDecimal(rate));

        activityBalanceRateService.saveActivityBalanceRate(activityBalanceRate);

        activityBalanceRateService.reloadCache();
        slashCommandEvent
                .reply(StringFormatter.format("Активность {} успешно сохранена :video_game:", activityDiscordOptional.get().getApplicationName()))
                .queue();
    }

    @Override
    public CommandData toCommandData() {
        return Commands
                .slash(getName(), getDescription())
                .addOption(OptionType.STRING, applicationIdOption, "Идентификатор активности")
                .addOption(OptionType.NUMBER, rateOption, "Количество валюты раз Минуту");
    }
}
