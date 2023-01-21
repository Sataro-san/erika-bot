package kg.shsatarov.erikabot.commands;

import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class GoodManCommand implements ExecutableCommand {

    @Value("${constants.goodManId}")
    private String goodManId;

    @Override
    public String getName() {
        return "krasavchik";
    }

    @Override
    public String getDescription() {
        return "Вы знаете о ком речь";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        slashCommandEvent
                .reply(StringFormatter.format("<@{}> красавчик!", goodManId))
                .queue();

    }
}
