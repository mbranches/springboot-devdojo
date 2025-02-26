package com.mbranches.springboot_devdojo.service;

import com.mbranches.springboot_devdojo.model.Anime;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AnimeService {
    public List<Anime> listAll() {
        return Arrays.asList(new Anime(1L, "Naruto"), new Anime(2L, "Bersek"), new Anime(3L, "Boruto"));
    }
}
