package com.mbranches.springboot_devdojo.client;

import com.mbranches.springboot_devdojo.model.Anime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SpringClient {
    public static void main(String[] args) {
//        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8090/animes/2", Anime.class);
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8090/animes/{id}", Anime.class, 2);
        log.info(String.valueOf(entity));

        Anime obj = new RestTemplate().getForObject("http://localhost:8090/animes/2", Anime.class);
        System.out.println(obj);
    }
}
