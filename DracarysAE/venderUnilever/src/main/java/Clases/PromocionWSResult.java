package Clases;

public class PromocionWSResult
{
    private int PromocionId;
    private String Descripcion;
    private String Descripcion25;
    private boolean Activa;
    private int ProveedorId;

    public PromocionWSResult(int promocionId, String descripcion, String descripcion25, boolean activa, int proveedorId) {
        PromocionId = promocionId;
        Descripcion = descripcion;
        Descripcion25 = descripcion25;
        Activa = activa;
        ProveedorId = proveedorId;
    }

    public int getPromocionId() {
        return PromocionId;
    }

    public void setPromocionId(int promocionId) {
        PromocionId = promocionId;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDescripcion25() {
        return Descripcion25;
    }

    public void setDescripcion25(String descripcion25) {
        Descripcion25 = descripcion25;
    }

    public boolean isActiva() {
        return Activa;
    }

    public void setActiva(boolean activa) {
        Activa = activa;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }
}
