package Clases;

public class DistribuidorWSResult
{
    private int EmpleadoId;
    private String NombreCompleto;

    public DistribuidorWSResult(int empleadoId, String nombreCompleto) {
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
