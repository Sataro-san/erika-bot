package kg.shsatarov.erikabot.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ACTIVITY_DICTIONARY")
@Getter
@Setter
public class ActivityDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACTIVITY_DICTIONARY_SEQ")
    @SequenceGenerator(name = "ACTIVITY_DICTIONARY_SEQ", allocationSize = 1)
    private Long id;

    private String applicationName;

    @Column(nullable = false)
    private String discordApplicationId;

}
