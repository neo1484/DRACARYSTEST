package Clases;

public class TipoNegocioWSResult
{
    private int TipoId;
    private String Descripcion;

    public TipoNegocioWSResult(int tipoId, String descripcion) {
        TipoId = tipoId;
        Descripcion = descripcion;
    }

    public int getTipoId() {
        return TipoId;
    }

    public void setTipoId(int tipoId) {
        TipoId = tipoId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
