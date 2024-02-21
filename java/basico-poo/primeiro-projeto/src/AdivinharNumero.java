import java.util.Random;
import java.util.Scanner;

public class AdivinharNumero {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);
        int palpiteNumero = 0;
        int tentativas = 0;

        int numeroAleatorio = new Random().nextInt(100);

        while (tentativas < 5){
            System.out.println("Digite um número de 1 a 100! Você terá 5 tentativas.");
            palpiteNumero = leitor.nextInt();
            tentativas++;

            if (palpiteNumero > numeroAleatorio) {
                System.out.println("O número aleatório é menor!");
            } else if (palpiteNumero < numeroAleatorio) {
                System.out.println("O número aleatório é maior!");
            } else if (palpiteNumero == numeroAleatorio){
                System.out.println("Você acertou!");
                break;
            }

            if (tentativas == 5) {
                System.out.println("Você não conseguiu adivinhar o número, o número era " + numeroAleatorio);
            }
        }
    }
}
