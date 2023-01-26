package kg.shsatarov.erikabot.services.shop.impl;

import kg.shsatarov.erikabot.entities.shop.Product;
import kg.shsatarov.erikabot.repositories.shop.ProductRepository;
import kg.shsatarov.erikabot.services.shop.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllByDiscordGuildId(String discordGuildId) {
        return productRepository.findAllByDiscordGuildId(discordGuildId);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}
