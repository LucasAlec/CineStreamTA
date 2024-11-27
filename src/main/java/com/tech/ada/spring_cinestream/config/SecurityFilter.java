package com.tech.ada.spring_cinestream.config;

import com.tech.ada.spring_cinestream.model.Usuario;
import com.tech.ada.spring_cinestream.exception.NotFoundException;
import com.tech.ada.spring_cinestream.repository.UsuarioRepository;
import com.tech.ada.spring_cinestream.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UsuarioRepository usuarioRepository;
    private static final Logger logger = LoggerFactory.getLogger(SecurityFilter.class);

    public SecurityFilter(JWTService jwtService, UsuarioRepository usuarioRepository) {
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var token = this.recoverToken(request);

        if (token != null && !token.isEmpty()) {
            Optional<String> login = jwtService.validateToken(token);

            if (login.isPresent()) {
                try {
                    Optional<Usuario> usuario = usuarioRepository.findByEmail(login.get());
                    if (usuario.isEmpty()) throw new NotFoundException();
                    var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                    var authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (NotFoundException e) {
                    logger.error("Usuário não encontrado para o email: {}", login.get(), e);
                }
            }
        }

        filterChain.doFilter(request, response);
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.startsWith("/login") ||
                path.startsWith("/register") ||
                path.startsWith("/h2");
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
