package kg.shsatarov.erikabot.commands.balance;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.GuildCurrency;
import kg.shsatarov.erikabot.services.GuildCurrencyService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelpBalanceCommand implements ExecutableCommand {

    private final GuildCurrencyService guildCurrencyService;

    @Override
    public String getName() {
        return "balance-help";
    }

    @Override
    public String getDescription() {
        return "Помощь по валюте";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        Optional<GuildCurrency> guildCurrencyOptional = guildCurrencyService.getByGuildId(slashCommandEvent.getGuild().getId());

        if (guildCurrencyOptional.isEmpty()) {
            slashCommandEvent.reply("На сервере не установлена валюта :no_entry_sign:").queue();
            return;
        }
        GuildCurrency guildCurrency = guildCurrencyOptional.get();

        Role currencyRole = slashCommandEvent.getGuild().getRoleById(guildCurrency.getDiscordRoleId());
        if (currencyRole == null) {
            slashCommandEvent.reply("На сервере не установлена роль получателя валюты :no_entry_sign:").queue();
            return;
        }


        slashCommandEvent
                .reply(StringFormatter.format(
                        "{}\n:coin: На сервере установлена валюта - ***{}*** ({})\n\n:watch: *Валюта начисляется раз в Минуту при соблюдении следующих условий:*\n  1. У вас должна быть роль ***{}***\n  2. Необходимо включить активности в Discord (чтобы бот мог собирать информацию)\n  3. Статус Онлайн или Не беспокоить\n  4. Нужно быть в голосовом канале, в котором больше одного пользователя\n\n:video_game: За каждую активность (игру) валюта начисляется по разному, для просмотра тарификаций введите команду `/activity-rates`",
                        slashCommandEvent.getMember().getAsMention(),
                        guildCurrency.getCurrencyName(),
                        guildCurrency.getShortCode(),
                        currencyRole.getName()
                        ))
                .queue();

    }
}
