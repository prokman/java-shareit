package ru.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class ItemUpdateRequest {


    private long id;

    private long userId;

    private String name;
    private String description;
    private Boolean available;
}
