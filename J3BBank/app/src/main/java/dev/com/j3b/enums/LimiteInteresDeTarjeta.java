package dev.com.j3b.enums;

public enum LimiteInteresDeTarjeta {

    ORO(10000,0.05),
    PLATA(50000,0.03),
    BRONCE(25000,0.02);

    private double limite;
    private double interes;
    private LimiteInteresDeTarjeta(double limite,double interes){
        this.limite = limite;
        this.interes = interes;
    }

    public double getLimite() {
        return limite;
    }

    public double getInteres(){
        return interes;
    }
}
