package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDtoResponse;

import java.util.Comparator;

public class BookingDtoResponseComparator implements Comparator<BookingDtoResponse> {


    @Override
    public int compare(BookingDtoResponse o1, BookingDtoResponse o2) {
        return o2.getStart().compareTo(o1.getStart());
    }
}
