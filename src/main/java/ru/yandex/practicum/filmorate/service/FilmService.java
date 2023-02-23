package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;

    public Film createFilm(Film film){
        return filmStorage.createFilm(film);
    }

    public List<Film> findAllFilms(){
        return filmStorage.findAllFilms();
    }

    public Film changeFilm(Film film){
        return filmStorage.changeFilm(film);
    }

    public Film getFilmById(int filmId) {
        return filmStorage.getFilmById(filmId);
    }

    public Film deleteFilm(Film film) {
        return filmStorage.deleteFilm(film);
    }

    public Film addLike(int filmId, int userId){
        checkFilms(filmId);
        filmStorage.getFilmById(filmId).getFilmsLikesUsers().add(userId);
        log.info("Пользователь с id: {} поставил лайк фильму с id: {}", userId, filmId);
        return filmStorage.getFilmById(filmId);
    }

    public Film deleteLike(int filmId, int userId){
        checkFilms(filmId);
        if (!filmStorage.getFilmById(filmId).getFilmsLikesUsers().contains(userId))
            throw new ErrorException("Пользователь не ставил лайк фильму");
        filmStorage.getFilmById(filmId).getFilmsLikesUsers().remove(userId);
        log.info("Пользователь с id: {} удалил лайк у фильма с id: {}", userId, filmId);
        return filmStorage.getFilmById(filmId);
    }

    public List<Film> getPopularFilms(int count){
        return filmStorage.findAllFilms().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getFilmsLikesUsers().size(), o1.getFilmsLikesUsers().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkFilms(int filmId){
        if (!filmStorage.getFilms().containsKey(filmId))
            throw new ErrorException("Фильм с ID: " + filmId + " не найден");
    }


}
