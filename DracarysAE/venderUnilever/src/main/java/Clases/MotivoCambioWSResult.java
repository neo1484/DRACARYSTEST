package Clases;

public class MotivoCambioWSResult
{
    private int MotivoCambioId;
    private String Descripcion;

    public MotivoCambioWSResult(int motivoCambioId, String descripcion) {
        MotivoCambioId = motivoCambioId;
        Descripcion = descripcion;
    }

    public int getMotivoCambioId() {
        return MotivoCambioId;
    }

    public void setMotivoCambioId(int motivoCambioId) {
        MotivoCambioId = motivoCambioId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
