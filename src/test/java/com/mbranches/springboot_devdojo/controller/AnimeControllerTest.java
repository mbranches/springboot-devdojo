package com.mbranches.springboot_devdojo.controller;

import com.mbranches.springboot_devdojo.controller.AnimeController;
import com.mbranches.springboot_devdojo.model.Anime;
import com.mbranches.springboot_devdojo.service.AnimeService;
import com.mbranches.springboot_devdojo.util.AnimeCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {
    @InjectMocks //utiliza-se nas classes que eu quero testar
    AnimeController controller;

    @Mock //utiliza-se nas classes que estão dentro da que eu to testando
    private AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage); //quando o metodo list all for chamado dentro do meu anime controller(passando qualquer arg) vai ser retornado animePage
    }

    @Test
    @DisplayName("List return list of anime inside page object when successful")
    void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
        String expectedName = AnimeCreator.createValidAnime().getName();
        Page<Anime> animePage = controller.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList()).isNotNull()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }
}
