package Clases;

public class CategoriaPOPWSResult
{
    private int CategoriaId;
    private String Nombre;
    private int ProveedorId;

    public CategoriaPOPWSResult(int categoriaId, String nombre, int proveedorId) {
        CategoriaId = categoriaId;
        Nombre = nombre;
        ProveedorId = proveedorId;
    }

    public int getCategoriaId() {
        return CategoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        CategoriaId = categoriaId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }
}
