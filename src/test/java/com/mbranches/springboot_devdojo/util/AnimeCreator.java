package com.mbranches.springboot_devdojo.util;

import com.mbranches.springboot_devdojo.model.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Naruto")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Naruto")
                .id(1L)
                .build();
    }

    public static Anime createValidUpdateAnime() {
        return Anime.builder()
                .name("Naruto 22222")
                .id(1L)
                .build();
    }
}
