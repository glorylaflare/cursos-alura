public class Conversor {
    public static void main(String[] args) {
        double celsius = 20.0;
        double fahrenheit = (celsius * 1.8) + 32;
        String mensagem = String.format("A temperatura de %f Celsius é equivalente a %f Fahrenheit", celsius, fahrenheit);
        System.out.println(mensagem);

        //Casting
        int fahrenheitInter = (int) fahrenheit;
        System.out.println(fahrenheitInter);
    }
}
