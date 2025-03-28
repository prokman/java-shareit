package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.booking.service.BookingDtoResponseComparator;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImp implements ItemService {
    private final ItemRepository itemRepository;
    private final UserStorage userStorage;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public ItemDto createItem(ItemCreateRequest itemCreateRequest, long ownerId) {
        itemCreateRequest.setUserId(ownerId);
        User owner = userStorage.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + ownerId + " не найден"));
        Item item = itemRepository.save(ItemMapper.itemCreateRequestToItem(itemCreateRequest, owner));
        return ItemMapper.itemToDto(item);
    }

    @Override
    public ItemDtoWithComments getItem(long itemId, long userId) {
        if (userStorage.findById(userId).isPresent()) {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new NotFoundException("Предмет с ид " + itemId + " не найден"));
            List<CommentDto> commentDtoList = commentRepository.findByItemId(itemId)
                    .stream()
                    .map(CommentMapper::commentToDto)
                    .toList();
            BookingDtoResponse lastBooking = null;
            BookingDtoResponse nextBooking = null;

            if (item.getOwner().getId().equals(userId)) {
                Set<Booking> bookingList = bookingRepository.findBookingByItemIdUserIdwithItem(userId, itemId);
                Set<BookingDtoResponse> itemBookingListDesc = bookingList
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
                Set<BookingDtoResponse> itemBookingListAsc = bookingList
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator().reversed())
                        .collect(Collectors.toCollection(LinkedHashSet::new));

                LocalDateTime current = LocalDateTime.now();

                for (BookingDtoResponse bookingDto : itemBookingListDesc) {
                    if (bookingDto.getStart().isAfter(current)) {
                        nextBooking = bookingDto;
                    }
                }
                for (BookingDtoResponse bookingDto : itemBookingListAsc) {
                    if (bookingDto.getEnd().isBefore(current)) {
                        lastBooking = bookingDto;
                    }
                }
            }
            return ItemMapper
                    .itemToItemDtoWithComments(item,
                            commentDtoList, lastBooking, nextBooking);
        } else {
            throw new NotFoundException("Пользователь с ид " + userId + " не найден");
        }
    }

    @Override
    public Set<ItemDtoForOwner> getAllItem(long ownerId) {
        Set<Booking> bookingList = bookingRepository.findAllBookingWithItem();
        Set<Item> item = itemRepository.findByOwnerId(ownerId);
        Set<ItemDtoForOwner> itemDtoForOwnerSet = item
                .stream()
                .map(ItemMapper::itemToDtoForOwner)
                .collect(Collectors.toSet());

        Set<BookingDtoResponse> itemBookingListDesc = bookingList
                .stream()
                .map(BookingMapper::bookingToDtoResponse)
                .sorted(new BookingDtoResponseComparator())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        Set<BookingDtoResponse> itemBookingListAsc = bookingList
                .stream()
                .map(BookingMapper::bookingToDtoResponse)
                .sorted(new BookingDtoResponseComparator().reversed())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        BookingDtoResponse lastBooking = null;
        BookingDtoResponse nextBooking = null;

        LocalDateTime current = LocalDateTime.now();

        for (ItemDtoForOwner it : itemDtoForOwnerSet) {
            for (BookingDtoResponse bookingDto : itemBookingListDesc) {
                if (bookingDto.getItem().getId().equals(it.getId()) && bookingDto.getStart().isAfter(current)) {
                    nextBooking = bookingDto;
                }
            }
            for (BookingDtoResponse bookingDto : itemBookingListAsc) {
                if (bookingDto.getItem().getId().equals(it.getId()) && bookingDto.getEnd().isBefore(current)) {
                    lastBooking = bookingDto;
                }
            }
            List<CommentDto> commentDtoList = commentRepository.findByItemId(it.getId())
                    .stream()
                    .map(CommentMapper::commentToDto)
                    .toList();
            it.setNextBooking(nextBooking);
            it.setLastBooking(lastBooking);
            it.setComments(commentDtoList);
        }
        return itemDtoForOwnerSet;
    }

    @Override
    @Transactional
    public ItemDto updateItem(ItemUpdateRequest itemUpdateRequest, long itemId, long ownerId) {
        itemUpdateRequest.setId(itemId);
        itemUpdateRequest.setUserId(ownerId);
        Item existItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с ид " + itemId + " не найден"));
        if (existItem.getOwner().getId() == ownerId) {
            if (itemUpdateRequest.getName() != null) existItem.setName(itemUpdateRequest.getName());
            if (itemUpdateRequest.getDescription() != null)
                existItem.setDescription(itemUpdateRequest.getDescription());
            if (itemUpdateRequest.getAvailable() != null) existItem.setAvailable(itemUpdateRequest.getAvailable());
            return ItemMapper.itemToDto(existItem);
        } else {
            throw new NotFoundException("у пользователя " + ownerId + " нет товара " + itemId);
        }


    }

    @Override
    public Set<ItemDto> itemSearch(long ownerId, String searchString) {
        if (searchString.isEmpty()) {
            return new HashSet<>();
        }
        return itemRepository.itemSearch(searchString.toLowerCase(), ownerId).stream().map(ItemMapper::itemToDto).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public CommentDto createComment(CommentDtoRequest commentDtoRequest, long itemId, long authorId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с ид " + itemId + " не найден"));
        User user = userStorage.findById(authorId)
                .orElseThrow(() -> new BadRequestException("Пользователь с ид " + authorId + " не найден"));
        Set<Booking> bookingListByUserAndItem = bookingRepository.findByBookerIdAndItemId(authorId, itemId);
        LocalDateTime current = LocalDateTime.now();
        Boolean finishedRent = bookingListByUserAndItem.stream()
                .anyMatch(booking -> booking.getEndDate().isBefore(current));
        if (finishedRent) {
            Comment comment = commentRepository
                    .save(CommentMapper.commentDtoRequestToComment(commentDtoRequest, item, user));
            return CommentMapper.commentToDto(comment);
        } else {
            throw new BadRequestException("Пользователь с ид "
                    + authorId + " не завершил аренду предмета " + itemId +
                    " на текущий момент " + current);
        }
    }
}
