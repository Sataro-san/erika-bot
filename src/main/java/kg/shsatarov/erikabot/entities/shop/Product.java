package kg.shsatarov.erikabot.entities.shop;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import kg.shsatarov.erikabot.entities.base.BaseDiscordEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "PRODUCTS")
@Getter
@Setter
@NoArgsConstructor
@SequenceGenerator(name = "id_generator", sequenceName = "CANDIDATES_SEQ", allocationSize = 1)
public class Product extends BaseDiscordEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

}
