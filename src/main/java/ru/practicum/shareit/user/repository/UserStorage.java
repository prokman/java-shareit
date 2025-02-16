package ru.practicum.shareit.user.repository;

import ru.practicum.shareit.user.model.User;

import java.util.Map;
import java.util.Optional;

public interface UserStorage {

    User createUser(User user);

    Optional<User> getUser(long userId);

    User updateUser(User user);

    Optional<User> removeUser(long userId);

    Map<Long, User> getAllUsers();

}
