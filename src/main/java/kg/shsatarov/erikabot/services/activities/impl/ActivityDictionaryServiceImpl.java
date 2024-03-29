package kg.shsatarov.erikabot.services.activities.impl;

import kg.shsatarov.erikabot.entities.activities.ActivityDictionary;
import kg.shsatarov.erikabot.repositories.activities.ActivityDictionaryRepository;
import kg.shsatarov.erikabot.services.activities.ActivityDictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityDictionaryServiceImpl implements ActivityDictionaryService {

    private final ActivityDictionaryRepository activityDictionaryRepository;

    @Override
    public List<ActivityDictionary> getAll() {
        return activityDictionaryRepository.findAll();
    }

    @Override
    public Optional<ActivityDictionary> getById(Long id) {
        return activityDictionaryRepository.findById(id);
    }

    @Override
    public Optional<ActivityDictionary> getByDiscordApplicationId(String discordApplicationId) {
        return activityDictionaryRepository.findByDiscordApplicationId(discordApplicationId);
    }
}
