package kg.shsatarov.erikabot.services.impl;

import kg.shsatarov.erikabot.entities.UserBalance;
import kg.shsatarov.erikabot.exceptions.UserBalanceException;
import kg.shsatarov.erikabot.repositories.UserBalanceRepository;
import kg.shsatarov.erikabot.services.UserBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserBalanceServiceImpl implements UserBalanceService {

    private final UserBalanceRepository userBalanceRepository;

    @Override
    public List<UserBalance> getAll() {
        return userBalanceRepository.findAll();
    }

    @Override
    public Optional<UserBalance> getEntityByDiscordUserId(String discordUserId) {
        return userBalanceRepository.findByDiscordUserId(discordUserId);
    }

    @Override
    public UserBalance addBalanceToUser(String discordUserId, BigDecimal amount) {

        UserBalance userBalance = getEntityByDiscordUserId(discordUserId).orElseThrow(() -> new UserBalanceException("Баланс пользователя не найден"));

        userBalance.setBalance(userBalance.getBalance().add(amount));

        return userBalanceRepository.save(userBalance);
    }

    @Override
    public UserBalance createUserBalance(String discordUserId, String discordGuildId) {
        UserBalance newUserBalance = new UserBalance();
        newUserBalance.setDiscordUserId(discordUserId);
        newUserBalance.setBalance(BigDecimal.ZERO);
        newUserBalance.setDiscordGuildId(discordGuildId);

        return userBalanceRepository.save(newUserBalance);
    }
}
