package kg.shsatarov.erikabot.entities.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
public abstract class BaseDiscordEntity extends BaseEntity {

    @Column(nullable = false)
    protected String discordGuildId;

}
