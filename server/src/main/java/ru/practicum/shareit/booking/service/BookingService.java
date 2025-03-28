package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.Set;

public interface BookingService {

    BookingDtoResponse createBooking(BookingDtoRequest bookingDto, Long bookerId);

    BookingDtoResponse setBookingStatus(Long bookingId, Boolean isApproved, Long ownerId);

    BookingDtoResponse getBookingByBookerOrOwner(Long bookingId, Long bookerOrOwnerId);

    Set<BookingDtoResponse> getAllBookingsCurrentUser(Long currentUser, BookingState bookingState);

    Set<BookingDtoResponse> getAllOwnerBookings(Long ownerId, BookingState bookingState);
}
