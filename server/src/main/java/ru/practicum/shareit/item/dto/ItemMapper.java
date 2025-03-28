package ru.practicum.shareit.item.dto;


import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;


public class ItemMapper {
    public static ItemDto itemToDto(Item item) {
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setDescription(item.getDescription());
        itemDto.setAvailable(item.getAvailable());
        return itemDto;
    }

    public static Item itemCreateRequestToItem(ItemCreateRequest itemCreateRequest, User owner) {
        Item item = new Item();
        item.setOwner(owner);
        item.setName(itemCreateRequest.getName());
        item.setDescription(itemCreateRequest.getDescription());
        item.setAvailable(itemCreateRequest.getAvailable());
        if (itemCreateRequest.getRequestId()!=null) {
            item.setRequestId(itemCreateRequest.getRequestId());
        }
        return item;
    }

    public static Item itemUpdateRequestToItem(ItemUpdateRequest itemUpdateRequest, User owner) {
        Item item = new Item();
        item.setId(itemUpdateRequest.getId());
        item.setOwner(owner);
        item.setName(itemUpdateRequest.getName());
        item.setDescription(itemUpdateRequest.getDescription());
        item.setAvailable(itemUpdateRequest.getAvailable());
        return item;
    }

    public static ItemDtoForOwner itemToDtoForOwner(Item item) {
        ItemDtoForOwner itemDtoForOwner = new ItemDtoForOwner();
        itemDtoForOwner.setId(item.getId());
        itemDtoForOwner.setName(item.getName());
        itemDtoForOwner.setDescription(item.getDescription());
        itemDtoForOwner.setAvailable(item.getAvailable());
        return itemDtoForOwner;
    }

    public static ItemDtoWithComments itemToItemDtoWithComments(Item item,
                                                                List<CommentDto> commentDtoList,
                                                                BookingDtoResponse lastBooking,
                                                                BookingDtoResponse nextBooking) {
        ItemDtoWithComments itemDtoWithComments = new ItemDtoWithComments();
        itemDtoWithComments.setId(item.getId());
        itemDtoWithComments.setName(item.getName());
        itemDtoWithComments.setDescription(item.getDescription());
        itemDtoWithComments.setAvailable(item.getAvailable());
        itemDtoWithComments.setComments(commentDtoList);
        itemDtoWithComments.setLastBooking(lastBooking);
        itemDtoWithComments.setNextBooking(nextBooking);

        return itemDtoWithComments;
    }

    public static ItemDtoForRequest itemToDtoForRequest (Item item) { //Long ownerId
        ItemDtoForRequest itemDtoForRequest = new ItemDtoForRequest();
        itemDtoForRequest.setId(item.getId());
        itemDtoForRequest.setName(item.getName());
        itemDtoForRequest.setOwnerId(item.getOwner().getId());
        return itemDtoForRequest;
    }

}
