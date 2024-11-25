package com.tech.ada.spring_cinestream.dto.token;

import com.tech.ada.spring_cinestream.model.Token;

public record TokenResult(Token token, boolean isExpired) {}
