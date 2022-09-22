package Clases;

public class ProntoPagoClienteWSResult
{
    private int ProntoPagoId;
    private int ClienteId;

    public ProntoPagoClienteWSResult(int prontoPagoId, int clienteId) {
        ProntoPagoId = prontoPagoId;
        ClienteId = clienteId;
    }

    public int getProntoPagoId() {
        return ProntoPagoId;
    }

    public void setProntoPagoId(int prontoPagoId) {
        ProntoPagoId = prontoPagoId;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }
}
