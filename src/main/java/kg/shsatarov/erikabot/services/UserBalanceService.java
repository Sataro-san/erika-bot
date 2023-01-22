package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.entities.UserBalance;
import net.dv8tion.jda.api.entities.Member;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserBalanceService {

    List<UserBalance> getAll();

    Optional<UserBalance> getEntityByDiscordUserIdAndDiscordGuildId(String discordUserId, String discordGuildId);

    UserBalance addBalanceToUser(Member member, BigDecimal amount);

    UserBalance createUserBalance(String discordUserId, String discordGuildId);
}
