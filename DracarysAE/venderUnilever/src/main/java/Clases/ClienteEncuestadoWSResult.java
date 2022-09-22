package Clases;

public class ClienteEncuestadoWSResult
{
    private int ClienteId;
    private int EncuestaId;

    public ClienteEncuestadoWSResult(int clienteId, int encuestaId) {
        ClienteId = clienteId;
        EncuestaId = encuestaId;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public int getEncuestaId() {
        return EncuestaId;
    }

    public void setEncuestaId(int encuestaId) {
        EncuestaId = encuestaId;
    }
}
