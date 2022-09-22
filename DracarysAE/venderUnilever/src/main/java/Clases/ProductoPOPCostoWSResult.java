package Clases;

public class ProductoPOPCostoWSResult
{
    private int CostoId;
    private int ProductoId;
    private float Costo;

    public ProductoPOPCostoWSResult(int costoId, int productoId, float costo) {
        CostoId = costoId;
        ProductoId = productoId;
        Costo = costo;
    }

    public int getCostoId() {
        return CostoId;
    }

    public void setCostoId(int costoId) {
        CostoId = costoId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public float getCosto() {
        return Costo;
    }

    public void setCosto(float costo) {
        Costo = costo;
    }
}
