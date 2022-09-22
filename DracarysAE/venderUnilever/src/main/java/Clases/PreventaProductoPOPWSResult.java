package Clases;

public class PreventaProductoPOPWSResult
{
    private int PreVentaProductoPOPId;
    private int PreVentaId;
    private int ProductoId;
    private int Cantidad;
    private int ClienteId;
    private boolean Syncro;
    private String Observacion;
    private int MotivoPopId;

    public PreventaProductoPOPWSResult(int preVentaProductoPOPId, int preVentaId, int productoId, int cantidad, int clienteId, boolean syncro, String observacion, int motivoPopId) {
        PreVentaProductoPOPId = preVentaProductoPOPId;
        PreVentaId = preVentaId;
        ProductoId = productoId;
        Cantidad = cantidad;
        ClienteId = clienteId;
        Syncro = syncro;
        Observacion = observacion;
        MotivoPopId = motivoPopId;
    }

    public int getPreVentaProductoPOPId() {
        return PreVentaProductoPOPId;
    }

    public void setPreVentaProductoPOPId(int preVentaProductoPOPId) {
        PreVentaProductoPOPId = preVentaProductoPOPId;
    }

    public int getPreVentaId() {
        return PreVentaId;
    }

    public void setPreVentaId(int preVentaId) {
        PreVentaId = preVentaId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public boolean isSyncro() {
        return Syncro;
    }

    public void setSyncro(boolean syncro) {
        Syncro = syncro;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public int getMotivoPopId() {
        return MotivoPopId;
    }

    public void setMotivoPopId(int motivoPopId) {
        MotivoPopId = motivoPopId;
    }
}
