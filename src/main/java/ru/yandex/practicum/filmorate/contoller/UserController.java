package ru.yandex.practicum.filmorate.contoller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    private int generatorId() {
        return id++;
    }

    @GetMapping
    public List<User> findAllUsers() {
        log.info("Текущее количество пользователей: {}", users.size());
        return List.copyOf(users.values());
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        userValidation(user);
        user.setId(generatorId());
        users.put(user.getId(), user);
        log.info("Пользователь сохранен: {} ", user);
        return user;
    }

    @PutMapping
    public User ChangeUser(@Valid @RequestBody User user) {
        log.info("Проверка обновления: {} ", user);
        if (users.containsKey(user.getId())) {
            userValidation(user);
            users.replace(user.getId(), user);
            log.info("Данные пользователя изменены: {} ", user);
        }else{
            log.debug("пользователь не найден");
            throw new ValidationException("пользователь не найден");
        }
        return user;
    }

    public void userValidation(User user) {

        if (user.getName() == null || user.getName().trim().isEmpty()) {
            user.setName(user.getLogin());
        }
    }
}