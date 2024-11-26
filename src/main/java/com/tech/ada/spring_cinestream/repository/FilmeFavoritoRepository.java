package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.FilmesFavoritos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeFavoritoRepository extends JpaRepository<com.tech.ada.spring_cinestream.model.FilmesFavoritos, Long> {

    List<FilmesFavoritos> findByUsuarioId(Long id);
}
