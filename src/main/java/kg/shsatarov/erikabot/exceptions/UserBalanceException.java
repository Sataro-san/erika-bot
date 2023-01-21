package kg.shsatarov.erikabot.exceptions;

import kg.shsatarov.erikabot.utils.StringFormatter;

public class UserBalanceException extends RuntimeException {

    public UserBalanceException(String message) {
        super(message);
    }

    public UserBalanceException(String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments));
    }

    public UserBalanceException(Throwable cause, String pattern, Object... arguments) {
        super(StringFormatter.format(pattern, arguments), cause);
    }

}
