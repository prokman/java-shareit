package ru.practicum.shareit.booking.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @EntityGraph(attributePaths = {"booker"})
    Set<Booking> findByBookerId(Long bookerId);

    @Query("select booking from Booking booking " +
            " where booking.booker.id = :bookerId " +
            " and booking.endDate < :current"
    )
    Set<Booking> findByBookerIdAndEndDateLessThan(Long bookerId, LocalDateTime current);

    @Query("select booking from Booking booking " +
            " where booking.booker.id = :bookerId " +
            " and booking.startDate > :current"
    )
    Set<Booking> findByBookerIdAndStartDateLaterThan(Long bookerId, LocalDateTime current);

    @Query("select booking from Booking booking " +
            " where booking.booker.id = :bookerId " +
            " and booking.startDate < :current" +
            " and booking.endDate > :current"
    )
    Set<Booking> findByBookerIdAndCurrent(Long bookerId, LocalDateTime current);

    @Query("select booking from Booking booking " +
            " where booking.booker.id = :bookerId " +
            " and booking.status = :bookingStatus"
    )
    Set<Booking> findByBookerIdAndStatus(Long bookerId, BookingStatus bookingStatus);

    @Query("select booking from Booking booking " +
            " JOIN FETCH booking.bookedItem "

    )
    Set<Booking> findAllBookingWithItem();

    @Query("select booking from Booking booking " +
            " where booking.booker.id = :bookerId " +
            " and booking.bookedItem.id = :itemId"
    )
    Set<Booking> findByBookerIdAndItemId(Long bookerId, Long itemId);

    @Query("select booking from Booking booking " +
            " JOIN FETCH booking.bookedItem" +
            " Where booking.bookedItem.id = :itemId" +
            " And booking.booker.id = :bookerId"
    )
    Set<Booking> findBookingByItemIdUserIdwithItem(Long bookerId, Long itemId);




//    @Query("select booking from Bookings booking " +
//            " where booking.status = :status " +
//            " and booking.id = :bookerId"
//    )
}
