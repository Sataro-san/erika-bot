package kg.shsatarov.erikabot.commands.balance;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.GuildCurrency;
import kg.shsatarov.erikabot.services.GuildCurrencyService;
import kg.shsatarov.erikabot.services.UserBalanceService;
import kg.shsatarov.erikabot.services.UserService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetCurrentBalanceCommand implements ExecutableCommand {

    private final UserService userService;
    private final UserBalanceService userBalanceService;
    private final GuildCurrencyService guildCurrencyService;

    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getDescription() {
        return "Узнай свой баланс!";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        Member member = slashCommandEvent.getMember();

        Optional<GuildCurrency> guildCurrencyOptional = guildCurrencyService.getByGuildId(slashCommandEvent.getGuild().getId());

        if (guildCurrencyOptional.isEmpty()) {
            slashCommandEvent.reply("На сервере не установлена валюта :no_entry_sign:").queue();
            return;
        }

        Role salaryRole = slashCommandEvent
                .getGuild()
                .getRoleById(guildCurrencyOptional.get().getDiscordRoleId());

        if (salaryRole == null) {
            slashCommandEvent
                    .reply("Роль для получения валюты не найдена :no_entry_sign:")
                    .queue();
            return;
        }

        if (!userService.hasSalaryRole(member)) {
            slashCommandEvent
                    .reply(StringFormatter.format("{} вы не обладаете ролью ***{}*** :no_entry_sign:", member.getAsMention(), salaryRole.getName()))
                    .queue();
            return;
        }

        userBalanceService.getEntityByDiscordUserIdAndDiscordGuildId(member.getId(), member.getGuild().getId())
                .ifPresentOrElse(
                        userBalance -> slashCommandEvent.reply(StringFormatter.format("{} ваш баланс {}: {} :moneybag:", member.getAsMention(), guildCurrencyOptional.get().getCurrencyNameGenitive(), userBalance.getBalance())).queue(),
                        () -> slashCommandEvent.reply("Ваш баланс не найден, для создания баланса выполните команду :keyboard:\n`/balance-create`").queue());

    }
}
