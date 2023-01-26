package kg.shsatarov.erikabot.commands;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;

import java.util.List;

/**
 * any implementing class must be @Bean
 **/
public interface ModalComponent {

    String getModalId();

    String getModalTitle();

    List<ActionRow> getModalActionRows();

    void onModalSubmit(ModalInteractionEvent modalEvent);

}
