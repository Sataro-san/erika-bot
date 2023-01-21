package kg.shsatarov.erikabot.cache;

import jakarta.annotation.PostConstruct;
import kg.shsatarov.erikabot.commands.ExecutableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CommandCache {

    @Autowired
    private List<? extends ExecutableCommand> commands;

    private Map<String, ExecutableCommand> executableCommands = new LinkedHashMap<>();

    @PostConstruct
    private void initialize() {

        commands.forEach(x -> {
            executableCommands.put(x.getName(), x);
        });

    }

    public List<ExecutableCommand> getCommands() {
        return executableCommands.values().stream().toList();
    }

    public ExecutableCommand getCommand(String commandName) {
        return executableCommands.get(commandName);
    }

}
