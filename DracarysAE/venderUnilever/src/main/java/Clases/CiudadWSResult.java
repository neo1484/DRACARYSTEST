package Clases;

public class CiudadWSResult
{
    private int CiudadId;
    private String Nombre;

    public CiudadWSResult(int ciudadId, String nombre) {
        CiudadId = ciudadId;
        Nombre = nombre;
    }

    public int getCiudadId() {
        return CiudadId;
    }

    public void setCiudadId(int ciudadId) {
        CiudadId = ciudadId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
