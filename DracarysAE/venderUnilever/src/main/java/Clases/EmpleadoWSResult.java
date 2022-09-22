package Clases;

public class EmpleadoWSResult
{
    private int EmpleadoId;
    private String NombreCompleto;

    public EmpleadoWSResult(int empleadoId, String nombreCompleto) {
        EmpleadoId = empleadoId;
        NombreCompleto = nombreCompleto;
    }

    public int getEmpleadoId() {
        return EmpleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        EmpleadoId = empleadoId;
    }

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }
}
