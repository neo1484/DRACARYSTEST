package Clases;

public class PromocionPrecioListWSResult
{
    private int PromocionId;
    private int PrecioListaId;

    public PromocionPrecioListWSResult(int promocionId, int precioListaId) {
        this.PromocionId = promocionId;
        PrecioListaId = precioListaId;
    }

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        this.PromocionId = promocionId;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }
}
