package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * TODO Sprint add-controllers.
 */
@Data
public class Item {

    @Positive
    private long id;
    @NotBlank(message = "userId не может быть пустым")
    private long userId;
    @NotBlank(message = "name не может быть пустым")
    private String name;
    @NotBlank(message = "description не может быть пустым")
    private String description;
    @NotNull
    @NotBlank(message = "available не может быть пустым")
    Boolean available;
}
