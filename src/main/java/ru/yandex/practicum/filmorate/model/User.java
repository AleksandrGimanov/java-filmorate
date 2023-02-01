package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.message.ExceptionMessage;

import javax.validation.constraints.*;

import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    @Email(message = ExceptionMessage.INCORRECT_EMAIL)
    private String email;
    @NotBlank(message = ExceptionMessage.EMPTY_LOGIN)
    @Pattern(regexp = ".*\\S.",message = ExceptionMessage.LOGIN_WITHOUT_SPACE)
    private String login;
    private String name;
    @PastOrPresent(message = ExceptionMessage.INCORRECT_BIRTHDAY)
    private LocalDate birthday;
}
