package kg.shsatarov.erikabot.entities.activities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ACTIVITY_BALANCE_RATES")
@Getter
@Setter
public class ActivityBalanceRate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITY_BALANCE_RATES_SEQ")
    @SequenceGenerator(name = "ACTIVITY_BALANCE_RATES_SEQ", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_dictionary_id")
    private ActivityDictionary activityDictionary;
    @Column(nullable = false)
    private String discordGuildId;
    private BigDecimal rate;

}