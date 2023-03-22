package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {

    Film createFilm(Film film);

    Collection<Film> findAllFilms();

    Film changeFilm(Film film);

    Film deleteFilm(int filmId);

    Film getFilmById(int filmId);

    void addLike(int filmId, int userId);

    void deleteLike(int filmId, int userId);

    List<Film> getPopularFilms(int count);


}
