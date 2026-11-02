package cl.duoc.biblioteca.usuario.dto;

public record LoginResponse(String token, UsuarioResponse usuario) {
}