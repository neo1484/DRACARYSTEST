package Clases;

public class ZonaMercadoWSResult
{
    private int ZonaMercadoId;
    private String Nombre;

    public ZonaMercadoWSResult(int zonaMercadoId, String nombre) {
        ZonaMercadoId = zonaMercadoId;
        Nombre = nombre;
    }

    public int getZonaMercadoId() {
        return ZonaMercadoId;
    }

    public void setZonaMercadoId(int zonaMercadoId) {
        ZonaMercadoId = zonaMercadoId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
