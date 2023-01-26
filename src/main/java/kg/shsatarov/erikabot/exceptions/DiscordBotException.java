package kg.shsatarov.erikabot.exceptions;

import kg.shsatarov.erikabot.utils.StringFormatter;

public class DiscordBotException extends RuntimeException {

    public DiscordBotException(String message) {
        super(message);
    }

    public DiscordBotException(String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments));
    }

    public DiscordBotException(Throwable cause, String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments), cause);
    }

}
