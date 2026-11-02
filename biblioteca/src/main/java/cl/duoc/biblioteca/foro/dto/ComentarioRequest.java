package cl.duoc.biblioteca.foro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioRequest(
        @NotBlank @Size(max = 2000) String contenido,
        @NotNull Long autorId) {
}