package com.mbranches.springboot_devdojo.repository;

import com.mbranches.springboot_devdojo.model.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@Log4j2
@DataJpaTest
@DisplayName("Test for anime repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        Assertions.assertThat(animeSaved).isNotNull();

        Assertions.assertThat(animeSaved.getId()).isNotNull();

        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when Successful")
    void save_UpdateAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        animeSaved.setName("DragonBall Z");

        Anime animeUpdated = this.animeRepository.save(animeSaved);

        Assertions.assertThat(animeUpdated).isNotNull();

        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());

        Assertions.assertThat(animeUpdated.getId()).isEqualTo(animeSaved.getId());

    }

    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemoveAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();

        Anime animeToBeDeleted = this.animeRepository.save(animeToBeSaved);

        this.animeRepository.delete(animeToBeDeleted);

        Optional<Anime> animeOptional = this.animeRepository.findById(animeToBeDeleted.getId());

        Assertions.assertThat(animeOptional).isEmpty();
    }

    @Test
    @DisplayName("Find by name returns list of anime when Successful")
    void findByName_ReturnListOfAnime_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();

        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        String name = animeSaved.getName();

        List<Anime> animes = this.animeRepository.findByName(name);

        Assertions.assertThat(animes).isNotEmpty();

        Assertions.assertThat(animes).contains(animeSaved);
    }


        @Test
        @DisplayName("Find by name returns list empty when no anime is found")
        void findByName_ReturnListEmpty_WhenAnimeIsNotFound() {
            List<Anime> animes = this.animeRepository.findByName("###xxxYYYzzz");

            Assertions.assertThat(animes).isEmpty();
        }

    private Anime createAnime() {
        return Anime.builder()
                .name("Naruto")
                .build();
    }
}