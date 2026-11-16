package cl.duoc.libros.factory;

import cl.duoc.libros.model.Libro;

public class LibroFactory {
    public static Libro crear(String titulo, String autor, Integer anio, String genero) {
        return Libro.builder()
                .titulo(titulo)
                .autor(autor)
                .anioPublicacion(anio)
                .genero(genero)
                .build();
    }
}
