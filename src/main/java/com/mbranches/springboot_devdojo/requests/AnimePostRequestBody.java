package com.mbranches.springboot_devdojo.requests;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePostRequestBody {
    @NotEmpty(message = "The anime cannot be empty")
    private String name;
}
