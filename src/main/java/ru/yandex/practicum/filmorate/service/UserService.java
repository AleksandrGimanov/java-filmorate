package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        return userStorage.createUser(user);
    }

    public User getUserById(Integer userId) {
        return userStorage.getUserById(userId);
    }

    public User changeUser(User user) {
        return userStorage.changeUser(user);
    }

    public User deleteUser(User user) {
        return userStorage.deleteUser(user);
    }

    public User addFriend(int userId, int friendId) {
        if (userId == friendId)
            throw new ValidationException("Пользователь не может быть другом сам себе");
        getUserById(userId);
        getUserById(friendId);
        userStorage.getUserById(userId).getUsersFriends().add(friendId);
        userStorage.getUserById(friendId).getUsersFriends().add(userId);
        log.info("Пользователи с id: {} и {} стали друзьями", userId, friendId);
        return userStorage.getUserById(userId);
    }

    public User deleteFriend(int userId, int friendId) {
        if (userId == friendId)
            throw new ValidationException("Пользователь не может быть другом сам себе");
        getUserById(userId);
        getUserById(friendId);
        if (!userStorage.getUserById(userId).getUsersFriends().contains(friendId) && !userStorage.getUserById(friendId).getUsersFriends().contains(userId))
            throw new ValidationException("Пользователи не друзья");
        userStorage.getUserById(userId).getUsersFriends().remove(friendId);
        userStorage.getUserById(friendId).getUsersFriends().remove(userId);
        log.info("Пользователи с id: {} и {} больше не друзья", userId, friendId);
        return userStorage.getUserById(userId);
    }

    public List<User> getUserFriends(int userId) {
        getUserById(userId);
        return userStorage.getUserById(userId).getUsersFriends().stream()
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        getUserById(userId);
        getUserById(otherUserId);
        if (userId == otherUserId)
            throw new ValidationException("Пользователь не может быть другом сам себе");
        return userStorage.getUserById(userId).getUsersFriends().stream()
                .filter(friendId -> userStorage.getUserById(otherUserId).getUsersFriends().contains(friendId))
                .map(userStorage::getUserById)
                .collect(Collectors.toList());
    }
}
