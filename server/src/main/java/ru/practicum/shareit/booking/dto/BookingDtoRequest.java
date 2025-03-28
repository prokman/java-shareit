package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDtoRequest {
    private LocalDateTime start;


    private LocalDateTime end;


    private Long itemId;


    boolean isStartDateBeforeEndDate() {
        return start.isBefore(end);
    }
}
