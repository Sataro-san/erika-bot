package kg.shsatarov.erikabot.commands.balance;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.services.UserBalanceService;
import kg.shsatarov.erikabot.services.UserService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateBalance implements ExecutableCommand {

    private final UserService userService;
    private final UserBalanceService userBalanceService;

    @Value("${constants.salaryRoleId}")
    private String salaryRoleId;

    @Override
    public String getName() {
        return "create-balance";
    }

    @Override
    public String getDescription() {
        return "Создать баланс";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        Member member = slashCommandEvent.getMember();

        Role salaryRole = slashCommandEvent.getGuild().getRoleById(salaryRoleId);

        if (!userService.hasSalaryRole(member)) {
            slashCommandEvent
                    .reply(StringFormatter.format("{} вы не обладаете ролью \"{}\"", member.getAsMention(), salaryRole.getName()))
                    .queue();

            return;
        }

        if (userBalanceService.getEntityByDiscordUserId(member.getId()).isPresent()) {
            slashCommandEvent
                    .reply(StringFormatter.format("{} у вас уже имеется баланс", member.getAsMention()))
                    .queue();

            return;
        }

        userBalanceService.createUserBalance(member.getId());
        slashCommandEvent
                .reply(StringFormatter.format("{} баланс был создан!", member.getAsMention()))
                .queue();

    }
}
