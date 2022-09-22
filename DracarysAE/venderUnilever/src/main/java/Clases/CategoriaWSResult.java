package Clases;

public class CategoriaWSResult
{
    private int Id;
    private int CategoriaId;
    private String Nombre;
    private int PadreId;
    private int Hijos;
    private int ProveedorId;
    private int Jerarquia1Id;
    private int Jerarquia2Id;
    private int Jerarquia3Id;
    private int Jerarquia5Id;

    public CategoriaWSResult(int id, int categoriaId, String nombre, int padreId, int hijos, int proveedorId, int jerarquia1Id, int jerarquia2Id, int jerarquia3Id, int jerarquia5Id) {
        Id = id;
        CategoriaId = categoriaId;
        Nombre = nombre;
        PadreId = padreId;
        Hijos = hijos;
        ProveedorId = proveedorId;
        Jerarquia1Id = jerarquia1Id;
        Jerarquia2Id = jerarquia2Id;
        Jerarquia3Id = jerarquia3Id;
        Jerarquia5Id = jerarquia5Id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public int getPadreId() {
        return PadreId;
    }

    public void setPadreId(int padreId) {
        PadreId = padreId;
    }

    public int getHijos() {
        return Hijos;
    }

    public void setHijos(int hijos) {
        Hijos = hijos;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }

    public int getJerarquia1Id() {
        return Jerarquia1Id;
    }

    public void setJerarquia1Id(int jerarquia1Id) {
        Jerarquia1Id = jerarquia1Id;
    }

    public int getJerarquia2Id() {
        return Jerarquia2Id;
    }

    public void setJerarquia2Id(int jerarquia2Id) {
        Jerarquia2Id = jerarquia2Id;
    }

    public int getJerarquia3Id() {
        return Jerarquia3Id;
    }

    public void setJerarquia3Id(int jerarquia3Id) {
        Jerarquia3Id = jerarquia3Id;
    }

    public int getJerarquia5Id() {
        return Jerarquia5Id;
    }

    public void setJerarquia5Id(int jerarquia5Id) {
        Jerarquia5Id = jerarquia5Id;
    }
}
