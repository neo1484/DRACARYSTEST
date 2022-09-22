package Clases;

public class VendedorZonaVentaWSResult
{
    private int ZonaVentaId;
    private String RutaId;

    public VendedorZonaVentaWSResult(int zonaVentaId, String rutaId) {
        ZonaVentaId = zonaVentaId;
        RutaId = rutaId;
    }

    public int getZonaVentaId() {
        return ZonaVentaId;
    }

    public void setZonaVentaId(int zonaVentaId) {
        ZonaVentaId = zonaVentaId;
    }

    public String getRutaId() {
        return RutaId;
    }

    public void setRutaId(String rutaId) {
        RutaId = rutaId;
    }
}
