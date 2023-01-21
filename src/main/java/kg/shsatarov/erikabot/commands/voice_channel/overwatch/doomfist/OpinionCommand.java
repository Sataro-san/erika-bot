package kg.shsatarov.erikabot.commands.voice_channel.overwatch.doomfist;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.lava_player.PlayerManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@NoArgsConstructor
public class OpinionCommand implements ExecutableCommand {

    @Override
    public String getName() {
        return "opinion";
    }

    @Override
    public String getDescription() {
        return "Did I ask?";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        try {

            String voiceLineURL = "https://static1.squarespace.com/static/59af2189c534a58c97bd63b3/t/5af351e0562fa7cf25063079/1525895649225/Doomfist+voice+line+I+didn%27t+ask+for+your+opinion.ogg/original/Doomfist+voice+line+I+didn%27t+ask+for+your+opinion.ogg";

            PlayerManager.getInstance().playMusicLocal(slashCommandEvent.getGuild(), voiceLineURL, 100L);

            slashCommandEvent
                    .reply(";)")
                    .queue();

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}