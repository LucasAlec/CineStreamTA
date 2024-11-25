package com.tech.ada.spring_cinestream.dto.token;

import com.tech.ada.spring_cinestream.model.TokenEntity;

public record TokenResult(TokenEntity token, boolean isExpired) {}
