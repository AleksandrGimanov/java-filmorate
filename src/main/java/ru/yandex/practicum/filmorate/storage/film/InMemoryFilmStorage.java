package ru.yandex.practicum.filmorate.storage.film;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @Override
    public List<Film> findAllFilms() {
        log.info("Текущее количество фильмов: {}", films.size());
        return List.copyOf(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        log.info("проверка создания фильма: {} ", film);
        filmValidation(film);
        film.setId(generatorId());
        films.put(film.getId(), film);
        log.info("Фильм добавлен: {} ", film);
        return film;
    }

    @Override
    public Film changeFilm(Film film) {
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

    @Override
    public Film deleteFilm(Film film) {
        log.info("проверка удаления фильма: {} ", film);
        if (films.containsKey(film.getId())) {
            films.remove(film.getId(), film);
            log.info("фильм удален: {} ", film);
            return  film;
        } else {
            log.debug("фильм не найден");
            throw new ErrorException("фильм не найден");
        }
    }

    @Override
    public Film getFilmById(int filmId) {
        if (films.containsKey(filmId))
            return films.get(filmId);
        else
            throw new ErrorException("Фильм с данным " + filmId + " не найден" );
    }


    private int generatorId() {
        return id++;
    }

    public void filmValidation(Film film) {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
    }
    @Override
    public Map<Integer, Film> getFilms(){
        return films;
    }


}
