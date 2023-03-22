package ru.yandex.practicum.filmorate.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Genre {
    private int id;
    private String name;
}
