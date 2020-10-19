package dev.com.j3b.manejadorLogIn;

public class PrestamosActivos {


    private String descripcion;
    private String montoCancelar;
    private String idPrestamo;

    public PrestamosActivos(String descripcion, String montoCancelar, String idPrestamo) {
        this.descripcion = descripcion;
        this.montoCancelar = montoCancelar;
        this.idPrestamo = idPrestamo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return String.format(idPrestamo);
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMontoCancelar() {
        return montoCancelar;
    }

    public void setMontoCancelar(String montoCancelar) {
        this.montoCancelar = montoCancelar;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }
}
