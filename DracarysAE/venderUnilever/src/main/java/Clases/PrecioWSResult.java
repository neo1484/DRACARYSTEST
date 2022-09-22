package Clases;

public class PrecioWSResult
{
    private int PrecioListaId;
    private int ProductoId;
    private float Precio;
    private float PrecioPaquete;
    private float DescuentoUnidad;
    private float DescuentoPaquete;
    private float MargenUnidad;
    private float MargenPaquete;
    private int PrecioId;
    private boolean Activo;

    public PrecioWSResult(int precioListaId, int productoId, float precio, float precioPaquete, float descuentoUnidad,
                          float descuentoPaquete, float margenUnidad, float margenPaquete, int precioId, boolean activo)
    {
        PrecioListaId = precioListaId;
        ProductoId = productoId;
        Precio = precio;
        PrecioPaquete = precioPaquete;
        DescuentoUnidad = descuentoUnidad;
        DescuentoPaquete = descuentoPaquete;
        MargenUnidad = margenUnidad;
        MargenPaquete = margenPaquete;
        PrecioId = precioId;
        Activo = activo;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
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

    public float getDescuentoUnidad() {
        return DescuentoUnidad;
    }

    public void setDescuentoUnidad(float descuentoUnidad) {
        DescuentoUnidad = descuentoUnidad;
    }

    public float getDescuentoPaquete() {
        return DescuentoPaquete;
    }

    public void setDescuentoPaquete(float descuentoPaquete) {
        DescuentoPaquete = descuentoPaquete;
    }

    public float getMargenUnidad() {
        return MargenUnidad;
    }

    public void setMargenUnidad(float margenUnidad) {
        MargenUnidad = margenUnidad;
    }

    public float getMargenPaquete() {
        return MargenPaquete;
    }

    public void setMargenPaquete(float margenPaquete) {
        MargenPaquete = margenPaquete;
    }

    public int getPrecioId() {
        return PrecioId;
    }

    public void setPrecioId(int precioId) {
        PrecioId = precioId;
    }

    public boolean isActivo() {
        return Activo;
    }

    public void setActivo(boolean activo) {
        Activo = activo;
    }
}
