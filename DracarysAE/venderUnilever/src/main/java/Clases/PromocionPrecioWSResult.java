package Clases;

public class PromocionPrecioWSResult
{
    private int PrecioId;
    private int PromocionId;
    private int PrecioListaId;
    private float Precio;
    private float PrecioOriginal;

    public PromocionPrecioWSResult(int precioId, int promocionId, int precioListaId, float precio, float precioOriginal) {
        PrecioId = precioId;
        PromocionId = promocionId;
        PrecioListaId = precioListaId;
        Precio = precio;
        PrecioOriginal = precioOriginal;
    }

    public int getPrecioId() {
        return PrecioId;
    }

    public void setPrecioId(int precioId) {
        PrecioId = precioId;
    }

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        PromocionId = promocionId;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }

    public float getPrecio() {
        return Precio;
    }

    public void setPrecio(float precio) {
        Precio = precio;
    }

    public float getPrecioOriginal() {
        return PrecioOriginal;
    }

    public void setPrecioOriginal(float precioOriginal) {
        PrecioOriginal = precioOriginal;
    }
}
