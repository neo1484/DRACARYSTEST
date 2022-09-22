package Clases;

public class EncuestaWSResult
{
    private int EncuestaId;
    private String Nombre;
    private boolean Activa;

    public EncuestaWSResult(int encuestaId, String nombre, boolean activa) {
        EncuestaId = encuestaId;
        Nombre = nombre;
        Activa = activa;
    }

    public int getEncuestaId() {
        return EncuestaId;
    }

    public void setEncuestaId(int encuestaId) {
        EncuestaId = encuestaId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public boolean isActiva() {
        return Activa;
    }

    public void setActiva(boolean activa) {
        Activa = activa;
    }
}
