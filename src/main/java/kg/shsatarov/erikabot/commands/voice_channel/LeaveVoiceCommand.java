package kg.shsatarov.erikabot.commands.voice_channel;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class LeaveVoiceCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Выйти из голосового чата";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        Guild guild = slashCommandEvent.getGuild();
        AudioManager audioManager = guild.getAudioManager();

        if (guild.getAudioManager().isConnected()) {
            slashCommandEvent
                    .reply(StringFormatter.format("Leaving channel {}", audioManager.getConnectedChannel().getName()))
                    .queue();

            audioManager.closeAudioConnection();
        } else {
            slashCommandEvent
                    .reply("Im currently not in voice channel")
                    .queue();
        }

    }
}
