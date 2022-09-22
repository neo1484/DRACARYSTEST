package Clases;

public class CondicionTributariaWSResult
{
    private String Nit;
    private int Anio;
    private float MontoAcumulado;

    public CondicionTributariaWSResult(String nit, int anio, float montoAcumulado) {
        Nit = nit;
        Anio = anio;
        MontoAcumulado = montoAcumulado;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }

    public float getMontoAcumulado() {
        return MontoAcumulado;
    }

    public void setMontoAcumulado(float montoAcumulado) {
        MontoAcumulado = montoAcumulado;
    }
}
