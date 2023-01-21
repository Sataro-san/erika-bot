package kg.shsatarov.erikabot.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringFormatter {

    public String format(String messagePattern, Object... arguments) {

        return org.slf4j.helpers.MessageFormatter.arrayFormat(messagePattern, arguments).getMessage();
    }

    public String fromMentionedToId(String mentioned) {
        return mentioned.replaceAll("<@", "").replaceAll(">", "");
    }
}
