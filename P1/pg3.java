//con la clase fraccion crear un metodo puro, que me permita sumar dos fracciones y q me retorne su resultado

import java.math.BigInteger;

public class Fraccion {
    private int numerador;
    private int denominador;

    public Fraccion(int numerador, int denominador) {
        
        this.numerador = numerador;
        this.denominador = denominador;
        
    }

    public Fraccion sumar(Fraccion segunda) {
        int newNumerador = this.numerador * segunda.denominador + segunda.numerador * this.denominador;
        int newDenominador = this.denominador * segunda.denominador;
        return new Fraccion(newNumerador, newDenominador);
    }
  
    public String toString() {
        return numerador + "/" + denominador;
    }

    public static void main(String[] args) {
        Fraccion frac1 = new Fraccion(1, 2);
        Fraccion frac2 = new Fraccion(1, 3);

        Fraccion resultado = frac1.sumar(frac2);
        System.out.println(resultado); 
    }
}
