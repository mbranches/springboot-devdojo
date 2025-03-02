package com.mbranches.springboot_devdojo.repository;

import com.mbranches.springboot_devdojo.model.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    List<Anime> findByName(String name);
}
