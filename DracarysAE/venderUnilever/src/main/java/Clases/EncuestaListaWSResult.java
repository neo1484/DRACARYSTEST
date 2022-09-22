package Clases;

public class EncuestaListaWSResult
{
    private int DetalleId;
    private String Valor;

    public EncuestaListaWSResult(int detalleId, String valor) {
        DetalleId = detalleId;
        Valor = valor;
    }

    public int getDetalleId() {
        return DetalleId;
    }

    public void setDetalleId(int detalleId) {
        DetalleId = detalleId;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }
}
