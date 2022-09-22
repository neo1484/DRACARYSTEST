package Clases;

public class DiaSemanaWSResult
{
    private int DiaId;
    private String Descripcion;

    public DiaSemanaWSResult(int diaId, String descripcion) {
        DiaId = diaId;
        Descripcion = descripcion;
    }

    public int getDiaId() {
        return DiaId;
    }

    public void setDiaId(int diaId) {
        DiaId = diaId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
