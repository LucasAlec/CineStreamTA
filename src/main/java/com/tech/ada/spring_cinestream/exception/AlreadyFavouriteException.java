package com.tech.ada.spring_cinestream.exception;

public class AlreadyFavouriteException extends Exception {
    public AlreadyFavouriteException(String mensagem) {
        super(mensagem);
    }

    public AlreadyFavouriteException() {
        System.out.println("Esse filme já está na lista de favorito do usuário.");
    }
}
