package kg.shsatarov.erikabot.commands.activity;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.services.UserService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetActivityCommand implements ExecutableCommand {

    private final UserService userService;

    @Value("${constants.salaryRoleId}")
    private String salaryRoleId;

    @Override
    public String getName() {
        return "activity";
    }

    @Override
    public String getDescription() {
        return "get activity command";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        String userId = slashCommandEvent.getOption("user").getAsString();

        Member member = slashCommandEvent.getGuild().getMemberById(userId);

        Role salaryRole = slashCommandEvent.getGuild().getRoleById(salaryRoleId);

        if (!userService.hasSalaryRole(member)) {
            slashCommandEvent
                    .reply(StringFormatter.format("{} не обладает ролью \"{}\" :no_entry_sign:", member.getAsMention(), salaryRole.getName()))
                    .queue();

            return;
        }

        Activity activity = member.getActivities().stream()
                .filter(currentActivity -> Activity.ActivityType.PLAYING.equals(currentActivity.getType()))
                .findFirst()
                .orElse(null);

        if (activity == null) {
            slashCommandEvent.reply(StringFormatter.format("{} has no activities", member.getAsMention())).queue();
            return;
        }

        slashCommandEvent.reply(StringFormatter.format("{} {} {} applicationId {}", member.getAsMention(), activity.getType(), activity.getName(), activity.asRichPresence().getApplicationId())).queue();

    }

    @Override
    public CommandData toCommandData() {
        return Commands
                .slash(getName(), getDescription())
                .addOption(OptionType.USER, "user", "Отметить пользователя");
    }
}
