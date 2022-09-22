package Clases;

public class PreventaProductoCambioWSResult
{
    private int Id;
    private int PreVentaProductoCambioId;
    private int PreVentaId;
    private int ProductoId;
    private int Cantidad;
    private int ClienteId;
    private boolean Sincro;
    private int MotivoCambioId;

    public PreventaProductoCambioWSResult(int id, int preVentaProductoCambioId, int preVentaId, int productoId,
                                          int cantidad, int clienteId, boolean sincro, int motivoCambioId) {
        Id = id;
        PreVentaProductoCambioId = preVentaProductoCambioId;
        PreVentaId = preVentaId;
        ProductoId = productoId;
        Cantidad = cantidad;
        ClienteId = clienteId;
        Sincro = sincro;
        MotivoCambioId = motivoCambioId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPreVentaProductoCambioId() {
        return PreVentaProductoCambioId;
    }

    public void setPreVentaProductoCambioId(int preVentaProductoCambioId) {
        PreVentaProductoCambioId = preVentaProductoCambioId;
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

    public boolean isSincro() {
        return Sincro;
    }

    public void setSincro(boolean sincro) {
        Sincro = sincro;
    }

    public int getMotivoCambioId() {
        return MotivoCambioId;
    }

    public void setMotivoCambioId(int motivoCambioId) {
        MotivoCambioId = motivoCambioId;
    }
}
