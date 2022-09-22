package Clases;

public class MotivoEliminacionMatchWSResult
{
    private int MotivoId;
    private String Descripcion;

    public MotivoEliminacionMatchWSResult(int motivoId, String descripcion) {
        MotivoId = motivoId;
        Descripcion = descripcion;
    }

    public int getMotivoId() {
        return MotivoId;
    }

    public void setMotivoId(int motivoId) {
        MotivoId = motivoId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
