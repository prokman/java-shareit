package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDtoResponse;
import ru.practicum.shareit.booking.dto.BookingDtoRequest;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.Set;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDtoResponse createBooking(@Valid @RequestBody BookingDtoRequest bookingDtoRequest,
                                            @RequestHeader("X-Sharer-User-Id") @Positive Long bookerId) {
        BookingDtoResponse resp = bookingService.createBooking(bookingDtoRequest, bookerId);
        return resp;
    }

    @PatchMapping("/{bookingId}")
    public BookingDtoResponse setBookingStatus(@PathVariable @Positive @NotNull Long bookingId,
                                                    @RequestParam(required = true) @NotNull Boolean approved,
                                                    @RequestHeader("X-Sharer-User-Id") @Positive Long ownerId) {
        return bookingService.setBookingStatus(bookingId, approved, ownerId);
    }

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    BookingDtoResponse getBookingByBookerOrOwner(@PathVariable @Positive @NotNull Long bookingId,
                                                 @RequestHeader("X-Sharer-User-Id") @NotNull @Positive Long bookerOrOwnerId) {
        return bookingService.getBookingByBookerOrOwner(bookingId, bookerOrOwnerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Set<BookingDtoResponse> getAllBookingsCurrentUser(@RequestParam(required = false, defaultValue = "ALL") BookingState state,
                                                      @RequestHeader("X-Sharer-User-Id") @NotNull @Positive Long currentUser) {
        return bookingService.getAllBookingsCurrentUser(currentUser, state);
    }

    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK)
    Set<BookingDtoResponse> getAllOwnerBookings(@RequestParam(required = false, defaultValue = "ALL") BookingState bookingState,
                                                @RequestHeader("X-Sharer-User-Id") @Positive Long ownerId) {
        return bookingService.getAllOwnerBookings(ownerId, bookingState);
    }

}
