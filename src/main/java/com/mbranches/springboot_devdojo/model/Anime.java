package com.mbranches.springboot_devdojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Anime {
    private long id;
    private String name;
}