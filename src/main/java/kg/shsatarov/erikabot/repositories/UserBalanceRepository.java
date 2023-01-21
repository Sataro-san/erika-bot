package kg.shsatarov.erikabot.repositories;

import kg.shsatarov.erikabot.entities.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    Optional<UserBalance> findByDiscordUserId(String discordUserId);

}
