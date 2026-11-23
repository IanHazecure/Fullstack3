package cl.duoc.editorial.usuario.dto;

public record LoginResponse(String token, UsuarioResponse usuario) {
}