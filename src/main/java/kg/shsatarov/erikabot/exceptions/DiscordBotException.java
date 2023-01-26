package kg.shsatarov.erikabot.exceptions;

import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.Getter;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public class DiscordBotException extends RuntimeException {

    @Getter
    private IReplyCallback replyCallback;

    public DiscordBotException(String message) {
        super(message);
    }

    public DiscordBotException(String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments));
    }

    public DiscordBotException(IReplyCallback replyCallback, String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments));
        this.replyCallback = replyCallback;
    }

    public DiscordBotException(Throwable cause, String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments), cause);
    }

}
