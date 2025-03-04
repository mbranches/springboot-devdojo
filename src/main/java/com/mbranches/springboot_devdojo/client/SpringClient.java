package com.mbranches.springboot_devdojo.client;

import com.mbranches.springboot_devdojo.model.Anime;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {
    public static void main(String[] args) {
//        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8090/animes/2", Anime.class);
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8090/animes/{id}", Anime.class, 2);
        log.info(String.valueOf(entity));

        Anime[] obj = new RestTemplate().getForObject("http://localhost:8090/animes/all", Anime[].class);
        log.info(Arrays.toString(obj));

        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8090/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        log.info(exchange.getBody());

//        Anime kingdom = Anime.builder().name("Kingdom").build();
//        Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8090/animes", kingdom, Anime.class);
//        log.info("Saved anime {}", kingdomSaved);

//        Anime samuraiChamploo = Anime.builder().name("Samurai Champloo").build();
//        ResponseEntity<Anime> samuraiChamplooSaved = new RestTemplate().exchange("http://localhost:8090/animes", HttpMethod.POST, new HttpEntity<>(samuraiChamploo), Anime.class);
//        log.info("Saved anime {}", samuraiChamplooSaved);
//
//        Anime samuraiChamplooAltered = samuraiChamplooSaved.getBody();
//        samuraiChamplooAltered.setName("Samurai Champloo 2");


//        ResponseEntity<Void> alterationResponse = new RestTemplate().exchange("http://localhost:8090/animes", HttpMethod.PUT, new HttpEntity<>(samuraiChamplooAltered), Void.class);

//        log.info(alterationResponse);

        new RestTemplate().exchange("http://localhost:8090/animes/{id}", HttpMethod.DELETE, null, Void.class, 21);

        new RestTemplate().exchange("http://localhost:8090/animes/{id}", HttpMethod.DELETE, null, Void.class, 22);

    }

}
