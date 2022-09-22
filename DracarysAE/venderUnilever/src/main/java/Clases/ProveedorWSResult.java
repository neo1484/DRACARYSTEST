package Clases;

public class ProveedorWSResult
{
    private int ProveedorId;
    private String Nombre;

    public ProveedorWSResult(int proveedorId, String nombre) {
        ProveedorId = proveedorId;
        Nombre = nombre;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
