package ru.yandex.practicum.filmorate.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class MpaController {

    private final MpaService mpaService;

    @GetMapping("/mpa")
    public Collection<Mpa> findAll() {
        return mpaService.findAll();
    }

    @GetMapping("/mpa/{id}")
    public Mpa getById(@PathVariable int id) {
        return mpaService.getById(id);
    }
}