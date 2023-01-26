package kg.shsatarov.erikabot.services.activities;

import kg.shsatarov.erikabot.entities.activities.ActivityDictionary;

import java.util.List;
import java.util.Optional;

public interface ActivityDictionaryService {

    List<ActivityDictionary> getAll();
    Optional<ActivityDictionary> getById(Long id);
    Optional<ActivityDictionary> getByDiscordApplicationId(String discordApplicationId);

}
