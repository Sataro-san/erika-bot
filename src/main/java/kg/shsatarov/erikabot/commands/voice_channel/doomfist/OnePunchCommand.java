package kg.shsatarov.erikabot.commands.voice_channel.doomfist;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.lava_player.PlayerManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Slf4j
@Component
public class OnePunchCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "op";
    }

    @Override
    public String getDescription() {
        return ";)";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        try {

            String voiceLineURL = "https://static.wikia.nocookie.net/overwatch_gamepedia/images/0/08/Doomfist_-_One_punch_is_all_I_need.ogg/revision/latest?cb=20170813140029";

            PlayerManager.getInstance().playMusicLocal(slashCommandEvent.getGuild(), voiceLineURL, 100L);

            slashCommandEvent
                    .reply(";)")
                    .queue();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
