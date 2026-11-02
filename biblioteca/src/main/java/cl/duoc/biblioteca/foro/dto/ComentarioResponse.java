package cl.duoc.biblioteca.foro.dto;

import java.time.LocalDateTime;

public record ComentarioResponse(Long id, String contenido, LocalDateTime fechaCreacion,
        Long temaId, Long autorId, String autor) {
}