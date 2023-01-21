package kg.shsatarov.erikabot.commands.voice_channel;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

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
        return "Пригласить в голосовй чат";
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

        } else {
            slashCommandEvent
                    .reply("User " + eventMember.getAsMention() + " is not in voice channel")
                    .queue();
        }

    }
}
