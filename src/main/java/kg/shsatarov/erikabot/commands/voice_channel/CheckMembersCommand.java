package kg.shsatarov.erikabot.commands.voice_channel;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Slf4j
@Component
public class CheckMembersCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "vc-members";
    }

    @Override
    public String getDescription() {
        return "Инфомрация о пользвоателях в голосовом чате";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        GuildVoiceState memberVoiceState = slashCommandEvent.getMember().getVoiceState();

        if (!memberVoiceState.inAudioChannel()) {
            slashCommandEvent.reply("Присоединитесь в голосовой чат для выполнения команды").queue();
        }

        VoiceChannel voiceChannel = slashCommandEvent.getGuild().getVoiceChannelCache().getElementById(memberVoiceState.getChannel().getId());

        List<Member> members = voiceChannel.getMembers();

        String mentionedUsers = members.stream()
                .map(member -> member.getAsMention())
                .collect(Collectors.joining(" "));

        slashCommandEvent.reply(StringFormatter.format("{} в голосовом канале \"{}\"", mentionedUsers, voiceChannel.getName())).queue();

    }
}
