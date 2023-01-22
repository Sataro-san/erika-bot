package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.entities.UserBalance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserBalanceService {

    List<UserBalance> getAll();

    Optional<UserBalance> getEntityByDiscordUserId(String discordUserId);

    UserBalance addBalanceToUser(String discordUserId, BigDecimal amount);

    UserBalance createUserBalance(String discordUserId, String discordGuildId);
}
