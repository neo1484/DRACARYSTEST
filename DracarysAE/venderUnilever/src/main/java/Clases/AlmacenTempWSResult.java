package Clases;

public class AlmacenTempWSResult
{
    private int AlmacenId;
    private String Descripcion;

    public AlmacenTempWSResult(int almacenId, String descripcion) {
        AlmacenId = almacenId;
        Descripcion = descripcion;
    }

    public int getAlmacenId() {
        return AlmacenId;
    }

    public void setAlmacenId(int almacenId) {
        AlmacenId = almacenId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
