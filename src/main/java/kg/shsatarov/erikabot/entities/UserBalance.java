package kg.shsatarov.erikabot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "USER_BALANCES")
@Getter
@Setter
public class UserBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_BALANCES_SEQ")
    @SequenceGenerator(name = "USER_BALANCES_SEQ", allocationSize = 1)
    private Long id;

    private String discordUserId;

    private BigDecimal balance;

}
