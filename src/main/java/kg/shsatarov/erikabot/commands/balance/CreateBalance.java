package kg.shsatarov.erikabot.commands.balance;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.GuildCurrency;
import kg.shsatarov.erikabot.exceptions.DiscordBotException;
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

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateBalance implements ExecutableCommand {

    private final UserService userService;
    private final UserBalanceService userBalanceService;
    private final GuildCurrencyService guildCurrencyService;

    @Override
    public String getName() {
        return "balance-create";
    }

    @Override
    public String getDescription() {
        return "Создать баланс";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        Member member = slashCommandEvent.getMember();

        GuildCurrency guildCurrency = guildCurrencyService.getByGuildId(slashCommandEvent.getGuild().getId())
                .orElseThrow(() -> new DiscordBotException(slashCommandEvent, "На сервере не установлена валюта :no_entry_sign:"));

        Role salaryRole = slashCommandEvent.getGuild().getRoleById(guildCurrency.getDiscordRoleId());

        if (!userService.hasSalaryRole(member)) {
            throw new DiscordBotException(slashCommandEvent, "{} вы не обладаете ролью ***{}*** :no_entry:sign:", member.getAsMention(), salaryRole.getName());
        }

        if (userBalanceService.getEntityByDiscordUserIdAndDiscordGuildId(member.getId(), member.getGuild().getId()).isPresent()) {
            throw new DiscordBotException(slashCommandEvent, "{} вы не обладаете ролью ***{}*** :no_entry:sign:", member.getAsMention(), salaryRole.getName());
        }

        userBalanceService.createUserBalance(member.getId(), member.getGuild().getId());
        slashCommandEvent
                .reply(StringFormatter.format("{} баланс был создан :coin:", member.getAsMention()))
                .queue();

    }
}
