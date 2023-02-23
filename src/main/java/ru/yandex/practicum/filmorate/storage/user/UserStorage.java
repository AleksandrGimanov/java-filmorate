package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;

public interface UserStorage {

    List<User> findAllUsers();

    User createUser(User user);

    User changeUser(User user);

    User deleteUser(User user);

    User getUserById(int userId);

    Map<Integer, User> getUsers();
}
