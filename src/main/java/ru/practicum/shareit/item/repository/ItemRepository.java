package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.Optional;
import java.util.Set;

public interface ItemRepository {

    Item createItem(Item item);

    Optional<Item> getItem(long itemId, long userId);

    Set<Item> getAllItem(long userId);

    Item updateItem(Item item);

    Set<Item> itemSearch(long userId, String searchString);
}
