package ru.yandex.practicum.filmorate.controllerTests;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.contoller.UserController;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends UserController {

    UserController userController = new UserController();

    @Test
    void shouldGetAllUsers() {
        User user1 = User.builder()
                .name("Александр")
                .email("ALex@gmail.com")
                .login("Aleks77")
                .birthday(LocalDate.of(2000, 12, 20))
                .build();
        User user2 = User.builder()
                .name("Сергей")
                .email("Sergo23@gmail.com")
                .login("Sergo")
                .birthday(LocalDate.of(2010, 5, 7))
                .build();
        userController.createUser(user1);
        userController.createUser(user2);
        assertEquals(2, userController.findAllUsers().size());
    }

    @Test
    void shouldCreateUserWithEmptyName() {
        User user = User.builder()
                .name("   ")
                .email("Fedor@gmail.com")
                .login("federic")
                .birthday(LocalDate.of(1997, 6, 23))
                .build();
        userController.createUser(user);
        assertEquals("federic",user.getName());
    }
}
