package Clases;

public class ZonaWSResult
{
    private int ZonaId;
    private String Nombre;

    public ZonaWSResult(int zonaId, String nombre) {
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
