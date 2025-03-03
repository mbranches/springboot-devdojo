package com.mbranches.springboot_devdojo.service;

import com.mbranches.springboot_devdojo.exception.BadRequestException;
import com.mbranches.springboot_devdojo.mapper.AnimeMapper;
import com.mbranches.springboot_devdojo.model.Anime;
import com.mbranches.springboot_devdojo.repository.AnimeRepository;
import com.mbranches.springboot_devdojo.requests.AnimePostRequestBody;
import com.mbranches.springboot_devdojo.requests.AnimePutRequestBody;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {
    private final AnimeRepository animeRepository;

    //Permite navegações do tipo: http://localhost:8090/animes?size=5&page=2
    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> listAllNonPage() {
        return animeRepository.findAll();
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    @Transactional //por padrao exceptions checked não dao rollback
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    public void delete(long id) {
        animeRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        findByIdOrThrowBadRequestException(animePutRequestBody.getId());

        animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePutRequestBody));
    }
}
