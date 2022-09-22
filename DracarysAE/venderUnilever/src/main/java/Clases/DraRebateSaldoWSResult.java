package Clases;

public class DraRebateSaldoWSResult
{
    private int ClienteId;
    private float Saldo;
    private float MaxDescuento;

    public DraRebateSaldoWSResult(int clienteId, float saldo, float maxDescuento) {
        ClienteId = clienteId;
        Saldo = saldo;
        MaxDescuento = maxDescuento;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float saldo) {
        Saldo = saldo;
    }

    public float getMaxDescuento() {
        return MaxDescuento;
    }

    public void setMaxDescuento(float maxDescuento) {
        MaxDescuento = maxDescuento;
    }
}
