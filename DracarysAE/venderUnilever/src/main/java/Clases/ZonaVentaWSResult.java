package Clases;

public class ZonaVentaWSResult
{
    private int ZonaId;
    private String Nombre;

    public ZonaVentaWSResult(int zonaId, String nombre) {
        ZonaId = zonaId;
        Nombre = nombre;
    }

    public int getZonaId() {
        return ZonaId;
    }

    public void setZonaId(int zonaId) {
        ZonaId = zonaId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
