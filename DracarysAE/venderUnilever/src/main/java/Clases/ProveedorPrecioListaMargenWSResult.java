package Clases;

public class ProveedorPrecioListaMargenWSResult
{
    private int ProveedorId;
    private int PrecioListaId;
    private float Margen;

    public ProveedorPrecioListaMargenWSResult(int proveedorId, int precioListaId, float margen) {
        ProveedorId = proveedorId;
        PrecioListaId = precioListaId;
        Margen = margen;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }

    public float getMargen() {
        return Margen;
    }

    public void setMargen(float margen) {
        Margen = margen;
    }
}
