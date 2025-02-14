package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exceptions.DuplicateDataException;
import ru.practicum.shareit.exceptions.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.dto.UserUpdateRequest;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserStorageInMemory;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStorageInMemory userStorageInMemory;

    @Override
    public UserDto createUser(UserCreateRequest userCreateRequest) {
        User user = UserMapper.userCreateRequestToUser(userCreateRequest);
        isEmailDuplicate(user);
        return UserMapper.UserToDto(userStorageInMemory.createUser(user));
    }

    @Override
    public UserDto getUser(long userId) {
        return userStorageInMemory.getUser(userId)
                .map(UserMapper::UserToDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид " + userId + " не найден"));
    }

    @Override
    public UserDto updateUser(UserUpdateRequest userUpdateRequest, long userId) {
        User newUser = UserMapper.userUpdateRequestToUser(userUpdateRequest, userId);
        isEmailDuplicate(newUser);
        return UserMapper.UserToDto(userStorageInMemory.updateUser(newUser));

    }

    @Override
    public void removeUser(long userId) {
        userStorageInMemory.removeUser(userId);
    }

    private void isEmailDuplicate(User user) {
        boolean emailExist = userStorageInMemory.getAllUsers().values()
                .stream()
                .anyMatch(existUser -> existUser.getEmail().equals(user.getEmail()));

        if (emailExist) {
            throw new DuplicateDataException("имэйл-" + user.getEmail() + " уже используется");
        }
    }


}
