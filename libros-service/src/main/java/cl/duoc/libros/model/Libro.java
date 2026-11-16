package cl.duoc.libros.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LIBROS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String autor;
    private Integer anioPublicacion;
    private String genero;
}
