package cl.duoc.biblioteca.foro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

public record TemaRequest(
        @NotBlank @Size(max = 150) String titulo,
        @NotBlank @Size(max = 4000) String contenido,
        @NotNull @Positive(message = "El id de categoría debe ser positivo") Long categoriaId,
        @NotNull @Positive(message = "El id de autor debe ser positivo") Long autorId) {
}