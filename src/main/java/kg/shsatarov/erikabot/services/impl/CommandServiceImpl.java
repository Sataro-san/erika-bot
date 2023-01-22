package kg.shsatarov.erikabot.services.impl;

import kg.shsatarov.erikabot.cache.CommandCache;
import kg.shsatarov.erikabot.services.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandCache commandCache;

    @Override
    public void executeCommand(SlashCommandInteractionEvent slashCommandEvent) {

        Optional.ofNullable(commandCache.getCommand(slashCommandEvent.getName()))
                .ifPresentOrElse(command -> command.execute(slashCommandEvent), () -> log.warn("Unknown command {}", slashCommandEvent.getName()));

    }

}
