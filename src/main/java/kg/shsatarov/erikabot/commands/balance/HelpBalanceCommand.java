package kg.shsatarov.erikabot.commands.balance;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Slf4j
public class HelpBalanceCommand implements ExecutableCommand {
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

        slashCommandEvent
                .reply(StringFormatter.format("{}\n:coin: На сервере установлена валюта - ***Канабаксы*** (KBS)\n\n:watch: *Валюта начисляется раз в 30 секунд при соблюдении следующих условий:*\n  1. У вас должна быть роль ***Чумба***\n  2. Необходимо включить активности в Discord (чтобы бот мог собирать информацию)\n  3. Нужно быть онлайн\n  4. Нужно быть в голосовом канале, в котом больше одного пользователя\n\n:video_game: За каждую активность (игру) валюта начисляется по разному, для просмотра тарификаций введите команду `/activity-rates`", slashCommandEvent.getMember().getAsMention()))
                .queue();

    }
}
