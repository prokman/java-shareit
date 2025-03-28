package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemAllRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDtoRequest;

import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @GetMapping
    public ResponseEntity<Object> getRequests (@Valid @RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        return itemRequestClient.getRequests(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequest (@Valid @RequestHeader("X-Sharer-User-Id") @Positive @NotNull long userId,
                                      @Valid @PathVariable("requestId") @Positive @NotNull long requestId) {
        return itemRequestClient.getRequest(userId, requestId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequest () {
        return itemRequestClient.getAllRequests();
    }

    @PostMapping
    public ResponseEntity<Object> createItemRequest (@Valid @RequestHeader("X-Sharer-User-Id") @Positive @NotNull long userId,
                                             @Valid @RequestBody ItemRequestDtoRequest itemRequestDtoRequest) {
        return itemRequestClient.createItemRequest(userId, itemRequestDtoRequest);
    }

}

