package com.mbranches.springboot_devdojo.repository;

import com.mbranches.springboot_devdojo.model.Anime;

import java.util.List;

public interface AnimeRepository {
    List<Anime> listAll();
}
