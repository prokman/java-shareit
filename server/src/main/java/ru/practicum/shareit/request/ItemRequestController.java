package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;
import ru.practicum.shareit.request.dto.ItemAllRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoRequest;
import ru.practicum.shareit.request.service.RequestService;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final RequestService requestService;

    @GetMapping
    public List<ItemRequestDto> getRequests (@RequestHeader("X-Sharer-User-Id") long userId) {
        return requestService.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequest (@RequestHeader("X-Sharer-User-Id") long userId,
                                      @PathVariable("requestId") long requestId) {
        return requestService.getRequest(userId, requestId);
    }

    @GetMapping("/all")
    public List<ItemAllRequestDto> getAllRequest () {
        return requestService.getAllRequests();
    }

    @PostMapping
    public ItemRequestDto createItemRequest (@RequestHeader("X-Sharer-User-Id") long userId,
                                             @RequestBody ItemRequestDtoRequest itemRequestDtoRequest) {
        return requestService.createItemRequest(userId, itemRequestDtoRequest);
    }

}

