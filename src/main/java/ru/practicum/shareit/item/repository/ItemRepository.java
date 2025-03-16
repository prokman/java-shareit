package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.Set;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> { //, QuerydslPredicateExecutor<Item>

    @EntityGraph(attributePaths = {"owner"})
    Set<Item> findByOwnerId(Long ownerId);

    @Query("select item from Item item" +
            " where item.available = true " +
            " and item.owner.id = :ownerId" +
            " and (LOWER(item.name) like LOWER(:searchString)" +
            " or LOWER(item.description) like LOWER(:searchString))"
            )
    Set<Item> itemSearch(String searchString, long ownerId);


//    Item createItem(Item item);
//
//    Optional<Item> getItem(long itemId, long userId);
//
//    Set<Item> getAllItem(long userId);
//
//    Item updateItem(Item item);
//
//    Set<Item> itemSearch(long userId, String searchString);
}
