package Clases;

public class PreventaBonificacionWSResult
{
    private int PreVentaId;
    private int BonificacionId;

    public int getPreVentaId() {
        return PreVentaId;
    }

    public void setPreVentaId(int preVentaId) {
        PreVentaId = preVentaId;
    }

    public int getBonificacionId() {
        return BonificacionId;
    }

    public void setBonificacionId(int bonificacionId) {
        BonificacionId = bonificacionId;
    }

    public PreventaBonificacionWSResult(int preVentaId, int bonificacionId) {
        PreVentaId = preVentaId;
        BonificacionId = bonificacionId;
    }
}
