package kg.shsatarov.erikabot.commands;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@NoArgsConstructor
@Slf4j
public class EmbedTestCommand implements ExecutableCommand {
    @Override
    public String getName() {
        return "embed-test";
    }

    @Override
    public String getDescription() {
        return "Проверка embed";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("EmbedTest command", "");
        embedBuilder.setColor(new Color(102, 0, 204));
        embedBuilder.setDescription("Some description");
        embedBuilder.addField("Title of field", "test of field", false);
        embedBuilder.addBlankField(false);
        embedBuilder.setAuthor("Erika", null, "https://cdn.discordapp.com/avatars/1065668722183917673/083f6ff1d16d038050a7e4d7b5faf3b2.png?size=256");
        embedBuilder.setFooter("Footer text", "https://cdn.discordapp.com/avatars/1065668722183917673/083f6ff1d16d038050a7e4d7b5faf3b2.png?size=256");
        embedBuilder.setImage("https://cdn.discordapp.com/avatars/1065668722183917673/083f6ff1d16d038050a7e4d7b5faf3b2.png?size=256");
        embedBuilder.setThumbnail("https://cdn.discordapp.com/avatars/1065668722183917673/083f6ff1d16d038050a7e4d7b5faf3b2.png?size=256");

        slashCommandEvent
                .replyEmbeds(embedBuilder.build())
                .queue();
    }
}
