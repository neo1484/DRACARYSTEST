package Clases;

public class PromocionTipoNegocioWSResult
{
    private int RegistroId;
    private int PromocionId;
    private int TipoNegocioId;

    public PromocionTipoNegocioWSResult(int registroId, int promocionId, int tipoNegocioId) {
        RegistroId = registroId;
        PromocionId = promocionId;
        TipoNegocioId = tipoNegocioId;
    }

    public int getRegistroId() {
        return RegistroId;
    }

    public void setRegistroId(int registroId) {
        RegistroId = registroId;
    }

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        PromocionId = promocionId;
    }

    public int getTipoNegocioId() {
        return TipoNegocioId;
    }

    public void setTipoNegocioId(int tipoNegocioId) {
        TipoNegocioId = tipoNegocioId;
    }
}
