package ru.practicum.shareit.request.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Entity
@Table(name = "requests", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "owner_id", nullable = false)
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;
}

