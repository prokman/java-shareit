package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.util.Set;

public interface ItemService {
    ItemDto createItem(ItemCreateRequest itemCreateRequest, long userId);

    ItemDtoWithComments getItem(long itemId, long userId);

    Set<ItemDtoForOwner> getAllItem(long itemId);

    ItemDto updateItem(ItemUpdateRequest itemUpdateRequest, long itemId, long userId);

    Set<ItemDto> itemSearch(long userId, String searchString);

    CommentDto createComment(CommentDtoRequest commentDtoRequest, long itemId, long authorId);


}
