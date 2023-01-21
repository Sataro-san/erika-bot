package kg.shsatarov.erikabot.listeners;

import kg.shsatarov.erikabot.services.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlashCommandListener extends ListenerAdapter {

    private final CommandService commandService;

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent slashCommandEvent) {

        log.info("executing command {}", slashCommandEvent.getName());
        commandService.executeCommand(slashCommandEvent);

    }
}
