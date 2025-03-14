package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import ru.practicum.shareit.item.service.ItemService;

import java.util.Set;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemDtoForOwner> getAllItem(@Valid @RequestHeader("X-Sharer-User-Id") @Positive long ownerId) {
        return itemService.getAllItem(ownerId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoWithComments getItem(@Valid @PathVariable("itemId") @Positive @NotNull long itemId,
                           @Valid @RequestHeader("X-Sharer-User-Id") @Positive @NotNull long userId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemDto> itemSearch(@RequestParam(required = false, defaultValue = "") String text,
                                   @Valid @RequestHeader("X-Sharer-User-Id") @Positive long ownerId) {
        return itemService.itemSearch(ownerId, text);
    }

    @PostMapping
    public ItemDto createItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest,
                              @Valid @RequestHeader("X-Sharer-User-Id") @Positive @NotNull long ownerId) {
        return itemService.createItem(itemCreateRequest, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest,
                              @PathVariable @Positive long itemId,
                              @Valid @RequestHeader("X-Sharer-User-Id") @Positive long ownerId) {
        return itemService.updateItem(itemUpdateRequest, itemId, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestBody CommentDtoRequest commentDtoRequest,
                                    @PathVariable @NotNull @Positive Long itemId,
                                    @Valid @RequestHeader("X-Sharer-User-Id") @Positive Long userId) {
        return itemService.createComment(commentDtoRequest, itemId, userId);
    }



}
