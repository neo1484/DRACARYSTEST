package Clases;

public class PromocionProductoWSResult
{
    private int PromocionId;
    private int ProductoId;
    private int Cantidad;
    private int CantidadPaquete;
    private float Descuento;
    private float DescuentoPaquete;
    private int PrecioListaId;
    private int CostoId;
    private float Precio;
    private float PrecioPaquete;

    public PromocionProductoWSResult(int promocionId, int productoId, int cantidad, int cantidadPaquete, float descuento, float descuentoPaquete, int precioListaId, int costoId, float precio, float precioPaquete) {
        PromocionId = promocionId;
        ProductoId = productoId;
        Cantidad = cantidad;
        CantidadPaquete = cantidadPaquete;
        Descuento = descuento;
        DescuentoPaquete = descuentoPaquete;
        PrecioListaId = precioListaId;
        CostoId = costoId;
        Precio = precio;
        PrecioPaquete = precioPaquete;
    }

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        PromocionId = promocionId;
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

    public int getCantidadPaquete() {
        return CantidadPaquete;
    }

    public void setCantidadPaquete(int cantidadPaquete) {
        CantidadPaquete = cantidadPaquete;
    }

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float descuento) {
        Descuento = descuento;
    }

    public float getDescuentoPaquete() {
        return DescuentoPaquete;
    }

    public void setDescuentoPaquete(float descuentoPaquete) {
        DescuentoPaquete = descuentoPaquete;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }

    public int getCostoId() {
        return CostoId;
    }

    public void setCostoId(int costoId) {
        CostoId = costoId;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public float getPrecioPaquete() {
        return PrecioPaquete;
    }

    public void setPrecioPaquete(float precioPaquete) {
        PrecioPaquete = precioPaquete;
    }
}
