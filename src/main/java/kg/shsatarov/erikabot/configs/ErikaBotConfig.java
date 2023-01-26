package kg.shsatarov.erikabot.configs;

import kg.shsatarov.erikabot.listeners.ModalListener;
import kg.shsatarov.erikabot.listeners.SlashCommandListener;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ErikaBotConfig {
    @Value("${jda.token}")
    private String token;


    private final SlashCommandListener slashCommandListener;
    private final ModalListener modalListener;

    @Bean
    public JDA jdaInstance() {

        return JDABuilder
                .createLight(token)
                .setStatus(OnlineStatus.ONLINE)
                .setActivity(Activity.watching("Doki Doki"))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .setChunkingFilter(ChunkingFilter.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_PRESENCES)
                .enableCache(CacheFlag.VOICE_STATE)
                .enableCache(CacheFlag.ACTIVITY)
                .addEventListeners(slashCommandListener, modalListener)
                .build();
    }

}
