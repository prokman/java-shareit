package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemCreateRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;

import java.util.Set;

public interface ItemService {
    ItemDto createItem(ItemCreateRequest itemCreateRequest, long userId);

    ItemDto getItem(long itemId, long userId);

    Set<ItemDto> getAllItem(long itemId);

    ItemDto updateItem(ItemUpdateRequest itemUpdateRequest, long itemId, long userId);

    Set<ItemDto> itemSearch(long userId, String searchString);


}
