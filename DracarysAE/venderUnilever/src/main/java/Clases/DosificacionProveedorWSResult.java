package Clases;

public class DosificacionProveedorWSResult
{
    private int DosificacionId;
    private int ProveedorId;
    private boolean Activa;
    private String Descripcion;
    private String DosificacionFD;

    public DosificacionProveedorWSResult(int dosificacionId, int proveedorId, boolean activa, String descripcion, String dosificacionFD) {
        DosificacionId = dosificacionId;
        ProveedorId = proveedorId;
        Activa = activa;
        Descripcion = descripcion;
        DosificacionFD = dosificacionFD;
    }

    public int getDosificacionId() {
        return DosificacionId;
    }

    public void setDosificacionId(int dosificacionId) {
        DosificacionId = dosificacionId;
    }

    public int getProveedorId() {
        return ProveedorId;
    }

    public void setProveedorId(int proveedorId) {
        ProveedorId = proveedorId;
    }

    public boolean isActiva() {
        return Activa;
    }

    public void setActiva(boolean activa) {
        Activa = activa;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDosificacionFD() {
        return DosificacionFD;
    }

    public void setDosificacionFD(String dosificacionFD) {
        DosificacionFD = dosificacionFD;
    }
}
