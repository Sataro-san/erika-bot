package kg.shsatarov.erikabot.services.shop;

import kg.shsatarov.erikabot.entities.shop.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllByDiscordGuildId(String discordGuildId);

    Product saveProduct(Product product);

}
