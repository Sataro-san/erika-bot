package kg.shsatarov.erikabot.services;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface CommandService {

    void executeCommand(SlashCommandInteractionEvent slashCommandEvent);

}
