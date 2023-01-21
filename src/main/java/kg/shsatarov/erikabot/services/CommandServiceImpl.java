package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.cache.CommandCache;
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
                .ifPresentOrElse(x -> x.execute(slashCommandEvent), () -> log.warn("Unknown command {}", slashCommandEvent.getName()));

    }

}