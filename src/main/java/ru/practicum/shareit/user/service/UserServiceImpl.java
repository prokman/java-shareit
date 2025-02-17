package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorage userStorageInMemory;

    @Override
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        User user = UserMapper.userCreateRequestToUser(userCreateRequest);
        return UserMapper.userToDto(userStorageInMemory.createUser(user));
    }

    @Override
    public UserDto getUser(long userId) {
        return userStorageInMemory.getUser(userId)
                .map(UserMapper::userToDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + userId + " не найден"));
    }

    @Override
    public UserDto updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        User newUser = UserMapper.userUpdateRequestToUser(userUpdateRequest, userId);
        return UserMapper.userToDto(userStorageInMemory.updateUser(newUser));

    }

    @Override
    public void removeUser(long userId) {
        userStorageInMemory.removeUser(userId);
    }

}
