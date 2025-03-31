package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemAllRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoRequest;

import java.util.List;

public interface RequestService {
    List<ItemRequestDto> getRequests(Long userId);

    ItemRequestDto getRequest(Long userId, Long requestId);

    ItemRequestDto createItemRequest(Long userId, ItemRequestDtoRequest itemRequestDtoRequest);

    List<ItemAllRequestDto> getAllRequests();

}
