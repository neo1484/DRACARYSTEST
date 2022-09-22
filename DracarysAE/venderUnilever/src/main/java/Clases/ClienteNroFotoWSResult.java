package Clases;

public class ClienteNroFotoWSResult
{
    private int ClienteId;
    private int Numero;

    public ClienteNroFotoWSResult(int clienteId, int numero) {
        ClienteId = clienteId;
        Numero = numero;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }
}
