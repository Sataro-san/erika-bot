package kg.shsatarov.erikabot.commands.voice_channel.overwatch;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.exceptions.DiscordBotException;
import kg.shsatarov.erikabot.lava_player.PlayerManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
@NoArgsConstructor
public class INeedHealingCommand implements ExecutableCommand {

    @Override
    public String getName() {
        return "healing";
    }

    @Override
    public String getDescription() {
        return "I need healing";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        AudioManager audioManager = slashCommandEvent.getGuild().getAudioManager();
        if (audioManager.getConnectedChannel() == null)
            throw new DiscordBotException(slashCommandEvent, "Пригласите меня в голосовой чат!");

        playSound(slashCommandEvent.getGuild());

        slashCommandEvent
                .reply("Heal me!)")
                .queue();

    }

    private void playSound(Guild guild) {

        try {
            Thread.sleep(500L);

            Random random = new Random();
            int randomInt = random.nextInt(3);

            String needHealingOne = "https://static.wikia.nocookie.net/overwatch_gamepedia/images/b/bd/Genji_-_I_need_healing.ogg/revision/latest?cb=20160824213321";
            String needHealingTwo = "https://static.wikia.nocookie.net/overwatch_gamepedia/images/f/fe/Genji_-_I_Require_Healing.ogg/revision/latest?cb=20170710022757";

            PlayerManager.getInstance().playMusicLocal(guild, randomInt < 2 ? needHealingOne : needHealingTwo, 250L);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

}
