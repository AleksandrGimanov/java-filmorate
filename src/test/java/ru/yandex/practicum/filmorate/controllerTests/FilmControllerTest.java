package ru.yandex.practicum.filmorate.controllerTests;

import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.contoller.FilmController;
import ru.yandex.practicum.filmorate.model.Film;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;


import java.time.LocalDate;
import java.util.Collection;

public class FilmControllerTest  {
    InMemoryFilmStorage filmController = new InMemoryFilmStorage();

    @Test
    void shouldGetAllFilms() {
        Film film1 = Film.builder()
                .name("Властелин колец")
                .description("Что-то про кольцо")
                .releaseDate(LocalDate.now())
                .duration(600)
                .build();
        Film film2 = Film.builder()
                .name("Хоббит")
                .description("легенда о карапузе")
                .releaseDate(LocalDate.now())
                .duration(500)
                .build();
        filmController.createFilm(film1);
        filmController.createFilm(film2);
        assertEquals(2,filmController.findAllFilms().size());
    }

    @Test
    void shouldCreateFilmWithIncorrectDate() {
        Film film = Film.builder()
                .name("Чаки")
                .description("про куклу")
                .releaseDate(LocalDate.of(1600,5,13))
                .duration(100)
                .build();
        ValidationException ex = assertThrows(ValidationException.class, () -> filmController.createFilm(film));
        assertEquals("дата релиза — не раньше 28 декабря 1895 года", ex.getMessage());
    }

    @Test
    void shouldUpdateFilm() {
        Film film1 = Film.builder()
                .name("Мэган")
                .description("про маленького киборга")
                .releaseDate(LocalDate.now())
                .duration(100)
                .build();
        Film film2 = Film.builder()
                .name("Я робот")
                .description("про большого киборга")
                .releaseDate(LocalDate.now())
                .duration(200)
                .build();

        filmController.createFilm(film1);
        film2.setId(film1.getId());
        filmController.changeFilm(film2);
        Collection<Film> actualFilms = filmController.findAllFilms();
        assertEquals(1, actualFilms.size());
        assertEquals(film2, actualFilms.iterator().next());
    }

    @Test
    void shouldUpdateFilmWithIncorrectId(){
        Film film1 = Film.builder()
                .name("ТБВ")
                .description("сериал про физиков")
                .releaseDate(LocalDate.now())
                .duration(1000)
                .build();
        Film film2 = Film.builder()
                .name("Клиника")
                .description("сериал про врачей")
                .releaseDate(LocalDate.now())
                .duration(2000)
                .build();
        filmController.createFilm(film1);
        film2.setId(777);
        ValidationException ex = assertThrows(ValidationException.class, () -> filmController.changeFilm(film2));
        assertEquals("фильм не найден", ex.getMessage());
    }
}
