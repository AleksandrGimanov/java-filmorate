package ru.yandex.practicum.filmorate.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Genre {
    private Integer id;
    private String name;
}
