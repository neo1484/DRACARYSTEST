package Clases;

public class RutaWSResult
{
    private int RutaId;
    private String Descripcion;

    public RutaWSResult(int rutaId, String descripcion) {
        RutaId = rutaId;
        Descripcion = descripcion;
    }

    public int getRutaId() {
        return RutaId;
    }

    public void setRutaId(int rutaId) {
        RutaId = rutaId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
