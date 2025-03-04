package com.mbranches.springboot_devdojo.service;

import com.mbranches.springboot_devdojo.controller.AnimeController;
import com.mbranches.springboot_devdojo.exception.BadRequestException;
import com.mbranches.springboot_devdojo.model.Anime;
import com.mbranches.springboot_devdojo.repository.AnimeRepository;
import com.mbranches.springboot_devdojo.requests.AnimePostRequestBody;
import com.mbranches.springboot_devdojo.requests.AnimePutRequestBody;
import com.mbranches.springboot_devdojo.util.AnimeCreator;
import com.mbranches.springboot_devdojo.util.AnimePostRequestBodyCreator;
import com.mbranches.springboot_devdojo.util.AnimePutRequestBodyCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {
    @InjectMocks //utiliza-se nas classes que eu quero testar
    AnimeService service;

    @Mock //utiliza-se nas classes que estão dentro da que eu to testando
    private AnimeRepository repository;

    @BeforeEach
    void setUp() {
        List<Anime> animeList = List.of(AnimeCreator.createValidAnime());
        PageImpl<Anime> animePage = new PageImpl<>(animeList);

        BDDMockito.when(repository.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        BDDMockito.when(repository.findAll())
                .thenReturn(animeList);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(repository.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(repository.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(repository).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("listAll return list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = service.listAll(PageRequest.of(1, 1));

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotNull()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPage return list of anime when successful")
    void listAllNonPage_ReturnsListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = service.listAllNonPage();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException return list of anime when successful")
    void findByIdOrThrowBadRequestException_ReturnsListOfAnime_WhenSuccessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        Anime returnedAnime = repository.findById(expectedAnime.getId()).get();

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .isEqualTo(expectedAnime);

    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException return BadRequestException when id not found")
    void findByIdOrThrowBadRequestException_ReturnsBadRequestException_WhenIdNotFound() {
        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

//        Assertions.assertThatExceptionOfType(BadRequestException.class)
//                .isThrownBy(() -> service.findByIdOrThrowBadRequestException(12))
//                .withMessageContaining("Anime not Found");

        Assertions.assertThatThrownBy(() -> service.findByIdOrThrowBadRequestException(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Anime not Found");
    }

    @Test
    @DisplayName("findByName return list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        List<Anime> returnedAnime = service.findByName(expectedAnime.getName());

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(returnedAnime.get(0))
                .isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findByName return empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeNotFound() {
        BDDMockito.when(repository.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        List<Anime> returnedAnime = service.findByName("XAXAxixiXOXO");

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        Anime returnedAnime = service.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .isEqualTo(expectedAnime);

    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> service.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        Assertions.assertThatCode(() -> service.delete(1))
                .doesNotThrowAnyException();
    }
}