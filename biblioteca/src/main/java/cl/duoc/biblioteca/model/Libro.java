package cl.duoc.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "LIBROS")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//validaciones mensajes 


    @NotBlank(message = "El título es obligatorio")
    @Size(max = 150, message = "El título no puede superar 150 caracteres")
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    @Size(max = 120, message = "El autor no puede superar 120 caracteres")
    @Column(nullable = false, length = 120)
    private String autor;

    @NotNull(message = "El año de publicación es obligatorio")
    @PositiveOrZero(message = "El año de publicación no puede ser negativo")
    @Column(name = "ANIO_PUBLICACION", nullable = false)
    private Integer anioPublicacion;

    @NotBlank(message = "El género es obligatorio")
    @Size(max = 80, message = "El género no puede superar 80 caracteres")
    @Column(nullable = false, length = 80)
    ////
    private String genero;

    public Libro() {
    }

    public Libro(Long id, String titulo, String autor, Integer anioPublicacion, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anioPublicacion = anioPublicacion;
        this.genero = genero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
