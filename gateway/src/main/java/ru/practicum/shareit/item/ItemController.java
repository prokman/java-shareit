package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDtoRequest;
import ru.practicum.shareit.item.dto.ItemCreateRequest;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@Slf4j
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @GetMapping
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getAllItem(@Valid @RequestHeader("X-Sharer-User-Id") @Positive long ownerId) {
        return itemClient.getAllItem(ownerId);
    }

    @GetMapping("/{itemId}")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getItem(@Valid @PathVariable("itemId") @Positive @NotNull long itemId,
                           @Valid @RequestHeader("X-Sharer-User-Id") @Positive @NotNull long userId) {
        return itemClient.getItem(itemId, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> itemSearch(@RequestParam(required = false, defaultValue = "") String text,
                                   @Valid @RequestHeader("X-Sharer-User-Id") @Positive long ownerId) {
        log.info("itemSearch with text {}, ownerId={}", text, ownerId);
        return itemClient.itemSearch(ownerId, text);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest,
                              @Valid @RequestHeader("X-Sharer-User-Id") @Positive @NotNull long ownerId) {
        return itemClient.createItem(itemCreateRequest, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest,
                              @PathVariable @Positive long itemId,
                              @Valid @RequestHeader("X-Sharer-User-Id") @Positive long ownerId) {
        return itemClient.updateItem(itemUpdateRequest, itemId, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestBody CommentDtoRequest commentDtoRequest,
                                    @PathVariable @NotNull @Positive Long itemId,
                                    @Valid @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemClient.createComment(commentDtoRequest, itemId, userId);
    }



}
