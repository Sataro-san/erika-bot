package kg.shsatarov.erikabot.commands.activity;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.services.ActivityBalanceRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ReloadActivityRateCacheCommand implements ExecutableCommand {

    private final ActivityBalanceRateService activityBalanceRateService;

    @Override
    public String getName() {
        return "activity-reload";
    }

    @Override
    public String getDescription() {
        return "Обновить кэш по активносям";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        activityBalanceRateService.reloadCache();

        slashCommandEvent.reply("Кэш активностей обновлён :white_check_mark:");

    }
}
