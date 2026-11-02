package cl.duoc.biblioteca.foro.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import cl.duoc.biblioteca.foro.dto.CategoriaRequest;
import cl.duoc.biblioteca.foro.dto.CategoriaResponse;
import cl.duoc.biblioteca.foro.model.Categoria;
import cl.duoc.biblioteca.foro.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Optional<CategoriaResponse> buscarPorId(Long id) {
        return categoriaRepository.findById(id).map(this::toResponse);
    }

    public CategoriaResponse crear(CategoriaRequest request) {
        if (categoriaRepository.existsByNombreIgnoreCase(request.nombre())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
        Categoria categoria = new Categoria();
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        return toResponse(categoriaRepository.save(categoria));
    }

    public Optional<CategoriaResponse> actualizar(Long id, CategoriaRequest request) {
        return categoriaRepository.findById(id).map(categoria -> {
            categoria.setNombre(request.nombre());
            categoria.setDescripcion(request.descripcion());
            return toResponse(categoriaRepository.save(categoria));
        });
    }

    public boolean eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            return false;
        }
        categoriaRepository.deleteById(id);
        return true;
    }

    private CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}