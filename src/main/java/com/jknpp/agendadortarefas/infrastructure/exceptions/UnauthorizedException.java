package com.jknpp.agendadortarefas.infrastructure.exceptions;


import javax.naming.AuthenticationException;

public class UnauthorizedException extends AuthenticationException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
