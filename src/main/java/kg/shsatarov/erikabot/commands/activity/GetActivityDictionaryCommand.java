package kg.shsatarov.erikabot.commands.activity;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.activities.ActivityDictionary;
import kg.shsatarov.erikabot.services.activities.ActivityDictionaryService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetActivityDictionaryCommand implements ExecutableCommand {

    private final ActivityDictionaryService activityDictionaryService;

    @Override
    public String getName() {
        return "dictionary-activities";
    }

    @Override
    public String getDescription() {
        return "Справочник активностей (нужен для заполнения начислений)";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        StringBuilder activityRatesStringBuilder = new StringBuilder();

        for (ActivityDictionary activityDictionary : activityDictionaryService.getAll()) {
            activityRatesStringBuilder.append(StringFormatter.format("**{}**: Идентификатор ***{}***:\n", activityDictionary.getApplicationName(), activityDictionary.getDiscordApplicationId()));
        }

        slashCommandEvent
                .reply(StringFormatter.format("{}\n:video_game: Список активностей:\n{}\n*Идентификатор необходим для создания начислений по активностям*", slashCommandEvent.getMember().getAsMention(), activityRatesStringBuilder.toString()))
                .queue();

    }
}
