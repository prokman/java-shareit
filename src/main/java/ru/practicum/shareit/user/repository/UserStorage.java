package ru.practicum.shareit.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.user.model.User;



public interface UserStorage extends JpaRepository<User, Long> {

//    User createUser(User user);
//
//    Optional<User> getUser(long userId);
//
//    User updateUser(User user);
//
//    Optional<User> removeUser(long userId);
//
//    Map<Long, User> getAllUsers();

}
