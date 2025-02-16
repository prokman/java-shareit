package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateRequest;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateRequest;

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
    public Set<ItemDto> getAllItem(@Valid @RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        return itemService.getAllItem(userId);
    }

    @GetMapping("/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    public ItemDto getItem(@Valid @PathVariable("itemId") @Positive long itemId,
                           @Valid @RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        return itemService.getItem(itemId, userId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Set<ItemDto> itemSearch(@RequestParam(required = false, defaultValue = "") String text,
                                   @Valid @RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        return itemService.itemSearch(userId, text);
    }

    @PostMapping
    public ItemDto createItem(@Valid @RequestBody ItemCreateRequest itemCreateRequest,
                              @Valid @RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        return itemService.createItem(itemCreateRequest, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto updateItem(@Valid @RequestBody ItemUpdateRequest itemUpdateRequest,
                              @PathVariable @Positive long itemId,
                              @Valid @RequestHeader("X-Sharer-User-Id") @Positive long userId) {
        return itemService.updateItem(itemUpdateRequest, itemId, userId);
    }


}
