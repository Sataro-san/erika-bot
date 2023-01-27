package kg.shsatarov.erikabot.commands.voice_channel;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.exceptions.DiscordBotException;
import kg.shsatarov.erikabot.lava_player.PlayerManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Slf4j
@Component
public class PEWGFCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "dorya";
    }

    @Override
    public String getDescription() {
        return "PEWGF";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        AudioManager audioManager = slashCommandEvent.getGuild().getAudioManager();
        if (audioManager.getConnectedChannel() == null)
            throw new DiscordBotException(slashCommandEvent, "Пригласите меня в голосовой чат!");


        String voiceLineURL = "https://cdn.blerp.com/normalized/673cedb0-7804-11ea-b006-7f239108b4c3";

        PlayerManager.getInstance().playMusicLocal(slashCommandEvent.getGuild(), voiceLineURL, 100L);

        slashCommandEvent
                .reply("PEWGF!")
                .queue();

    }
}
