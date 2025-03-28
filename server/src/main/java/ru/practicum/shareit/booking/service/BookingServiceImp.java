package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exceptions.BadRequestException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserStorage userStorage;


    @Override
    @Transactional
    public BookingDtoResponse createBooking(BookingDtoRequest bookingDtoRequest, Long bookerId) {
        Item bookedItem = itemRepository.findById(bookingDtoRequest.getItemId())
                .orElseThrow(() -> new NotFoundException("Предмет с ид " + bookingDtoRequest.getItemId() + " не найден"));
        User booker = userStorage.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + bookerId + " не найден"));
        if (bookedItem.getAvailable()) {

            Booking booking = BookingMapper.bookingDtoToBooking(bookingDtoRequest, booker, bookedItem);
            bookingRepository.save(booking);
            return BookingMapper.bookingToDtoResponse(booking);
        } else {
            throw new BadRequestException("предмет - " + bookingDtoRequest.getItemId() + " не доступен для бронирования");
        }
    }

    @Override
    @Transactional
    public BookingDtoResponse setBookingStatus(Long bookingId, Boolean isApproved, Long ownerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("бронирование - " + bookingId + " не найдено"));
        User owner = userStorage.findById(ownerId)
                .orElseThrow(() -> new BadRequestException("Пользователь с ид " + ownerId + " не найден"));
        if (booking.getBookedItem().getOwner().getId().equals(ownerId)) {
            if (isApproved) {
                booking.setStatus(BookingStatus.APPROVED);
            } else {
                booking.setStatus(BookingStatus.REJECTED);
            }
            bookingRepository.save(booking);
            return BookingMapper.bookingToDtoResponse(booking);
        } else {
            throw new BadRequestException("пользователь - " + owner.getId() +
                    " не является владелец предмета " + bookingId);
        }
    }

    @Override
    public BookingDtoResponse getBookingByBookerOrOwner(Long bookingId, Long bookerOrOwnerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("бронирование - " + bookingId + " не найдено"));
        User user = userStorage.findById(bookerOrOwnerId)
                .orElseThrow(() -> new BadRequestException("Пользователь с ид " + bookerOrOwnerId + " не найден"));
        if (booking.getBooker().getId().equals(bookerOrOwnerId)
                || booking.getBookedItem().getOwner().getId().equals(bookerOrOwnerId)) {
            return BookingMapper.bookingToDtoResponse(booking);
        } else {
            throw new BadRequestException("пользователь - " + bookerOrOwnerId +
                    " не является автором бронирования " + bookingId + " или владельцем предмета "
                    + booking.getBookedItem().getId());
        }
    }

    @Override
    public Set<BookingDtoResponse> getAllBookingsCurrentUser(Long currentUser, BookingState bookingState) {
        User user = userStorage.findById(currentUser)
                .orElseThrow(() -> new BadRequestException("Пользователь с ид " + currentUser + " не найден"));
        switch (bookingState) {
            case ALL: {
                return bookingRepository.findByBookerId(currentUser)
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            }
            case PAST:
                return bookingRepository.findByBookerIdAndEndDateLessThan(currentUser, LocalDateTime.now())
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            case FUTURE:
                return bookingRepository.findByBookerIdAndStartDateLaterThan(currentUser, LocalDateTime.now())
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            case CURRENT:
                return bookingRepository.findByBookerIdAndCurrent(currentUser, LocalDateTime.now())
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            case WAITING:
                return bookingRepository.findByBookerIdAndStatus(currentUser, BookingStatus.WAITING)
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            case REJECTED:
                return bookingRepository.findByBookerIdAndStatus(currentUser, BookingStatus.REJECTED)
                        .stream()
                        .map(BookingMapper::bookingToDtoResponse)
                        .sorted(new BookingDtoResponseComparator())
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            default: {
                throw new BadRequestException("некорректный параметр запроса");
            }
        }
    }

    @Override
    public Set<BookingDtoResponse> getAllOwnerBookings(Long ownerId, BookingState bookingState) {
        User user = userStorage.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + ownerId + " не найден"));
        Set<Booking> bookingList = bookingRepository.findAllBookingWithItem();
        Set<Item> itemList = itemRepository.findByOwnerId(ownerId);
        switch (bookingState) {
            case ALL: {
                return bookingList
                        .stream()
                        .filter(booking -> itemList.stream()
                                .anyMatch(item -> item.getId().equals(booking.getBookedItem().getId())))
                        .map(BookingMapper::bookingToDtoResponse)
                        .collect(Collectors.toSet());
            }
            case PAST:
                return bookingList
                        .stream()
                        .filter(booking -> itemList.stream()
                                .anyMatch(item -> item.getId().equals(booking.getBookedItem().getId())))
                        .map(BookingMapper::bookingToDtoResponse)
                        .filter(bookingDto -> bookingDto.getEnd().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toSet());
            case FUTURE:
                return bookingList
                        .stream()
                        .filter(booking -> itemList.stream()
                                .anyMatch(item -> item.getId().equals(booking.getBookedItem().getId())))
                        .map(BookingMapper::bookingToDtoResponse)
                        .filter(bookingDto -> bookingDto.getStart().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toSet());
            case CURRENT:
                return bookingList
                        .stream()
                        .filter(booking -> itemList.stream()
                                .anyMatch(item -> item.getId().equals(booking.getBookedItem().getId())))
                        .map(BookingMapper::bookingToDtoResponse)
                        .filter(bookingDto -> bookingDto.getStart().isBefore(LocalDateTime.now())
                                && bookingDto.getEnd().isAfter(LocalDateTime.now()))
                        .collect(Collectors.toSet());
            case WAITING:
                return bookingList
                        .stream()
                        .filter(booking -> itemList.stream()
                                .anyMatch(item -> item.getId().equals(booking.getBookedItem().getId())))
                        .map(BookingMapper::bookingToDtoResponse)
                        .filter(bookingDto -> bookingDto.getStatus().equals(BookingStatus.WAITING))
                        .collect(Collectors.toSet());
            case REJECTED:
                return bookingList
                        .stream()
                        .filter(booking -> itemList.stream()
                                .anyMatch(item -> item.getId().equals(booking.getBookedItem().getId())))
                        .map(BookingMapper::bookingToDtoResponse)
                        .filter(bookingDto -> bookingDto.getStatus().equals(BookingStatus.REJECTED))
                        .collect(Collectors.toSet());
            default: {
                throw new BadRequestException("некорректный параметр запроса");
            }
        }
    }
}
