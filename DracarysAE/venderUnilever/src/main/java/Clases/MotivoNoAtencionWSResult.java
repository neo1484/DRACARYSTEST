package Clases;

public class MotivoNoAtencionWSResult
{
    private int MotivoId;
    private String Descripcion;

    public MotivoNoAtencionWSResult(int motivoId, String descripcion) {
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
