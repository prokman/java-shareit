package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserUpdateRequest;


/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    final UserClient userClient;

    @GetMapping("/{userId}")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> getUser(@PathVariable("userId") @Positive long userId) {
        return userClient.getUser(userId);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return userClient.createUser(userCreateRequest);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
                                             @PathVariable long userId) {
        return userClient.updateUser(userUpdateRequest, userId);
    }

    @DeleteMapping("/{userId}")
    //@ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUser(@PathVariable("userId") @Positive long userId) {
        userClient.removeUser(userId);
    }
}
