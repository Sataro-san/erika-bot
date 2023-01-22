package kg.shsatarov.erikabot.services.impl;

import kg.shsatarov.erikabot.entities.GuildCurrency;
import kg.shsatarov.erikabot.services.GuildCurrencyService;
import kg.shsatarov.erikabot.services.UserService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final GuildCurrencyService guildCurrencyService;

    @Override
    public Boolean hasSalaryRole(Member member) {

        GuildCurrency guildCurrency = guildCurrencyService.getByGuildId(member.getGuild().getId()).orElse(null);

        return member.getRoles().stream().anyMatch(role -> role.getId().equals(guildCurrency.getDiscordRoleId()));
    }
}
