package kg.shsatarov.erikabot.services.impl;

import kg.shsatarov.erikabot.entities.UserBalance;
import kg.shsatarov.erikabot.exceptions.UserBalanceException;
import kg.shsatarov.erikabot.repositories.UserBalanceRepository;
import kg.shsatarov.erikabot.services.UserBalanceService;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Member;
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
    public Optional<UserBalance> getEntityByDiscordUserIdAndDiscordGuildId(String discordUserId, String discordGuildId) {
        return userBalanceRepository.findByDiscordUserIdAndDiscordGuildId(discordUserId, discordGuildId);
    }

    @Override
    public UserBalance addBalanceToUser(Member member, BigDecimal amount) {

        UserBalance userBalance = getEntityByDiscordUserIdAndDiscordGuildId(member.getId(), member.getGuild().getId())
                .orElseThrow(() -> new UserBalanceException("Баланс пользователя не найден"));

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
