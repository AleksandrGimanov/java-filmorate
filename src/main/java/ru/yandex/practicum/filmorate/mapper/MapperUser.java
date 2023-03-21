package ru.yandex.practicum.filmorate.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
@AllArgsConstructor
public class MapperUser implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet ResultSet, int rowNum) throws SQLException {
        return User.builder()
                .id(ResultSet.getInt("id"))
                .email(ResultSet.getString("email"))
                .login(ResultSet.getString("login"))
                .name(ResultSet.getString("name"))
                .birthday(ResultSet.getDate("birthday").toLocalDate())
                .build();
    }
}