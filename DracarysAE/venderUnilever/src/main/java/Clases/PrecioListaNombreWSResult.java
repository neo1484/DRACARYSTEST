package Clases;

public class PrecioListaNombreWSResult
{
    private int PrecioListaId;
    private String Descripcion;

    public PrecioListaNombreWSResult(int precioListaId, String descripcion) {
        PrecioListaId = precioListaId;
        Descripcion = descripcion;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
