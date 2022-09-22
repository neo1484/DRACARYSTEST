package Clases;

public class ClienteIncentivo
{
    private int IncentivoId;
    private int ClienteId;
    private float Monto;
    private float Saldo;
    private String CodigoIncentivo;
    private String DescripcionIncentivo;
    private boolean Check;

    public ClienteIncentivo(int incentivoId, int clienteId, float monto, float saldo, String codigoIncentivo, String descripcionIncentivo, boolean check)
    {
        IncentivoId = incentivoId;
        ClienteId = clienteId;
        Monto = monto;
        Saldo = saldo;
        CodigoIncentivo = codigoIncentivo;
        DescripcionIncentivo = descripcionIncentivo;
        Check = check;
    }

    public int getIncentivoId() {
        return IncentivoId;
    }

    public void setIncentivoId(int incentivoId) {
        IncentivoId = incentivoId;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public float getMonto() {
        return Monto;
    }

    public void setMonto(float monto) {
        Monto = monto;
    }

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float saldo) {
        Saldo = saldo;
    }

    public String getCodigoIncentivo() {
        return CodigoIncentivo;
    }

    public void setCodigoIncentivo(String codigoIncentivo) {
        CodigoIncentivo = codigoIncentivo;
    }

    public String getDescripcionIncentivo() {
        return DescripcionIncentivo;
    }

    public void setDescripcionIncentivo(String descripcionIncentivo) {
        DescripcionIncentivo = descripcionIncentivo;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }
}
