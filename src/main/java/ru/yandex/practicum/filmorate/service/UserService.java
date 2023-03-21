package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public List<User> findAllUsers() {
        return userStorage.findAllUsers();
    }

    public User createUser(User user) {
        validate(user);
        return userStorage.createUser(user);
    }

    public User getUserById(Integer userId) {
        return userStorage.getUserById(userId);
    }

    public User changeUser(User user) {
        validate(user);
        return userStorage.changeUser(user);
    }

    public User deleteUser(int id) {
        return userStorage.deleteUser(id);
    }

    public void addFriend(int userId, int friendId) {
         userStorage.addFriend(userId,friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        userStorage.deleteFriend(userId,friendId);
    }

    public List<User> getUserFriends(int userId) {
        return userStorage.getUserFriends(userId);
    }

    public List<User> getCommonFriends(int userId, int otherUserId) {
        return userStorage.getCommonFriends(userId,otherUserId);
    }

    private void validate(User user) {
        if (user.getName() == null || user.getName().isBlank()) user.setName(user.getLogin());
    }


}
