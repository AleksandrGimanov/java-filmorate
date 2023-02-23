package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;

public interface FilmStorage {

    Film createFilm(Film film);

    List<Film> findAllFilms();

    Film changeFilm(Film film);

    Film deleteFilm(Film film);

    Film getFilmById(int filmId);

    Map<Integer, Film> getFilms();

}
