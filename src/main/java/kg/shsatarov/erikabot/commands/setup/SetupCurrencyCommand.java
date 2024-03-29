package kg.shsatarov.erikabot.commands.setup;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.GuildCurrency;
import kg.shsatarov.erikabot.exceptions.DiscordBotException;
import kg.shsatarov.erikabot.services.GuildCurrencyService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupCurrencyCommand implements ExecutableCommand {

    private String currencyNameOption = "currency-name";
    private String shortCodeOption = "short-code";
    private String salaryRoleOption = "salary-role";
    private String economistRoleOption = "economist-role";
    private String currencyNameGenitiveOption = "currency-name-genitive";

    private final GuildCurrencyService guildCurrencyService;

    @Override
    public String getName() {
        return "setup-currency";
    }

    @Override
    public String getDescription() {
        return "Настройка валюты сервера";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        String currencyName = Optional.ofNullable(slashCommandEvent.getOption(currencyNameOption))
                .map(OptionMapping::getAsString)
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "{} не указан параметр {} :no_entry_sign:", slashCommandEvent.getMember().getAsMention(), currencyNameOption));

        String shortCode = Optional.ofNullable(slashCommandEvent.getOption(shortCodeOption))
                .map(OptionMapping::getAsString)
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "{} не указан параметр {} :no_entry_sign:", slashCommandEvent.getMember().getAsMention(), shortCodeOption));

        String roleId = Optional.ofNullable(slashCommandEvent.getOption(salaryRoleOption))
                .map(OptionMapping::getAsString)
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "{} не указан параметр {} :no_entry_sign:", slashCommandEvent.getMember().getAsMention(), salaryRoleOption));

        String economistRoleId = Optional.ofNullable(slashCommandEvent.getOption(economistRoleOption))
                .map(OptionMapping::getAsString)
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "{} не указан параметр {} :warning:", slashCommandEvent.getMember().getAsMention(), economistRoleOption));

        String currencyNameGenitive = Optional.ofNullable(slashCommandEvent.getOption(currencyNameGenitiveOption))
                .map(OptionMapping::getAsString)
                .orElse(currencyName);

        Optional<GuildCurrency> guildCurrencyOptional = guildCurrencyService.getByGuildId(slashCommandEvent.getGuild().getId());
        GuildCurrency guildCurrency = guildCurrencyOptional.orElseGet(GuildCurrency::new);

        guildCurrency.setCurrencyName(currencyName);
        guildCurrency.setShortCode(shortCode);
        guildCurrency.setCurrencyNameGenitive(currencyNameGenitive);
        guildCurrency.setDiscordGuildId(slashCommandEvent.getGuild().getId());
        guildCurrency.setDiscordRoleId(roleId);
        guildCurrency.setEconomistRoleId(economistRoleId);

        guildCurrencyService.saveCurrency(guildCurrency);

        slashCommandEvent
                .reply(StringFormatter.format("{} валюта {} успешно сохранена :coin:", slashCommandEvent.getMember().getAsMention(), guildCurrency.getCurrencyName()))
                .queue();
    }

    @Override
    public CommandData toCommandData() {
        return Commands
                .slash(getName(), getDescription())
                .addOption(OptionType.STRING, currencyNameOption, "Название валюты")
                .addOption(OptionType.STRING, shortCodeOption, "Сокращённое название")
                .addOption(OptionType.ROLE, salaryRoleOption, "Роль которая получает валюту")
                .addOption(OptionType.ROLE, economistRoleOption, "Роль отвечающая за экономику")
                .addOption(OptionType.STRING, currencyNameGenitiveOption, "[Опционально] Название валюты в род.падеже", false);
    }
}
