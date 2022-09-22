package Clases;

public class MotivoPopWSResult
{
    private int MotivoPopId;
    private String Descripcion;

    public MotivoPopWSResult(int motivoPopId, String descripcion) {
        MotivoPopId = motivoPopId;
        Descripcion = descripcion;
    }

    public int getMotivoPopId() {
        return MotivoPopId;
    }

    public void setMotivoPopId(int motivoPopId) {
        MotivoPopId = motivoPopId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
