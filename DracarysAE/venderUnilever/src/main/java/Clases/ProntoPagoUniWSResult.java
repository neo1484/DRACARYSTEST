package Clases;

public class ProntoPagoUniWSResult
{
    private int ProntoPagoId;
    private String Descripcion;

    public ProntoPagoUniWSResult(int prontoPagoId, String descripcion) {
        ProntoPagoId = prontoPagoId;
        Descripcion = descripcion;
    }

    public int getProntoPagoId() {
        return ProntoPagoId;
    }

    public void setProntoPagoId(int prontoPagoId) {
        ProntoPagoId = prontoPagoId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }
}
