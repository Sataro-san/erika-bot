package kg.shsatarov.erikabot.commands.voice_channel;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.lava_player.PlayerManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

import java.util.Random;

@NoArgsConstructor
@Slf4j
@Component
public class JoinVoiceChatCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Пригласить в голосовой чат";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        Guild guild = slashCommandEvent.getGuild();
        Member eventMember = slashCommandEvent.getMember();
        AudioManager audioManager = guild.getAudioManager();

        if (audioManager.isConnected()) {
            slashCommandEvent
                    .reply("Im already in " + audioManager.getConnectedChannel().getName())
                    .queue();

            return;
        }

        if (eventMember.getVoiceState().inAudioChannel()) {

            VoiceChannel voiceChannel = slashCommandEvent.getGuild().getVoiceChannelById(eventMember.getVoiceState().getChannel().getId());

            slashCommandEvent
                    .reply("Joining VoiceChannel " + voiceChannel.getName())
                    .queue();

            audioManager.openAudioConnection(voiceChannel);

            //testing audio player
            playHiSound(slashCommandEvent.getGuild());


        } else {
            slashCommandEvent
                    .reply("User " + eventMember.getAsMention() + " is not in voice channel")
                    .queue();
        }

    }

    private void playHiSound(Guild guild) {

        try {
            Thread.sleep(500L);

            Random random = new Random();
            int randomInt = random.nextInt(2);

            String hiAudioUrl = "https://static.wikia.nocookie.net/overwatch_gamepedia/images/4/4f/D.Va_-_Hi.ogg/revision/latest?cb=20160629221909";
            String hiyaAudioUrl = "https://static.wikia.nocookie.net/overwatch_gamepedia/images/a/ab/D.Va_-_Hiya%21.ogg/revision/latest?cb=20160929164557";


            PlayerManager.getInstance().playMusicLocal(guild, randomInt == 0 ? hiAudioUrl : hiyaAudioUrl, 450L);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }
}
