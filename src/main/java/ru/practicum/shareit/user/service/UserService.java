package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserUpdateRequest;

public interface UserService {
    UserDto createUser(UserCreateRequest userCreateRequest);

    UserDto getUser(long userId);

    UserDto updateUser(UserUpdateRequest userUpdateRequest, long userId);

    void removeUser(long userId);

}
