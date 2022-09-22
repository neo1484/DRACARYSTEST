package Clases;

public class MotivoNoEntregaWSResult
{
    private int Id;
    private int MotivoId;
    private String Descripcion;

    public MotivoNoEntregaWSResult(int id, int motivoId, String descripcion) {
        Id = id;
        MotivoId = motivoId;
        Descripcion = descripcion;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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
