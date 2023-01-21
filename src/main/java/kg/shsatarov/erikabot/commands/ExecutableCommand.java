package kg.shsatarov.erikabot.commands;

import kg.shsatarov.erikabot.enums.CommandType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

/**
 * any implementing class must have default constructor
 * using {@link CommandType#SLASH} as deafult
 **/
public interface ExecutableCommand {

    String getName();

    String getDescription();

    default CommandType getType() {
        return CommandType.SLASH;
    }

    void execute(SlashCommandInteractionEvent slashCommandEvent);

}
