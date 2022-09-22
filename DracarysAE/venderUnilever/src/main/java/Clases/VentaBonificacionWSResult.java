package Clases;

public class VentaBonificacionWSResult
{
    public int VentaId;
    public int BonificacionId;

    public VentaBonificacionWSResult(int ventaId, int bonificacionId) {
        VentaId = ventaId;
        BonificacionId = bonificacionId;
    }

    public int getVentaId() {
        return VentaId;
    }

    public void setVentaId(int ventaId) {
        VentaId = ventaId;
    }

    public int getBonificacionId() {
        return BonificacionId;
    }

    public void setBonificacionId(int bonificacionId) {
        BonificacionId = bonificacionId;
    }
}
