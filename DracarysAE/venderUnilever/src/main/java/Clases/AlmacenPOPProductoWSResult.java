package Clases;

public class AlmacenPOPProductoWSResult
{
    private int AlmacenId;
    private int ProductoId;
    private int Saldo;

    public AlmacenPOPProductoWSResult(int almacenId, int productoId, int saldo) {
        AlmacenId = almacenId;
        ProductoId = productoId;
        Saldo = saldo;
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

    public int getSaldo() {
        return Saldo;
    }

    public void setSaldo(int saldo) {
        Saldo = saldo;
    }
}
