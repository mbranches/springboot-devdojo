package com.mbranches.springboot_devdojo.controller;

import com.mbranches.springboot_devdojo.model.Anime;
import com.mbranches.springboot_devdojo.requests.AnimePostRequestBody;
import com.mbranches.springboot_devdojo.requests.AnimePutRequestBody;
import com.mbranches.springboot_devdojo.service.AnimeService;
import com.mbranches.springboot_devdojo.util.AnimeCreator;
import com.mbranches.springboot_devdojo.util.AnimePostRequestBodyCreator;
import com.mbranches.springboot_devdojo.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {
    @InjectMocks //utiliza-se nas classes que eu quero testar
    AnimeController controller;

    @Mock //utiliza-se nas classes que estão dentro da que eu to testando
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        List<Anime> animeList = List.of(AnimeCreator.createValidAnime());
        PageImpl<Anime> animePage = new PageImpl<>(animeList);

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage); //quando o metodo list all for chamado dentro do meu anime controller(passando qualquer arg) vai ser retornado animePage

        BDDMockito.when(animeServiceMock.listAllNonPage())
                .thenReturn(animeList);

        BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list return list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = controller.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotNull()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAll return list of anime when successful")
    void listAll_ReturnsListOfAnime_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        List<Anime> animeList = controller.listAll().getBody();

        Assertions.assertThat(animeList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById return list of anime when successful")
    void findById_ReturnsListOfAnime_WhenSuccessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        Anime returnedAnime = controller.findById(expectedAnime.getId()).getBody();

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .isEqualTo(expectedAnime);

    }

    @Test
    @DisplayName("findByName return list of anime when successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        List<Anime> returnedAnime = controller.findByName(expectedAnime.getName()).getBody();

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(returnedAnime.get(0))
                .isEqualTo(expectedAnime);
    }

    @Test
    @DisplayName("findByName return empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeNotFound() {
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());
        List<Anime> returnedAnime = controller.findByName("XAXAxixiXOXO").getBody();

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save return anime when successful")
    void save_ReturnsAnime_WhenSuccessful() {
        Anime expectedAnime = AnimeCreator.createValidAnime();
        Anime returnedAnime = controller.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(returnedAnime)
                .isNotNull()
                .isEqualTo(expectedAnime);

    }

    @Test
    @DisplayName("replace updates anime when successful")
    void replace_UpdatesAnime_WhenSuccessful() {
        ResponseEntity<Void> entity = controller.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes anime when successful")
    void delete_RemovesAnime_WhenSuccessful() {
        ResponseEntity<Void> entity = controller.delete(1);

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
