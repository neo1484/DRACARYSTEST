package Clases;

public class TipoCalleWSResult
{
    private int TipoCalleId;
    private String Descripcion;

    public TipoCalleWSResult(int tipoCalleId, String descripcion) {
        TipoCalleId = tipoCalleId;
        Descripcion = descripcion;
    }

    public int getTipoCalleId() {
        return TipoCalleId;
    }

    public void setTipoCalleId(int tipoCalleId) {
        TipoCalleId = tipoCalleId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
