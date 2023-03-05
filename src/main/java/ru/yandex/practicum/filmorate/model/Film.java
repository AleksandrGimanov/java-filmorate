package ru.yandex.practicum.filmorate.model;
import ru.yandex.practicum.filmorate.message.ExceptionMessage;

import lombok.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private int id;
    @NotBlank(message = ExceptionMessage.EMPTY_NAME)
    private String name;
    @Size(max = 200, message = ExceptionMessage.MAX_DESCRIPTION_SIZE)
    private String description;
    private LocalDate releaseDate;
    @PositiveOrZero(message = ExceptionMessage.POSITIVE_DURATION)
    private long duration;
}
