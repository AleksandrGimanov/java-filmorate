package ru.yandex.practicum.filmorate.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genres")
    public Collection<Genre> findAll() {
        return genreService.findAll();
    }

    @GetMapping("/genres/{id}")
    public Genre getById(@PathVariable int id) {
        return genreService.getById(id);
    }
}