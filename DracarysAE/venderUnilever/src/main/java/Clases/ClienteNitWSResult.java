package Clases;

public class ClienteNitWSResult
{
    private int ClienteId;
    private String NombreFactura;
    private String Nit;
    private int EmpleadoId;
    private int Dia;
    private int Mes;
    private int Anio;
    private boolean Sincronizacion;
    private String TipoNit;

    public ClienteNitWSResult(int clienteId, String nombreFactura, String nit, int empleadoId, int dia, int mes, int anio, boolean sincronizacion, String tipoNit) {
        ClienteId = clienteId;
        NombreFactura = nombreFactura;
        Nit = nit;
        EmpleadoId = empleadoId;
        Dia = dia;
        Mes = mes;
        Anio = anio;
        Sincronizacion = sincronizacion;
        TipoNit = tipoNit;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public String getNombreFactura() {
        return NombreFactura;
    }

    public void setNombreFactura(String nombreFactura) {
        NombreFactura = nombreFactura;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    public int getEmpleadoId() {
        return EmpleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        EmpleadoId = empleadoId;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }

    public boolean isSincronizacion() {
        return Sincronizacion;
    }

    public void setSincronizacion(boolean sincronizacion) {
        Sincronizacion = sincronizacion;
    }

    public String getTipoNit() {
        return TipoNit;
    }

    public void setTipoNit(String tipoNit) {
        TipoNit = tipoNit;
    }
}
