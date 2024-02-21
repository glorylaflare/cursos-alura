import java.io.IOException;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner leitura = new Scanner(System.in);
        ConsultaCEP consulta = new ConsultaCEP();

        System.out.println("Digite um número de cep para consulta:");
        var cep = leitura.nextLine();

        try {
            Endereco endereco = consulta.buscaendereco(cep);
            System.out.println(endereco);
            GeradorDeArquivo gerador = new GeradorDeArquivo();
            gerador.geraArquivoJson(endereco);
        } catch (RuntimeException | IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Finalizando a aplicação");
        }
    }
}