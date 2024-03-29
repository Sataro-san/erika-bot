package kg.shsatarov.erikabot.repositories.activities;

import kg.shsatarov.erikabot.entities.activities.ActivityDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActivityDictionaryRepository extends JpaRepository<ActivityDictionary, Long> {

    Optional<ActivityDictionary> findByDiscordApplicationId(String discordApplicationId);

}
