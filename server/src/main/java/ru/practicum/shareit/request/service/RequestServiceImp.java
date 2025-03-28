package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDtoForRequest;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemAllRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoRequest;
import ru.practicum.shareit.request.dto.RequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.repository.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestServiceImp implements RequestService {
    private final RequestRepository requestRepository;
    private final ItemRepository itemRepository;
    private final UserStorage userStorage;

    @Override
    public List<ItemRequestDto> getRequests(Long userId) {
        boolean userExists = userStorage.existsById(userId);
        if (!userExists) {
            throw new NotFoundException("Пользователь с ид " + userId + " не найден");
        }
        List<ItemRequest> itemRequestList = requestRepository.findByOwnerId(userId);
        List<Long> requestsId = itemRequestList
                .stream()
                .map(ItemRequest::getId)
                .collect(Collectors.toList());
        List<Item> itemList = itemRepository.findByRequestIdIn(requestsId);
        List<ItemRequestDto> itemRequestDtoList = itemRequestList
                .stream()
                .map(itemRequest -> {
                            ItemRequestDto dto = RequestMapper.itemRequestToDto(itemRequest, null);
                            dto.setItems(
                                    itemList
                                            .stream()
                                            .filter(item -> item.getRequestId().equals(dto.getId()))
                                            .map(ItemMapper::itemToDtoForRequest).toList()
                            );
                            return dto;
                        })
                .toList();
        return itemRequestDtoList;
    }

    @Override
    public ItemRequestDto getRequest(Long userId, Long requestId) {
        boolean userExists = userStorage.existsById(userId);
        if (!userExists) {
            throw new NotFoundException("Пользователь с ид " + userId + " не найден");
        }
        ItemRequest itemRequest = requestRepository.findById(requestId)
                .orElseThrow(()->new NotFoundException("Запрос с ид " + requestId + " не найден"));
        List<Item> itemList = itemRepository.findByRequestId(requestId);
        List<ItemDtoForRequest> itemDtoForRequestList = itemList
                .stream()
                .map(ItemMapper::itemToDtoForRequest)
                .toList();
        ItemRequestDto itemRequestDto = RequestMapper.itemRequestToDto(itemRequest, itemDtoForRequestList);
        return itemRequestDto;
    }

    @Override
    public List<ItemAllRequestDto> getAllRequests() {
        return requestRepository.findAll().stream().map(RequestMapper::itemAllRequestDto).toList();
    }

    @Override
    @Transactional
    public ItemRequestDto createItemRequest(Long userId, ItemRequestDtoRequest itemRequestDtoRequest) {
        boolean userExists = userStorage.existsById(userId);
        if (!userExists) {
            throw new NotFoundException("Пользователь с ид " + userId + " не найден");
        }
        ItemRequest itemRequest = requestRepository
                .save(RequestMapper.itemRequestDtoRequestToItemRequest(itemRequestDtoRequest, userId));
        return RequestMapper.itemRequestToDto(itemRequest, null);
    }


}
