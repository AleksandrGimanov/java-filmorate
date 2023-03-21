package ru.yandex.practicum.filmorate.storage.genre.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.mapper.MapperGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.Collection;

import java.util.List;


@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;
    MapperGenre mapperGenre;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate, MapperGenre mapperGenre) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapperGenre = mapperGenre;
    }

    @Override
    public Collection<Genre> findAll() {
        String sql = "SELECT * FROM genre";
        return jdbcTemplate.query(sql,mapperGenre);
    }

    @Override
    public Genre getById(int id) {
        final String sql = "SELECT * FROM genre WHERE id = ?";
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet(sql, id);

        if (!genreRows.next()) {
            log.warn("Жанр {} не найден.", id);
            throw new ErrorException("Жанр не найден");
        }
        return jdbcTemplate.queryForObject(sql,mapperGenre, id);
    }

    @Override
    public List<Genre> getByFilmId(int id) {
        String sqlQuery = "SELECT genre.id, genre.name " +
                "FROM genre " +
                "LEFT JOIN film_genre FG on genre.id = FG.genre_id " +
                "WHERE film_id = ?";
        return jdbcTemplate.query(sqlQuery,mapperGenre, id);
    }
}
