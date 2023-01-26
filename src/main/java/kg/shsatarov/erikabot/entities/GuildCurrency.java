package kg.shsatarov.erikabot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GUILD_CURRENCY")
@Getter
@Setter
public class GuildCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GUILD_CURRENCY_SEQ")
    @SequenceGenerator(name = "GUILD_CURRENCY_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String discordGuildId;

    @Column(nullable = false)
    private String discordRoleId;

    @Column(nullable = false)
    private String economistRoleId;

    @Column(nullable = false)
    private String currencyName;
    @Column(nullable = false)
    private String currencyNameGenitive;

    @Column(nullable = false)
    private String shortCode;
}
