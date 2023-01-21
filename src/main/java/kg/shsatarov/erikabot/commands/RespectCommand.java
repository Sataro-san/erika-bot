package kg.shsatarov.erikabot.commands;

import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class RespectCommand implements ExecutableCommand {

    @Value("${constants.respectUserId}")
    private String respectUserId;

    @Override
    public String getName() {
        return "respect";
    }

    @Override
    public String getDescription() {
        return "Show some respect for Kana";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        slashCommandEvent
                .reply(StringFormatter.format("{} выразил уважение <@{}>", slashCommandEvent.getUser().getAsMention(), respectUserId)).queue();

    }
}
