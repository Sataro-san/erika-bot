package kg.shsatarov.erikabot.services.impl;

import jakarta.annotation.PostConstruct;
import kg.shsatarov.erikabot.entities.GuildCurrency;
import kg.shsatarov.erikabot.repositories.GuildCurrencyRepository;
import kg.shsatarov.erikabot.services.GuildCurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuildCurrencyServiceImpl implements GuildCurrencyService {

    private final GuildCurrencyRepository guildCurrencyRepository;

    private List<GuildCurrency> guildCurrencyCache;

    @PostConstruct
    private void initialize() {
        reloadCache();
    }

    @Override
    public Optional<GuildCurrency> getByGuildId(String guildId) {
        return guildCurrencyCache.stream().filter(currency -> currency.getDiscordGuildId().equals(guildId)).findFirst();
    }

    @Override
    public GuildCurrency saveCurrency(GuildCurrency guildCurrency) {

        guildCurrency = guildCurrencyRepository.save(guildCurrency);
        this.reloadCache();

        return guildCurrency;
    }

    @Override
    public void reloadCache() {
        this.guildCurrencyCache = guildCurrencyRepository.findAll();
    }


}
