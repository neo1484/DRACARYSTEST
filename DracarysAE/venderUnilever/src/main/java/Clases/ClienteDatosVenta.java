package Clases;

public class ClienteDatosVenta
{
    private int id;
    private int clienteId;
    private float promedioSemanal;
    private int intensidadCompra;
    private String ultimaCompraFecha;
    private float ultimaCompraMonto;
    private String escadoPreventaFecha;
    private String escadoPreventa;
    private String escadoVentaFecha;
    private String escadoVenta;

    public ClienteDatosVenta(){}

    public ClienteDatosVenta(int id, int clienteId, float promedioSemanal, int intensidadCompra,
                             String ultimaCompraFecha, float ultimaCompraMonto,
                             String escadoPreventaFecha, String escadoPreventa, String escadoVentaFecha,
                             String escadoVenta)
    {
        this.id = id;
        this.clienteId = clienteId;
        this.promedioSemanal = promedioSemanal;
        this.intensidadCompra = intensidadCompra;
        this.ultimaCompraFecha = ultimaCompraFecha;
        this.ultimaCompraMonto = ultimaCompraMonto;
        this.escadoPreventaFecha = escadoPreventaFecha;
        this.escadoPreventa = escadoPreventa;
        this.escadoVentaFecha = escadoVentaFecha;
        this.escadoVenta = escadoVenta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public float getPromedioSemanal() {
        return promedioSemanal;
    }

    public void setPromedioSemanal(float promedioSemanal) {
        this.promedioSemanal = promedioSemanal;
    }

    public int getIntensidadCompra() {
        return intensidadCompra;
    }

    public void setIntensidadCompra(int intensidadCompra) {
        this.intensidadCompra = intensidadCompra;
    }

    public String getUltimaCompraFecha() {
        return ultimaCompraFecha;
    }

    public void setUltimaCompraFecha(String ultimaCompraFecha) {
        this.ultimaCompraFecha = ultimaCompraFecha;
    }

    public float getUltimaCompraMonto() {
        return ultimaCompraMonto;
    }

    public void setUltimaCompraMonto(float ultimaCompraMonto) {
        this.ultimaCompraMonto = ultimaCompraMonto;
    }

    public String getEscadoPreventaFecha() {
        return escadoPreventaFecha;
    }

    public void setEscadoPreventaFecha(String escadoPreventaFecha) {
        this.escadoPreventaFecha = escadoPreventaFecha;
    }

    public String getEscadoPreventa() {
        return escadoPreventa;
    }

    public void setEscadoPreventa(String escadoPreventa) {
        this.escadoPreventa = escadoPreventa;
    }

    public String getEscadoVentaFecha() {
        return escadoVentaFecha;
    }

    public void setEscadoVentaFecha(String escadoVentaFecha) {
        this.escadoVentaFecha = escadoVentaFecha;
    }

    public String getEscadoVenta() {
        return escadoVenta;
    }

    public void setEscadoVenta(String escadoVenta) {
        this.escadoVenta = escadoVenta;
    }
}
