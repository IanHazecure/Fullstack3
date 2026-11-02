package cl.duoc.biblioteca.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duoc.biblioteca.usuario.dto.UsuarioRequest;
import cl.duoc.biblioteca.usuario.dto.UsuarioResponse;
import cl.duoc.biblioteca.usuario.model.Rol;
import cl.duoc.biblioteca.usuario.model.Usuario;
import cl.duoc.biblioteca.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<UsuarioResponse> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Optional<UsuarioResponse> buscarPorId(Long id) {
        return usuarioRepository.findById(id).map(this::toResponse);
    }

    public UsuarioResponse crear(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        usuario.setRol(request.rol() == null ? Rol.USER : request.rol());
        return toResponse(usuarioRepository.save(usuario));
    }

    public Optional<UsuarioResponse> actualizar(Long id, UsuarioRequest request) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNombre(request.nombre());
            usuario.setEmail(request.email());
            usuario.setPasswordHash(passwordEncoder.encode(request.password()));
            usuario.setRol(request.rol() == null ? usuario.getRol() : request.rol());
            return toResponse(usuarioRepository.save(usuario));
        });
    }

    public boolean eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }
        usuarioRepository.deleteById(id);
        return true;
    }

    private UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getRol());
    }
}