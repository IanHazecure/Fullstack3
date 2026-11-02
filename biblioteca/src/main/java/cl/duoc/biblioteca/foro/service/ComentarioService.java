package cl.duoc.biblioteca.foro.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.biblioteca.foro.dto.ComentarioRequest;
import cl.duoc.biblioteca.foro.dto.ComentarioResponse;
import cl.duoc.biblioteca.foro.model.Comentario;
import cl.duoc.biblioteca.foro.model.Tema;
import cl.duoc.biblioteca.foro.repository.ComentarioRepository;
import cl.duoc.biblioteca.foro.repository.TemaRepository;
import cl.duoc.biblioteca.usuario.model.Usuario;
import cl.duoc.biblioteca.usuario.repository.UsuarioRepository;

@Service
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final TemaRepository temaRepository;
    private final UsuarioRepository usuarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository, TemaRepository temaRepository,
            UsuarioRepository usuarioRepository) {
        this.comentarioRepository = comentarioRepository;
        this.temaRepository = temaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ComentarioResponse> listarPorTema(Long temaId) {
        validarTema(temaId);
        return comentarioRepository.findByTemaIdOrderByFechaCreacionAsc(temaId).stream()
                .map(this::toResponse).toList();
    }

    public ComentarioResponse crear(Long temaId, ComentarioRequest request) {
        Tema tema = temaRepository.findById(temaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema no encontrado"));
        Usuario autor = usuarioRepository.findById(request.autorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
        Comentario comentario = new Comentario();
        comentario.setContenido(request.contenido());
        comentario.setFechaCreacion(LocalDateTime.now());
        comentario.setTema(tema);
        comentario.setAutor(autor);
        return toResponse(comentarioRepository.save(comentario));
    }

    public Optional<ComentarioResponse> actualizar(Long id, ComentarioRequest request) {
        return comentarioRepository.findById(id).map(comentario -> {
            Usuario autor = usuarioRepository.findById(request.autorId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado"));
            comentario.setContenido(request.contenido());
            comentario.setAutor(autor);
            return toResponse(comentarioRepository.save(comentario));
        });
    }

    public boolean eliminar(Long id) {
        if (!comentarioRepository.existsById(id)) {
            return false;
        }
        comentarioRepository.deleteById(id);
        return true;
    }

    private void validarTema(Long temaId) {
        if (!temaRepository.existsById(temaId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tema no encontrado");
        }
    }

    private ComentarioResponse toResponse(Comentario comentario) {
        return new ComentarioResponse(comentario.getId(), comentario.getContenido(), comentario.getFechaCreacion(),
                comentario.getTema().getId(), comentario.getAutor().getId(), comentario.getAutor().getNombre());
    }
}