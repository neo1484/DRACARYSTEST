package Clases;

public class DevolucionDistribuidorWSResult
{
    private int Id;
    private int AlmacenDevolucionId;
    private int DistribuidorId;
    private int Anio;
    private int Mes;
    private int Dia;
    private int EstadoId;
    private boolean EstadoSincronizacion;

    public DevolucionDistribuidorWSResult(int id, int almacenDevolucionId, int distribuidorId, int anio,
                                          int mes, int dia, int estadoId, boolean estadoSincronizacion) {
        Id = id;
        AlmacenDevolucionId = almacenDevolucionId;
        DistribuidorId = distribuidorId;
        Anio = anio;
        Mes = mes;
        Dia = dia;
        this.EstadoId = estadoId;
        EstadoSincronizacion = estadoSincronizacion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getAlmacenDevolucionId() {
        return AlmacenDevolucionId;
    }

    public void setAlmacenDevolucionId(int almacenDevolucionId) {
        AlmacenDevolucionId = almacenDevolucionId;
    }

    public int getDistribuidorId() {
        return DistribuidorId;
    }

    public void setDistribuidorId(int distribuidorId) {
        DistribuidorId = distribuidorId;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getEstadoId() {
        return EstadoId;
    }

    public void setEstadoId(int estadoId) {
        this.EstadoId = estadoId;
    }

    public boolean isEstadoSincronizacion() {
        return EstadoSincronizacion;
    }

    public void setEstadoSincronizacion(boolean estadoSincronizacion) {
        EstadoSincronizacion = estadoSincronizacion;
    }
}
