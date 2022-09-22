package Clases;

public class MercadoWSResult
{
    private int MercadoId;
    private String Nombre;

    public MercadoWSResult(int mercadoId, String nombre) {
        MercadoId = mercadoId;
        Nombre = nombre;
    }

    public int getMercadoId() {
        return MercadoId;
    }

    public void setMercadoId(int mercadoId) {
        MercadoId = mercadoId;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
