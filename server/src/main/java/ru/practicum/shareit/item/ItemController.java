package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemDtoForOwner> getAllItem(@RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.getAllItem(ownerId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDtoWithComments getItem(@PathVariable("itemId") long itemId,
                           @RequestHeader("X-Sharer-User-Id") long userId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemDto> itemSearch(@RequestParam(required = false, defaultValue = "") String text,
                                   @RequestHeader("X-Sharer-User-Id") long ownerId) {
        log.info("itemServerSearch with text={}, ownerId={}", text, ownerId);
        return itemService.itemSearch(ownerId, text);
    }

    @PostMapping
    public ItemDto createItem(@RequestBody ItemCreateRequest itemCreateRequest,
                              @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.createItem(itemCreateRequest, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@RequestBody ItemUpdateRequest itemUpdateRequest,
                              @PathVariable long itemId,
                              @RequestHeader("X-Sharer-User-Id") long ownerId) {
        return itemService.updateItem(itemUpdateRequest, itemId, ownerId);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestBody CommentDtoRequest commentDtoRequest,
                                    @PathVariable Long itemId,
                                   @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.createComment(commentDtoRequest, itemId, userId);
    }



}
