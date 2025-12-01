package modelo.dto;

import java.io.Serializable;

public class Cubiculo implements Serializable {
    
    private int codCubiculo;
    private int codLocal;
    private int codNivel;
    private String tipoCubiculo;
    private int capacidad;
    private double precioHora;
    private String estadoCubiculo;
    private String descripcion;
    
    public Cubiculo() {
    }

    public Cubiculo(int codCubiculo, int codLocal, int codNivel, String tipoCubiculo, 
                    int capacidad, double precioHora, String estadoCubiculo, String descripcion) {
        this.codCubiculo = codCubiculo;
        this.codLocal = codLocal;
        this.codNivel = codNivel;
        this.tipoCubiculo = tipoCubiculo;
        this.capacidad = capacidad;
        this.precioHora = precioHora;
        this.estadoCubiculo = estadoCubiculo;
        this.descripcion = descripcion;
    }

    public int getCodCubiculo() {
        return codCubiculo;
    }

    public void setCodCubiculo(int codCubiculo) {
        this.codCubiculo = codCubiculo;
    }

    public int getCodLocal() {
        return codLocal;
    }

    public void setCodLocal(int codLocal) {
        this.codLocal = codLocal;
    }

    public int getCodNivel() {
        return codNivel;
    }

    public void setCodNivel(int codNivel) {
        this.codNivel = codNivel;
    }

    public String getTipoCubiculo() {
        return tipoCubiculo;
    }

    public void setTipoCubiculo(String tipoCubiculo) {
        this.tipoCubiculo = tipoCubiculo;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(double precioHora) {
        this.precioHora = precioHora;
    }

    public String getEstadoCubiculo() {
        return estadoCubiculo;
    }

    public void setEstadoCubiculo(String estadoCubiculo) {
        this.estadoCubiculo = estadoCubiculo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Cubiculo{" +
                "codCubiculo=" + codCubiculo +
                ", codLocal=" + codLocal +
                ", codNivel=" + codNivel +
                ", tipoCubiculo='" + tipoCubiculo + '\'' +
                ", capacidad=" + capacidad +
                ", precioHora=" + precioHora +
                ", estadoCubiculo='" + estadoCubiculo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}