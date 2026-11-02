package cl.duoc.biblioteca.usuario.dto;

import cl.duoc.biblioteca.usuario.model.Rol;

public record UsuarioResponse(Long id, String nombre, String email, Rol rol) {
}