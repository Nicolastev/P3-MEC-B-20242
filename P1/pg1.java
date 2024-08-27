/*crear una clase llamada fraccion, con atributos correspondientes 
a numerador y denominado, aplicar encapzulamiento y los metodos get y set para manejar objetos de esta clase*/
public class Fraccion {
    private int numerador = 0;
    private int denominador = 0;

    public Fraccion(int numerador, int denominador) {
        this.numerador = numerador;
        this.denominador = denominador;
    }
    
    public int getNumerador() {
        return numerador;
    }
    
    public void setNumerador(int numerador) {
        this.numerador = numerador;
    }

    public int getDenominador() {
        return denominador;
    }

    public void setDenominador(int denominador) {
        this.denominador = denominador;
    }
}
