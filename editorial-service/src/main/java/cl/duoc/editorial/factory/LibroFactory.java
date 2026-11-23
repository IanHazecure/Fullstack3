package cl.duoc.editorial.factory;

import cl.duoc.editorial.model.Libro;

public class LibroFactory {

    private LibroFactory() {
    }

    public static Libro crear(String titulo, String autor, Integer anio, String genero) {
        return Libro.builder()
                .titulo(titulo)
                .autor(autor)
                .anioPublicacion(anio)
                .genero(genero)
                .build();
    }
}