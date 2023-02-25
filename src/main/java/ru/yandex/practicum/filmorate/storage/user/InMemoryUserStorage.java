package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generatorId() {
        return id++;
    }

    public void userValidation(User user) {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            user.setName(user.getLogin());
        }
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Текущее количество пользователей: {}", users.size());
        return List.copyOf(users.values());
    }

    @Override
    public User createUser(User user) {
        log.info("проверка создания пользователя: {} ", user);
        userValidation(user);
        user.setId(generatorId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен: {} ", user);
        return user;
    }

    @Override
    public User changeUser(User user) {
        log.info("Проверка обновления данных пользователя: {} ", user);
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.replace(user.getId(), user);
            log.info("Данные пользователя изменены: {} ", user);
        } else {
            log.debug("пользователь не найден");
            throw new ErrorException("пользователь не найден");
        }
        return user;
    }

    @Override
    public User deleteUser(User user) {
        log.info("Проверка удаления данных пользователя: {} ", user);
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.remove(user.getId(), user);
            log.info("пользователь удален: {} ", user);
            return user;
        } else {
            log.debug("пользователь не найден");
            throw new ErrorException("пользователь не найден");
        }
    }

    @Override
    public User getUserById(int userId) {
        if (users.containsKey(userId))
            return users.get(userId);
        else
            throw new ErrorException("Пользователь с " + userId + " не найден");
    }

}
