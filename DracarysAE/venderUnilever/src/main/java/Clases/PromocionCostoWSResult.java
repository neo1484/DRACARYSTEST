package Clases;

public class PromocionCostoWSResult
{
    private int PromocionId;
    private int CostoId;

    public PromocionCostoWSResult(int promocionId, int costoId) {
        PromocionId = promocionId;
        CostoId = costoId;
    }

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        PromocionId = promocionId;
    }

    public int getCostoId() {
        return CostoId;
    }

    public void setCostoId(int costoId) {
        CostoId = costoId;
    }
}
