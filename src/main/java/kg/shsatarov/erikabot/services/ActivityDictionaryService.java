package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.entities.ActivityDictionary;

import java.util.List;
import java.util.Optional;

public interface ActivityDictionaryService {

    List<ActivityDictionary> getAll();
    Optional<ActivityDictionary> getById(Long id);
    Optional<ActivityDictionary> getByDiscordApplicationId(String discordApplicationId);

}
