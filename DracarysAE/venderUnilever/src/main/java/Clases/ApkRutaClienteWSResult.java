package Clases;

public class ApkRutaClienteWSResult
{
    private int RutaId;
    private int DiaId;
    private String RutaNombre;
    private String DiaNombre;
    private boolean BloquearPreventaDistancia;
    private boolean RutaCompleta;

    public ApkRutaClienteWSResult(int rutaId, int diaId, String rutaNombre, String diaNombre, boolean bloquearPreventaDistancia, boolean rutaCompleta) {
        RutaId = rutaId;
        DiaId = diaId;
        RutaNombre = rutaNombre;
        DiaNombre = diaNombre;
        BloquearPreventaDistancia = bloquearPreventaDistancia;
        RutaCompleta = rutaCompleta;
    }

    public int getRutaId() {
        return RutaId;
    }

    public void setRutaId(int rutaId) {
        RutaId = rutaId;
    }

    public int getDiaId() {
        return DiaId;
    }

    public void setDiaId(int diaId) {
        DiaId = diaId;
    }

    public String getRutaNombre() {
        return RutaNombre;
    }

    public void setRutaNombre(String rutaNombre) {
        RutaNombre = rutaNombre;
    }

    public String getDiaNombre() {
        return DiaNombre;
    }

    public void setDiaNombre(String diaNombre) {
        DiaNombre = diaNombre;
    }

    public boolean isBloquearPreventaDistancia() {
        return BloquearPreventaDistancia;
    }

    public void setBloquearPreventaDistancia(boolean bloquearPreventaDistancia) {
        BloquearPreventaDistancia = bloquearPreventaDistancia;
    }

    public boolean isRutaCompleta() {
        return RutaCompleta;
    }

    public void setRutaCompleta(boolean rutaCompleta) {
        RutaCompleta = rutaCompleta;
    }
}
