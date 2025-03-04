package com.mbranches.springboot_devdojo.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private Long id;
    @NotNull(message = "the anime name cannot be empty")
    private String name;
}
