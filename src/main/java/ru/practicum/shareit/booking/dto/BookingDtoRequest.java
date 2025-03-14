package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
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
    @FutureOrPresent
    private LocalDateTime start;

    @Future
    private LocalDateTime end;

    @Positive
    private Long itemId;

    @AssertTrue
    boolean isStartDateBeforeEndDate() {
        return start.isBefore(end);
    }
}
