package dev.com.j3b.modelos;

public class SolicitudTarjeta {

    private Integer id;
    private String tipoTrabajo;
    private String dpi_cliente;
    private String empresa;
    private String estado;
    private Double salario;
    private String tarjeta;
    private String descripcion;



    public SolicitudTarjeta( Integer id, String tipoTrabajo, String dpi_cliente, String empresa, String estado, Double salario, String tarjeta, String descripcion) {
        this.id = id;
        this.tipoTrabajo = tipoTrabajo;
        this.dpi_cliente = dpi_cliente;
        this.empresa = empresa;
        this.estado = estado;
        this.salario = salario;
        this.tarjeta = tarjeta;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoTrabajo() {
        return tipoTrabajo;
    }

    public void setTipoTrabajo(String tipoTrabajo) {
        this.tipoTrabajo = tipoTrabajo;
    }

    public String getDpi_cliente() {
        return dpi_cliente;
    }

    public void setDpi_cliente(String dpi_cliente) {
        this.dpi_cliente = dpi_cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
