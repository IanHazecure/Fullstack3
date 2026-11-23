package cl.duoc.editorial.usuario.dto;

import cl.duoc.editorial.usuario.model.Rol;

public record UsuarioResponse(Long id, String nombre, String email, Rol rol) {
}