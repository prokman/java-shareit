package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorage;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Override
    @Transactional
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        User user = UserMapper.userCreateRequestToUser(userCreateRequest);
        return UserMapper.userToDto(userStorage.save(user));
    }

    @Override
    public UserDto getUser(long userId) {
        return userStorage.findById(userId)
                .map(UserMapper::userToDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + userId + " не найден"));
    }

    @Override
    @Transactional
    public UserDto updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        User existUser = userStorage.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + userId + " не найден"));
        if (userUpdateRequest.getEmail() != null) existUser.setEmail(userUpdateRequest.getEmail());
        if (userUpdateRequest.getName() != null) existUser.setName(userUpdateRequest.getName());
        return UserMapper.userToDto(userStorage.save(existUser));
    }

    @Override
    public void removeUser(long userId) {
        userStorage.deleteById(userId);
    }

}
