package Clases;

public class CanalRutaWSResult
{
    private int CanalRutaId;
    private String Descripcion;

    public CanalRutaWSResult(int canalRutaId, String descripcion) {
        CanalRutaId = canalRutaId;
        Descripcion = descripcion;
    }

    public int getCanalRutaId() {
        return CanalRutaId;
    }

    public void setCanalRutaId(int canalRutaId) {
        CanalRutaId = canalRutaId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
