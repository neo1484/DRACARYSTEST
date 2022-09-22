package Clases;

public class CostoWSResult
{
    private int CostoId;
    private int ProductoId;
    private float Costo;
    private float CostoPaquete;
    private float Cpp;

    public CostoWSResult(int costoId, int productoId, float costo, float costoPaquete, float cpp) {
        CostoId = costoId;
        ProductoId = productoId;
        Costo = costo;
        CostoPaquete = costoPaquete;
        Cpp = cpp;
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

    public float getCostoPaquete() {
        return CostoPaquete;
    }

    public void setCostoPaquete(float costoPaquete) {
        CostoPaquete = costoPaquete;
    }

    public float getCpp() {
        return Cpp;
    }

    public void setCpp(float cpp) {
        Cpp = cpp;
    }
}
