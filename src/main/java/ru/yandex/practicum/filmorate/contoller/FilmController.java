package ru.yandex.practicum.filmorate.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);
    private int id = 1;

    private int generatorId() {
        return id++;
    }

    @GetMapping("/films")
    public List<Film> findAllFilms() {
        log.info("Текущее количество фильмов: {}", films.size());
        return List.copyOf(films.values());
    }

    @PostMapping(value = "/films")
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("проверка: {} ", film);
        filmValidation(film);
        film.setId(generatorId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {} ", film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film changeFilm(@Valid @RequestBody Film film) {
        log.info("проверка обновления: {} ", film);
        if (films.containsKey(film.getId())) {
            filmValidation(film);
            films.replace(film.getId(), film);
            log.info("описание фильма изменено: {} ", film);
        } else {
            throw new ValidationException("фильм не найден");
        }
        return film;
    }

    public void filmValidation(Film film) {


        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
    }
}
