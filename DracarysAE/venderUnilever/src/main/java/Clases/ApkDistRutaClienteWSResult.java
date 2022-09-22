package Clases;

public class ApkDistRutaClienteWSResult
{
    private int RutaId;
    private int DiaId;
    private String RutaNombre;
    private String DiaNombre;

    public ApkDistRutaClienteWSResult(int rutaId, int diaId, String rutaNombre, String diaNombre) {
        RutaId = rutaId;
        DiaId = diaId;
        RutaNombre = rutaNombre;
        DiaNombre = diaNombre;
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
}
