package ru.practicum.shareit.booking.dto;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

public class BookingMapper {
    public static Booking bookingDtoToBooking(BookingDtoRequest bookingDtoRequest, User booker, Item bookedItem) {
        Booking booking = new Booking();
        booking.setStartDate(bookingDtoRequest.getStart());
        booking.setEndDate(bookingDtoRequest.getEnd());
        booking.setBookedItem(bookedItem);
        booking.setBooker(booker);
        booking.setStatus(BookingStatus.WAITING);
        return booking;
    }

    public static BookingDtoResponse bookingToDtoResponse(Booking booking) {
        BookingDtoResponse bookingDtoCreateResponse = new BookingDtoResponse(booking.getId(),
                booking.getStartDate(), booking.getEndDate(), booking.getStatus(),
                new BookingDtoResponse
                        .ItemDto(booking.getBookedItem().getId(), booking.getBookedItem().getName()),
                new BookingDtoResponse.UserDto(booking.getBooker().getId())
        );
        return bookingDtoCreateResponse;
    }
//    public static BookingDtoPatchResponse bookingToPatchResponse (Booking booking) {
//        BookingDtoPatchResponse bookingDtoPatchResponse = new BookingDtoPatchResponse(booking.getId(),
//                booking.getStartDate(), booking.getEndDate(), booking.getStatus(),
//                new BookingDtoResponse
//                        .ItemDto(booking.getBookedItem().getId(), booking.getBookedItem().getName()),
//                new BookingDtoResponse.UserDto(booking.getBooker().getId())
//        );
//        return bookingDtoPatchResponse;
//    }

}
