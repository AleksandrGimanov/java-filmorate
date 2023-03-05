package ru.yandex.practicum.filmorate.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    private int generatorId() {
        return id++;
    }

    @GetMapping
    public List<Film> findAllFilms() {
        log.info("Текущее количество фильмов: {}", films.size());
        return List.copyOf(films.values());
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("проверка: {} ", film);
        filmValidation(film);
        film.setId(generatorId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {} ", film);
        return film;
    }

    @PutMapping
    public Film changeFilm(@Valid @RequestBody Film film) {
        log.info("проверка обновления: {} ", film);
        if (films.containsKey(film.getId())) {
            filmValidation(film);
            films.replace(film.getId(), film);
            log.info("описание фильма изменено: {} ", film);
        } else {
            log.debug("фильм не найден");
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
