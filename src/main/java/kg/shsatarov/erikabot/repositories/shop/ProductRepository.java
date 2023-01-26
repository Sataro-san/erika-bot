package kg.shsatarov.erikabot.repositories.shop;

import kg.shsatarov.erikabot.entities.shop.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByDiscordGuildId(String discordGuildId);

}
