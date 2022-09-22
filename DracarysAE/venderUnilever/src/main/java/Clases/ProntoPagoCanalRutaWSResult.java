package Clases;

public class ProntoPagoCanalRutaWSResult
{
    private int ProntoPagoId;
    private int CanalRutaId;

    public ProntoPagoCanalRutaWSResult(int prontoPagoId, int canalRutaId) {
        ProntoPagoId = prontoPagoId;
        CanalRutaId = canalRutaId;
    }

    public int getProntoPagoId() {
        return ProntoPagoId;
    }

    public void setProntoPagoId(int prontoPagoId) {
        ProntoPagoId = prontoPagoId;
    }

    public int getCanalRutaId() {
        return CanalRutaId;
    }

    public void setCanalRutaId(int canalRutaId) {
        CanalRutaId = canalRutaId;
    }
}
