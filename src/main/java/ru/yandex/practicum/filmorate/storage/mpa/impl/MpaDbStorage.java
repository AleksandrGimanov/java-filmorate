package ru.yandex.practicum.filmorate.storage.mpa.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.mapper.MapperMpa;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.mpa.MpaStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Repository
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;
    private final MapperMpa mapperMpa;

    @Autowired
    public MpaDbStorage(JdbcTemplate jdbcTemplate, MapperMpa mapperMpa) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapperMpa = mapperMpa;
    }

    @Override
    public Collection<Mpa> findAll() {
        String sql = "SELECT * FROM mpa";

        return jdbcTemplate.query(sql, mapperMpa);
    }

    @Override
    public Mpa getById(int id) {
        String sql = "SELECT * FROM mpa WHERE id = ?";
        SqlRowSet mpaRows = jdbcTemplate.queryForRowSet(sql, id);

        if (!mpaRows.next()) {
            log.warn("Рейтинг {} не найден.", id);
            throw new ErrorException("Рейтинг не найден");
        }

        return jdbcTemplate.queryForObject(sql, mapperMpa, id);
    }

    @Override
    public Mpa getByFilmId(int id) {
        String sqlQuery = "select m.id, m.name " +
                "from MPA AS m " +
                "join Films AS f ON m.ID = f.MPA_ID " +
                "where f.ID = ?";
        List<Mpa> mpas = jdbcTemplate.query(sqlQuery,mapperMpa, id);
        if (mpas.size() != 1) {
            return null;
        }
        return mpas.get(0);
    }

}
