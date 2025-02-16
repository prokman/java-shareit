package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exceptions.DuplicateDataException;
import ru.practicum.shareit.user.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class UserStorageInMemory implements UserStorage {
    private final Map<Long, User> users;
    private final Set<String> emailList;
    private long userId = 0;

    @Override
    public User createUser(User user) {
        boolean emailNotExist = emailList.add(user.getEmail());
        if (!emailNotExist) {
            throw new DuplicateDataException("имэйл-" + user.getEmail() + " уже используется");
        }
        user.setId(++userId);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> getUser(long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public User updateUser(User user) {
        boolean emailNotExist = emailList.add(user.getEmail());
        if (!emailNotExist) {
            throw new DuplicateDataException("имэйл-" + user.getEmail() + " уже используется");
        }
        User existUser = users.get(user.getId());
        if (user.getEmail() != null) existUser.setEmail(user.getEmail());
        if (user.getName() != null) existUser.setName(user.getName());
        users.put(existUser.getId(), existUser);
        return existUser;
    }

    @Override
    public Optional<User> removeUser(long userId) {
        return Optional.ofNullable(users.remove(userId));
    }

    @Override
    public Map<Long, User> getAllUsers() {
        return users;
    }


}
