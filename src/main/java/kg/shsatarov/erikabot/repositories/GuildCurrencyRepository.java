package kg.shsatarov.erikabot.repositories;

import kg.shsatarov.erikabot.entities.GuildCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildCurrencyRepository extends JpaRepository<GuildCurrency, Long> {

}
