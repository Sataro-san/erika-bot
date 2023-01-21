package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.entities.UserBalance;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserBalanceService {

    Optional<UserBalance> getEntityByDiscordUserId(String discordUserId);

    UserBalance addBalanceToUser(String discordUserId, BigDecimal amount);

    UserBalance createUserBalance(String discordUserId);
}
