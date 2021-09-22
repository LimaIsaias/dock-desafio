package br.com.limaisaias.dockdesafio.exceptions;


public class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
