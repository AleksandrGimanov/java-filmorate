package ru.yandex.practicum.filmorate.storage.user.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Exception.ErrorException;
import ru.yandex.practicum.filmorate.mapper.MapperUser;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.sql.Date;
import java.sql.PreparedStatement;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    MapperUser mapperUser;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate, MapperUser mapperUser) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapperUser = mapperUser;
    }

    @Override
    public List<User> findAllUsers() {
        final String sqlQuery = "SELECT * FROM users";

        log.info("Список пользователей отправлен");
        return jdbcTemplate.query(sqlQuery,mapperUser);
    }


    @Override
    public User createUser(User user) {
        final String sqlQuery = "INSERT INTO users (EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "VALUES ( ?, ?, ?, ?)";
        KeyHolder generatedId = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            final PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getLogin());
            stmt.setString(3, user.getName());
            stmt.setDate(4, Date.valueOf(user.getBirthday()));
            return stmt;
        }, generatedId);

        log.info("Пользователь с id {} отправлен", user.getId());
        user.setId(Objects.requireNonNull(generatedId.getKey()).intValue());
        return user;
    }

    @Override
    public User changeUser(User user) {
        getUserById(user.getId());
        final String sqlQuery = "UPDATE users " +
                "SET name = ?, login = ?, email = ?, birthday = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(sqlQuery,
                user.getName(),
                user.getLogin(),
                user.getEmail(),
                user.getBirthday(),
                user.getId());
        log.info("Пользователь {} обновлен", user.getId());
        return user;
    }

    @Override
    public User deleteUser(int userId) {
        User user = getUserById(userId);
        final String sqlQuery = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sqlQuery, userId);
        log.info("Пользователь с id {} удален", userId);
        return user;
    }

    @Override
    public User getUserById(int userId) {
        final String sqlQuery = "SELECT * FROM users WHERE id = ?";
        SqlRowSet userRows = jdbcTemplate.queryForRowSet(sqlQuery, userId);

        if (userRows.next()) {
            log.info("Пользователь с id {} найден", userId);
            return jdbcTemplate.queryForObject(sqlQuery,mapperUser, userId);

        } else {
            log.warn("Пользователь с идентификатором {} не найден.", userId);
            throw new ErrorException("Пользователь не найден");
        }

    }

    @Override
    public void addFriend(int firstId, int secondId) {
        getUserById(firstId);
        getUserById(secondId);
        final String sqlQuery = "INSERT INTO friends (user_id, friend_id) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sqlQuery, firstId, secondId);
        log.info("Пользователь {} подписался на {}", firstId, secondId);
    }

    @Override
    public void deleteFriend(int firstId, int secondId) {
        getUserById(firstId);
        getUserById(secondId);
        final String sqlQuery = "DELETE FROM friends WHERE user_id = ? AND friend_id = ?";
        jdbcTemplate.update(sqlQuery, firstId, secondId);
        log.info("Пользователь {} отписался от {}", firstId, secondId);
    }

    @Override
    public List<User> getUserFriends(int id) {
        getUserById(id);
        final String sqlQuery = "select u.id, u.email, u.name, u.login, u.birthday " +
                "from friends as f left join users as u " +
                "on f.friend_id = u.id where f.user_id = ?" +
                "order by u.id";

        log.info("Запрос получения списка друзей пользователя {} выполнен", id);
        return jdbcTemplate.query(sqlQuery,mapperUser, id);
    }


    @Override
    public List<User> getCommonFriends(int firstId, int secondId) {
        getUserById(firstId);
        getUserById(secondId);
        final String sqlQuery = "select u.id, u.name, u.email, u.login, u.birthday " +
                "from friends as f " +
                "left join users as u on f.friend_id = u.id " +
                "where f.user_id = ? " +
                "and f.friend_id in (select friend_id from friends where user_id = ?) ";

        log.info("Список общих друзей {} и {} отправлен", firstId, secondId);
        return jdbcTemplate.query(sqlQuery,mapperUser, firstId, secondId);
    }

}
