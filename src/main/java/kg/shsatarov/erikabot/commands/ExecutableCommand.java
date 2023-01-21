package kg.shsatarov.erikabot.commands;

import kg.shsatarov.erikabot.enums.CommandType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

/**
 * any implementing class must have default constructor
 * using {@link CommandType#SLASH} as deafult
 * override toCommandData() if options needed
 **/
public interface ExecutableCommand {

    String getName();

    String getDescription();

    default CommandType getType() {
        return CommandType.SLASH;
    }

    void execute(SlashCommandInteractionEvent slashCommandEvent);

    default CommandData toCommandData() {
        return Commands.slash(getName(), getDescription());
    }
}
