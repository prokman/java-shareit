package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String name;
    @Email(message = "Неверный формат email")
    private String email;
}
