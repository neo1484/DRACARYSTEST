package Clases;

public class AlmacenProductoWSResult {
    private int AlmacenId;
    private int ProductoId;
    private int SaldoUnitario;
    private int SaldoPaquete;
    private float CostoUnitario;
    private float CostoPaquete;

    public AlmacenProductoWSResult(int almacenId, int productoId, int saldoUnitario, int saldoPaquete, float costoUnitario, float costoPaquete) {
        AlmacenId = almacenId;
        ProductoId = productoId;
        SaldoUnitario = saldoUnitario;
        SaldoPaquete = saldoPaquete;
        CostoUnitario = costoUnitario;
        CostoPaquete = costoPaquete;
    }

    public int getAlmacenId() {
        return AlmacenId;
    }

    public void setAlmacenId(int almacenId) {
        AlmacenId = almacenId;
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

    public float getCostoUnitario() {
        return CostoUnitario;
    }

    public void setCostoUnitario(float costoUnitario) {
        CostoUnitario = costoUnitario;
    }

    public float getCostoPaquete() {
        return CostoPaquete;
    }

    public void setCostoPaquete(float costoPaquete) {
        CostoPaquete = costoPaquete;
    }
}
