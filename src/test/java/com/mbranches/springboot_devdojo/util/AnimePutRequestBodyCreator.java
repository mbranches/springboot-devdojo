package com.mbranches.springboot_devdojo.util;

import com.mbranches.springboot_devdojo.model.Anime;
import com.mbranches.springboot_devdojo.requests.AnimePostRequestBody;
import com.mbranches.springboot_devdojo.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {
    public static AnimePutRequestBody createAnimePutRequestBody() {
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidUpdateAnime().getId())
                .name(AnimeCreator.createValidUpdateAnime().getName())
                .build();
    }
}
