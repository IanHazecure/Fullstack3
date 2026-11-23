package cl.duoc.editorial.usuario.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import cl.duoc.editorial.usuario.dto.LoginRequest;
import cl.duoc.editorial.usuario.dto.LoginResponse;
import cl.duoc.editorial.usuario.dto.RegisterRequest;
import cl.duoc.editorial.usuario.dto.UsuarioResponse;
import cl.duoc.editorial.usuario.model.Rol;
import cl.duoc.editorial.usuario.model.Usuario;
import cl.duoc.editorial.usuario.repository.UsuarioRepository;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));
        if (!passwordEncoder.matches(request.password(), usuario.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
        String token = jwtService.generar(usuario.getEmail(), usuario.getRol().name());
        UsuarioResponse response = new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                usuario.getRol());
        return new LoginResponse(token, response);
    }

    public LoginResponse register(RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ese email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPasswordHash(passwordEncoder.encode(request.password()));
        usuario.setRol(Rol.USER);
        usuario = usuarioRepository.save(usuario);
        String token = jwtService.generar(usuario.getEmail(), usuario.getRol().name());
        UsuarioResponse response = new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(),
                usuario.getRol());
        return new LoginResponse(token, response);
    }
}