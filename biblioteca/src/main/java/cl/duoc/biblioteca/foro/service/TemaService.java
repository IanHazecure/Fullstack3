package cl.duoc.biblioteca.foro.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.biblioteca.foro.dto.TemaRequest;
import cl.duoc.biblioteca.foro.dto.TemaResponse;
import cl.duoc.biblioteca.foro.model.Categoria;
import cl.duoc.biblioteca.foro.model.Tema;
import cl.duoc.biblioteca.foro.repository.CategoriaRepository;
import cl.duoc.biblioteca.foro.repository.TemaRepository;
import cl.duoc.biblioteca.usuario.model.Usuario;
import cl.duoc.biblioteca.usuario.repository.UsuarioRepository;

@Service
public class TemaService {

    private final TemaRepository temaRepository;
    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;

    public TemaService(TemaRepository temaRepository, CategoriaRepository categoriaRepository,
            UsuarioRepository usuarioRepository) {
        this.temaRepository = temaRepository;
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<TemaResponse> listar(String texto, Long categoriaId) {
        return temaRepository.findAll().stream()
                .filter(tema -> categoriaId == null || tema.getCategoria().getId().equals(categoriaId))
                .filter(tema -> texto == null || texto.isBlank()
                        || tema.getTitulo().toLowerCase().contains(texto.toLowerCase())
                        || tema.getContenido().toLowerCase().contains(texto.toLowerCase()))
                .map(this::toResponse)
                .toList();
    }

    public Optional<TemaResponse> buscarPorId(Long id) {
        return temaRepository.findById(id).map(this::toResponse);
    }

    public TemaResponse crear(TemaRequest request) {
        Tema tema = new Tema();
        aplicarDatos(tema, request);
        tema.setFechaCreacion(LocalDateTime.now());
        return toResponse(temaRepository.save(tema));
    }

    public Optional<TemaResponse> actualizar(Long id, TemaRequest request) {
        return temaRepository.findById(id).map(tema -> {
            aplicarDatos(tema, request);
            return toResponse(temaRepository.save(tema));
        });
    }

    public boolean eliminar(Long id) {
        if (!temaRepository.existsById(id)) {
            return false;
        }
        temaRepository.deleteById(id);
        return true;
    }

    private void aplicarDatos(Tema tema, TemaRequest request) {
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        tema.setTitulo(request.titulo());
        tema.setContenido(request.contenido());
        tema.setCategoria(categoria);
        tema.setAutor(autor);
    }

    private TemaResponse toResponse(Tema tema) {
        return new TemaResponse(tema.getId(), tema.getTitulo(), tema.getContenido(), tema.getFechaCreacion(),
                tema.getCategoria().getId(), tema.getCategoria().getNombre(), tema.getAutor().getId(),
                tema.getAutor().getNombre());
    }
}