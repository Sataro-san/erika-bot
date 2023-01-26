package kg.shsatarov.erikabot.listeners;

import kg.shsatarov.erikabot.commands.ModalComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ModalListener extends ListenerAdapter {

    private final List<ModalComponent> modalComponents;

    @Override
    public void onModalInteraction(ModalInteractionEvent modalEvent) {

        modalComponents.stream()
                .filter(modalComponent -> modalEvent.getModalId().equals(modalComponent.getModalId()))
                .findFirst()
                .ifPresent(modalComponent -> modalComponent.onModalSubmit(modalEvent));

    }
}
