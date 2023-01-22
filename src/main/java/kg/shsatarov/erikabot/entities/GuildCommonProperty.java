package kg.shsatarov.erikabot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "GUILD_COMMON_PROPERTY")
@Getter
@Setter
public class GuildCommonProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GUILD_COMMON_PROPERTY_SEQ")
    @SequenceGenerator(name = "GUILD_COMMON_PROPERTY_SEQ", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String discordGuildId;
    @Column(nullable = false)
    private String key;
    private String value;
}
