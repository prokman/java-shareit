package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.dto.ItemCreateRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemStorageInMemory;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImp implements ItemService {
    private final ItemStorageInMemory itemStorageInMemory;
    private final UserService userService;

    @Override
    public ItemDto createItem(ItemCreateRequest itemCreateRequest, long userId) {
        itemCreateRequest.setUserId(userId);
        UserDto userDto = userService.getUser(userId);
        Item item = ItemMapper.itemCreateRequestToItem(itemCreateRequest);
        return ItemMapper.itemToDto(itemStorageInMemory.createItem(item));
    }

    @Override
    public ItemDto getItem(long itemId, long userId) {
        return ItemMapper.itemToDto(itemStorageInMemory.getItem(itemId, userId).orElseThrow());
    }

    @Override
    public Set<ItemDto> getAllItem(long userId) {
        return itemStorageInMemory.getAllItem(userId)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public ItemDto updateItem(ItemUpdateRequest itemUpdateRequest, long itemId, long userId) {
        itemUpdateRequest.setId(itemId);
        itemUpdateRequest.setUserId(userId);
        Item item = ItemMapper.itemUpdateRequestToItem(itemUpdateRequest);
        return ItemMapper.itemToDto(itemStorageInMemory.updateItem(item));
    }

    @Override
    public Set<ItemDto> itemSearch(long userId, String searchString) {
        return itemStorageInMemory.itemSearch(userId, searchString)
                .stream()
                .map(ItemMapper::itemToDto)
                .collect(Collectors.toSet());
    }
}
