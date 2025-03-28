package ru.practicum.shareit.request.dto;

import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.request.model.ItemRequest;

import java.time.LocalDateTime;
import java.util.List;

public class RequestMapper {
    public static ItemRequest itemRequestDtoRequestToItemRequest(ItemRequestDtoRequest itemRequestDtoRequest, Long userId) {
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(itemRequestDtoRequest.getDescription());
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setOwnerId(userId);
        return itemRequest;
    }

    public static ItemRequestDto itemRequestToDto (ItemRequest itemRequest, List<ItemDtoForRequest> listOfItemsOffer) {
        ItemRequestDto itemRequestDto = new ItemRequestDto();
        itemRequestDto.setId(itemRequest.getId());
        itemRequestDto.setDescription(itemRequest.getDescription());
        itemRequestDto.setCreated(itemRequest.getCreated());
        itemRequestDto.setItems(listOfItemsOffer);
        return itemRequestDto;
    }

    public static ItemAllRequestDto itemAllRequestDto (ItemRequest itemRequest) {
        ItemAllRequestDto itemAllRequestDto = new ItemAllRequestDto();
        itemAllRequestDto.setId(itemRequest.getId());
        itemAllRequestDto.setDescription(itemRequest.getDescription());
        itemAllRequestDto.setCreated(itemRequest.getCreated());
        return itemAllRequestDto;
    }

}
