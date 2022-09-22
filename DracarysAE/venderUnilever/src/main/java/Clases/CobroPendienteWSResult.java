package Clases;

public class CobroPendienteWSResult
{
    private int VentaId;
    private int ClienteId;
    private String Cliente;
    private String FechaFD;
    private float MontoFinal;
    private String VencimientoFD;
    private int DiasMora;
    private float Saldo;

    public CobroPendienteWSResult(int ventaId, int clienteId, String cliente, String fechaFD,
                                  float montoFinal, String vencimientoFD, int diasMora, float saldo) {
        VentaId = ventaId;
        ClienteId = clienteId;
        Cliente = cliente;
        FechaFD = fechaFD;
        MontoFinal = montoFinal;
        VencimientoFD = vencimientoFD;
        DiasMora = diasMora;
        Saldo = saldo;
    }

    public int getVentaId() {
        return VentaId;
    }

    public void setVentaId(int ventaId) {
        VentaId = ventaId;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getFechaFD() {
        return FechaFD;
    }

    public void setFechaFD(String fechaFD) {
        FechaFD = fechaFD;
    }

    public float getMontoFinal() {
        return MontoFinal;
    }

    public void setMontoFinal(float montoFinal) {
        MontoFinal = montoFinal;
    }

    public String getVencimientoFD() {
        return VencimientoFD;
    }

    public void setVencimientoFD(String vencimientoFD) {
        VencimientoFD = vencimientoFD;
    }

    public int getDiasMora() {
        return DiasMora;
    }

    public void setDiasMora(int diasMora) {
        DiasMora = diasMora;
    }

    public float getSaldo() {
        return Saldo;
    }

    public void setSaldo(float saldo) {
        Saldo = saldo;
    }
}
