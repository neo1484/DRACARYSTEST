package Clases;

public class CanalRutaPrecioWSResult
{
    private int CanalPrecioRutaId;
    private int CanalRutaId;
    private int ProductoId;
    private float Hpb;
    private float DescuentoCanal;
    private float DescuentoAjuste;

    public CanalRutaPrecioWSResult(int canalPrecioRutaId, int canalRutaId, int productoId, float hpb, float descuentoCanal, float descuentoAjuste) {
        CanalPrecioRutaId = canalPrecioRutaId;
        CanalRutaId = canalRutaId;
        ProductoId = productoId;
        Hpb = hpb;
        DescuentoCanal = descuentoCanal;
        DescuentoAjuste = descuentoAjuste;
    }

    public int getCanalPrecioRutaId() {
        return CanalPrecioRutaId;
    }

    public void setCanalPrecioRutaId(int canalPrecioRutaId) {
        CanalPrecioRutaId = canalPrecioRutaId;
    }

    public int getCanalRutaId() {
        return CanalRutaId;
    }

    public void setCanalRutaId(int canalRutaId) {
        CanalRutaId = canalRutaId;
    }

    public int getProductoId() {
        return ProductoId;
    }

    public void setProductoId(int productoId) {
        ProductoId = productoId;
    }

    public float getHpb() {
        return Hpb;
    }

    public void setHpb(float hpb) {
        Hpb = hpb;
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
}
