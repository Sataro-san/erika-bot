package kg.shsatarov.erikabot.commands.currency;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
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
public class GetCurrentBalanceCommand implements ExecutableCommand {

    private final UserService userService;

    @Value("${constants.salaryRoleId}")
    private String salaryRoleId;

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

        Role salaryRole = slashCommandEvent.getGuild().getRoleById(salaryRoleId);

        if (!userService.hasSalaryRole(member)) {
            slashCommandEvent
                    .reply(StringFormatter.format("{} вы не обладаете ролью \"{}\"", member.getAsMention(), salaryRole.getName()))
                    .queue();

            return;
        }


        slashCommandEvent
                .reply(StringFormatter.format("{} ваш баланс Канабаксов: {} шт.", member.getAsMention(), 0))
                .queue();

    }
}
