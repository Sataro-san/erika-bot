package kg.shsatarov.erikabot.services;

import kg.shsatarov.erikabot.entities.GuildCurrency;

import java.util.Optional;

public interface GuildCurrencyService {

    Optional<GuildCurrency> getByGuildId(String guildId);

    GuildCurrency saveCurrency(GuildCurrency guildCurrency);

    void reloadCache();

}
