package Clases;

public class ProntoPagoJerarquiaWSResult
{
    private int ProntoPagoId;
    private String Jerarquia;
    private int JerarquiaId;
    private float Descuento;

    public ProntoPagoJerarquiaWSResult(int prontoPagoId, String jerarquia, int jerarquiaId, float descuento) {
        ProntoPagoId = prontoPagoId;
        Jerarquia = jerarquia;
        JerarquiaId = jerarquiaId;
        Descuento = descuento;
    }

    public int getProntoPagoId() {
        return ProntoPagoId;
    }

    public void setProntoPagoId(int prontoPagoId) {
        ProntoPagoId = prontoPagoId;
    }

    public String getJerarquia() {
        return Jerarquia;
    }

    public void setJerarquia(String jerarquia) {
        Jerarquia = jerarquia;
    }

    public int getJerarquiaId() {
        return JerarquiaId;
    }

    public void setJerarquiaId(int jerarquiaId) {
        JerarquiaId = jerarquiaId;
    }

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float descuento) {
        Descuento = descuento;
    }
}
