package ru.practicum.shareit.item.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateRequest {


    private long userId;


    private String name;


    private String description;


    private Boolean available;

    private Long requestId;

}
