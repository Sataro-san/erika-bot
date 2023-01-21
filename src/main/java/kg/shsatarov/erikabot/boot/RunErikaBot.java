package kg.shsatarov.erikabot.boot;

import kg.shsatarov.erikabot.cache.CommandCache;
import kg.shsatarov.erikabot.commands.ExecutableCommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RunErikaBot implements CommandLineRunner {
    private final JDA jdaInstance;
    private final CommandCache commandCache;


    @Override
    public void run(String... args) {
        CommandListUpdateAction commandListUpdateAction = jdaInstance.updateCommands();

        commandListUpdateAction.addCommands(toCommandDataCollection(commandCache.getCommands())).queue();
    }

    private List<CommandData> toCommandDataCollection(List<ExecutableCommand> executableCommands) {

        return executableCommands.stream().map(executableCommand -> executableCommand.toCommandData()).toList();

    }
}
