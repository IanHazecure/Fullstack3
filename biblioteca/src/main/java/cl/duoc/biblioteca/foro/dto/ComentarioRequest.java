package cl.duoc.biblioteca.foro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public record ComentarioRequest(
        @NotBlank @Size(max = 2000) String contenido,
        @NotNull @Positive(message = "El id de autor debe ser positivo") Long autorId) {
}