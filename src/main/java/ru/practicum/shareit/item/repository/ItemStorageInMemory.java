package ru.practicum.shareit.item.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;


import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ItemStorageInMemory implements ItemRepository {
    private final Map<Long, Map<Long, Item>> items;
    private long itemId = 0;

    @Override
    public Item createItem(Item item) {
        item.setId(++itemId);
        items.computeIfAbsent(item.getUserId(), key -> new HashMap<>()).put(item.getId(), item);
        items.computeIfPresent(item.getUserId(), (key, value) -> value).put(item.getId(), item);
        return item;
    }

    @Override
    public Optional<Item> getItem(long itemId, long userId) {
        return Optional.of(items.get(userId).get(itemId));
    }

    @Override
    public Set<Item> getAllItem(long userId) {
        return new HashSet<>(items.get(userId).values());
    }

    @Override
    public Item updateItem(Item item) {
        Item existItem = null;
        if (items.get(item.getUserId()) != null) {
            if (items.get(item.getUserId()).get(item.getId()) != null) {
                existItem = items.get(item.getUserId()).get(item.getId());
            }
        }

        if (existItem != null) {
            if (item.getDescription() != null) existItem.setDescription(item.getDescription());
            if (item.getName() != null) existItem.setName(item.getName());
            if (item.getAvailable() != null) existItem.setAvailable(item.getAvailable());
            items.computeIfPresent(item.getUserId(), (key, value) -> value).put(existItem.getId(), existItem);
            return existItem;
        } else {
            throw new NotFoundException("у пользователя " + item.getUserId() + " нет товара " + item.getId());
        }

    }

    @Override
    public Set<Item> itemSearch(long userId, String searchString) {

        if (searchString.isEmpty()) return new HashSet<>();


        Set<Item> searchInName = items.get(userId).values()
                .stream()
                .filter(item -> item.getName().toLowerCase().indexOf(searchString.toLowerCase()) > -1)
                .filter(item -> item.getAvailable())
                .collect(Collectors.toSet());
        Set<Item> searchInDescription = items.get(userId).values()
                .stream()
                .filter(item -> item.getName().toLowerCase().indexOf(searchString.toLowerCase()) > -1)
                .filter(item -> item.getAvailable())
                .collect(Collectors.toSet());
        Set<Item> serachResult = new HashSet<>(searchInName);
        serachResult.addAll(searchInDescription);
        return serachResult;
    }
}
