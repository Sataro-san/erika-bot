package kg.shsatarov.erikabot.commands;

import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class HelloCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public String getDescription() {
        return "Привет!";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {
        slashCommandEvent
                .reply("Hello " + slashCommandEvent.getUser().getName())
                .queue();
    }
}
