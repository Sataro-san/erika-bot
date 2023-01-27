package kg.shsatarov.erikabot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class GlobalExceptionHandler {

    @Pointcut("within(kg.shsatarov.erikabot..*)")
    public void discordBotExceptionAwareMethods() {}

    @Around("discordBotExceptionAwareMethods()")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            return joinPoint.proceed();
        } catch (DiscordBotException e) {
            log.error(e.getMessage());
            e.getReplyCallback().reply(e.getMessage()).queue();
            throw e;
        }

    }

}
