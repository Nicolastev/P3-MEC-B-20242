crear una sobre carga de constructor y crear un objeto para cada una de sus formas
  public class Fraccion {
    private int numerador = 0;
    private int denominador = 0;
    
//sobrecarga
    public static void main(String[] args) {
       
        fraccion sub1 = new fraccion();
        fraccion sub2 = new fraccion(100);
        fraccion sub3 = new fraccion(200)};

        sub1.numerador(50);
        sub2.numerador(150);
        sub3.numerador(250);
        
        sub1.denominador(50);
        sub2.denominador(150);
        sub3.denominador(250);
    }
}
