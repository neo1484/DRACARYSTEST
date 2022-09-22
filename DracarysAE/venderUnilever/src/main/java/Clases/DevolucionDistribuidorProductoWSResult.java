package Clases;

public class DevolucionDistribuidorProductoWSResult
{
    private int AlmacenDevolucionId;
    private int ProductoId;
    private int SaldoUnitario;
    private int SaldoPaquete;
    private boolean EstadoSincronizacion;

    public DevolucionDistribuidorProductoWSResult(int almacenDevolucionId, int productoId, int saldoUnitario, int saldoPaquete, boolean estadoSincronizacion) {
        AlmacenDevolucionId = almacenDevolucionId;
        ProductoId = productoId;
        SaldoUnitario = saldoUnitario;
        SaldoPaquete = saldoPaquete;
        EstadoSincronizacion = estadoSincronizacion;
    }

    public int getAlmacenDevolucionId() {
        return AlmacenDevolucionId;
    }

    public void setAlmacenDevolucionId(int almacenDevolucionId) {
        AlmacenDevolucionId = almacenDevolucionId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public int getSaldoUnitario() {
        return SaldoUnitario;
    }

    public void setSaldoUnitario(int saldoUnitario) {
        SaldoUnitario = saldoUnitario;
    }

    public int getSaldoPaquete() {
        return SaldoPaquete;
    }

    public void setSaldoPaquete(int saldoPaquete) {
        SaldoPaquete = saldoPaquete;
    }

    public boolean isEstadoSincronizacion() {
        return EstadoSincronizacion;
    }

    public void setEstadoSincronizacion(boolean estadoSincronizacion) {
        EstadoSincronizacion = estadoSincronizacion;
    }
}
