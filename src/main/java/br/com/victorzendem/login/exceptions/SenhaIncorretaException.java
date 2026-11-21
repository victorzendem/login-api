package br.com.victorzendem.login.exceptions;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String message) {
        super(message);
    }

    public SenhaIncorretaException (){
        super("Senha atual incorreta.");
    }
}

