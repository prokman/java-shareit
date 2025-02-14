package ru.practicum.shareit.item.dto;


import ru.practicum.shareit.item.model.Item;


public class ItemMapper {
    public static ItemDto ItemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static Item ItemCreateRequestToItem(ItemCreateRequest itemCreateRequest) {
        Item item = new Item();
        item.setUserId(itemCreateRequest.getUserId());
        item.setName(itemCreateRequest.getName());
        item.setDescription(itemCreateRequest.getDescription());
        item.setAvailable(itemCreateRequest.getAvailable());
        return item;
    }

    public static Item ItemUpdateRequestToItem(ItemUpdateRequest itemUpdateRequest) {
        Item item = new Item();
        item.setId(itemUpdateRequest.getId());
        item.setUserId(itemUpdateRequest.getUserId());
        item.setName(itemUpdateRequest.getName());
        item.setDescription(itemUpdateRequest.getDescription());
        item.setAvailable(itemUpdateRequest.getAvailable());
        return item;
    }

}
