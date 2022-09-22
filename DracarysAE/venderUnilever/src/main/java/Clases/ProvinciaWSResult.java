package Clases;

public class ProvinciaWSResult
{
    private int ProvinciaId;
    private String Nombre;
    private int CiudadId;

    public ProvinciaWSResult(int provinciaId, String nombre, int ciudadId) {
        ProvinciaId = provinciaId;
        Nombre = nombre;
        CiudadId = ciudadId;
    }

    public int getProvinciaId() {
        return ProvinciaId;
    }

    public void setProvinciaId(int provinciaId) {
        ProvinciaId = provinciaId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getCiudadId() {
        return CiudadId;
    }

    public void setCiudadId(int ciudadId) {
        CiudadId = ciudadId;
    }
}
