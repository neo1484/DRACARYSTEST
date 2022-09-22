package Clases;

public class PreventaProductoDistWSResult
{
    private int Id;
    private int PreVentaProductoId;
    private int PreVentaId;
    private int ProductoId;
    private int PromocionId;
    private int Cantidad;
    private int CantidadPaquete;
    private float Monto;
    private float Descuento;
    private float MontoFinal;
    private int EstadoId;
    private boolean EstadoSincronizacion;
    private float Costo;
    private int CostoId;
    private int PrecioId;
    private float DescuentoCanal;
    private float DescuentoAjuste;
    private int CanalRutaPrecioId;
    private float DescuentoProntoPago;

    public PreventaProductoDistWSResult(int id, int preVentaProductoId, int preVentaId, int productoId,
                                        int promocionId, int cantidad, int cantidadPaquete, float monto,
                                        float descuento, float montoFinal, int estadoId, boolean estadoSincronizacion,
                                        float costo, int costoId, int precioId, float descuentoCanal,
                                        float descuentoAjuste, int canalRutaPrecioId, float descuentoProntoPago) {
        Id = id;
        PreVentaProductoId = preVentaProductoId;
        PreVentaId = preVentaId;
        ProductoId = productoId;
        PromocionId = promocionId;
        Cantidad = cantidad;
        CantidadPaquete = cantidadPaquete;
        Monto = monto;
        Descuento = descuento;
        MontoFinal = montoFinal;
        EstadoId = estadoId;
        EstadoSincronizacion = estadoSincronizacion;
        Costo = costo;
        CostoId = costoId;
        PrecioId = precioId;
        DescuentoCanal = descuentoCanal;
        DescuentoAjuste = descuentoAjuste;
        CanalRutaPrecioId = canalRutaPrecioId;
        DescuentoProntoPago = descuentoProntoPago;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPreVentaProductoId() {
        return PreVentaProductoId;
    }

    public void setPreVentaProductoId(int preVentaProductoId) {
        PreVentaProductoId = preVentaProductoId;
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

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        PromocionId = promocionId;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public int getCantidadPaquete() {
        return CantidadPaquete;
    }

    public void setCantidadPaquete(int cantidadPaquete) {
        CantidadPaquete = cantidadPaquete;
    }

    public float getMonto() {
        return Monto;
    }

    public void setMonto(float monto) {
        Monto = monto;
    }

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float descuento) {
        Descuento = descuento;
    }

    public float getMontoFinal() {
        return MontoFinal;
    }

    public void setMontoFinal(float montoFinal) {
        MontoFinal = montoFinal;
    }

    public int getEstadoId() {
        return EstadoId;
    }

    public void setEstadoId(int estadoId) {
        EstadoId = estadoId;
    }

    public boolean isEstadoSincronizacion() {
        return EstadoSincronizacion;
    }

    public void setEstadoSincronizacion(boolean estadoSincronizacion) {
        EstadoSincronizacion = estadoSincronizacion;
    }

    public float getCosto() {
        return Costo;
    }

    public void setCosto(float costo) {
        Costo = costo;
    }

    public int getCostoId() {
        return CostoId;
    }

    public void setCostoId(int costoId) {
        CostoId = costoId;
    }

    public int getPrecioId() {
        return PrecioId;
    }

    public void setPrecioId(int precioId) {
        PrecioId = precioId;
    }

    public float getDescuentoCanal() {
        return DescuentoCanal;
    }

    public void setDescuentoCanal(float descuentoCanal) {
        DescuentoCanal = descuentoCanal;
    }

    public float getDescuentoAjuste() {
        return DescuentoAjuste;
    }

    public void setDescuentoAjuste(float descuentoAjuste) {
        DescuentoAjuste = descuentoAjuste;
    }

    public int getCanalRutaPrecioId() {
        return CanalRutaPrecioId;
    }

    public void setCanalRutaPrecioId(int canalRutaPrecioId) {
        CanalRutaPrecioId = canalRutaPrecioId;
    }

    public float getDescuentoProntoPago() {
        return DescuentoProntoPago;
    }

    public void setDescuentoProntoPago(float descuentoProntoPago) {
        DescuentoProntoPago = descuentoProntoPago;
    }
}
