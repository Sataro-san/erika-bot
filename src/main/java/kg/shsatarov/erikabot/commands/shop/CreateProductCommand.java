package kg.shsatarov.erikabot.commands.shop;

import jakarta.annotation.PostConstruct;
import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.commands.ModalComponent;
import kg.shsatarov.erikabot.entities.shop.Product;
import kg.shsatarov.erikabot.exceptions.DiscordBotException;
import kg.shsatarov.erikabot.services.shop.ProductService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateProductCommand implements ExecutableCommand, ModalComponent {
    private TextInput productNameInput;
    private TextInput productPriceInput;

    private final ProductService productService;

    @PostConstruct
    private void initialize() {

        productNameInput = TextInput
                .create("product-name-input", "Наименование товара", TextInputStyle.SHORT)
                .setRequired(true)
                .setMinLength(3)
                .setMaxLength(20)
                .build();

        productPriceInput = TextInput
                .create("product-price-input", "Цена товара", TextInputStyle.SHORT)
                .setPlaceholder("Например: 3.14 или 50")
                .setRequired(true)
                .build();
    }

    @Override
    public String getName() {
        return "product-create";
    }

    @Override
    public String getDescription() {
        return "Форма создания товара";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        slashCommandEvent
                .replyModal(Modal.create(getModalId(), getModalTitle()).addActionRows(getModalActionRows()).build())
                .queue();
    }


    //modal component
    @Override
    public String getModalId() {
        return "product-create-modal";
    }

    @Override
    public String getModalTitle() {
        return "Создание товара";
    }

    @Override
    public List<ActionRow> getModalActionRows() {

        return List.of(
                ActionRow.of(productNameInput),
                ActionRow.of(productPriceInput)
        );
    }

    @Override
    public void onModalSubmit(ModalInteractionEvent modalEvent) {

        String productName = modalEvent.getValue(productNameInput.getId()).getAsString();
        String productPriceString = modalEvent.getValue(productPriceInput.getId()).getAsString();

        try {
            BigDecimal productPrice = new BigDecimal(productPriceString);

            Product product = new Product();
            product.setName(productName);
            product.setPrice(productPrice);
            product.setDiscordGuildId(modalEvent.getGuild().getId());

            productService.saveProduct(product);

            modalEvent
                    .reply(StringFormatter.format("Товар {} с ценой {} создан!", productName, productPrice))
                    .queue();

        } catch (NumberFormatException e) {
            throw new DiscordBotException(modalEvent, "{}\nНе удалось создать товар \"{}\", невалидное значение цены: {}", modalEvent.getMember().getAsMention(), productName, productPriceString);
        }

    }
}
