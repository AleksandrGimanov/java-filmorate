package ru.yandex.practicum.filmorate.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private int id = 1;

    private int generatorId() {
        return id++;
    }

    @GetMapping("/users")
    public List<User> findAllUsers() {
        log.info("Текущее количество пользователей: {}", users.size());
        return List.copyOf(users.values());
    }

    @PostMapping(value = "/users")
    public User createUser(@Valid @RequestBody User user) {
        userValidation(user);
        user.setId(generatorId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен: {} ", user);
        return user;
    }

    @PutMapping(value = "/users")
    public User ChangeUser(@Valid @RequestBody User user) {
        log.info("Проверка обновления: {} ", user);
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.replace(user.getId(), user);
            log.info("Данные пользователя изменены: {} ", user);
        }else{
            throw new ValidationException("пользователь не найден");
        }
        return user;
    }

    public void userValidation(User user) {

        if (user.getName() == null || user.getName().equals(" ") || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}