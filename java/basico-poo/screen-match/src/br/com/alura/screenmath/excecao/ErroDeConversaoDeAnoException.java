package br.com.alura.screenmath.excecao;

public class ErroDeConversaoDeAnoException extends Throwable {
    private String mensagem;
    public ErroDeConversaoDeAnoException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return this.mensagem;
    }
}
