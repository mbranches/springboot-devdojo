package com.mbranches.springboot_devdojo.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class BadRequestDetails extends ExceptionDetails {
}
