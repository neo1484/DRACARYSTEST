package Clases;

public class FotoCategoriaWSResult
{
    private int CategoriaId;
    private String Descripcion;

    public FotoCategoriaWSResult(int categoriaId, String descripcion) {
        CategoriaId = categoriaId;
        Descripcion = descripcion;
    }

    public int getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        CategoriaId = categoriaId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
