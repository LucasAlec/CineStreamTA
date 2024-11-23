package com.tech.ada.spring_cinestream.repository;

import com.tech.ada.spring_cinestream.model.FilmeFavorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeFavoritoRepository extends JpaRepository<FilmeFavorito, Long> {

    List<FilmeFavorito> findByUsuarioId(Long id);
}
