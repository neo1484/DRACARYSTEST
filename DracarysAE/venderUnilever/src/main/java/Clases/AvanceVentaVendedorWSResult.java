package Clases;

public class AvanceVentaVendedorWSResult
{
    private int VendedorId;
    private int Via;
    private int Mes;
    private int Anio;
    private String NombreVendedor;
    private float Presupuesto;
    private float Avance;
    private float Tendencia;
    private int Cobertura;
    private String Objeto;
    private float CoberturaPorcentaje;

    public AvanceVentaVendedorWSResult(int vendedorId, int via, int mes, int anio, String nombreVendedor,
                                       float presupuesto, float avance, float tendencia, int cobertura,
                                       String objeto, float coberturaPorcentaje) {
        VendedorId = vendedorId;
        Via = via;
        Mes = mes;
        Anio = anio;
        NombreVendedor = nombreVendedor;
        Presupuesto = presupuesto;
        Avance = avance;
        Tendencia = tendencia;
        Cobertura = cobertura;
        Objeto = objeto;
        CoberturaPorcentaje = coberturaPorcentaje;
    }

    public int getVendedorId() {
        return VendedorId;
    }

    public void setVendedorId(int vendedorId) {
        VendedorId = vendedorId;
    }

    public int getVia() {
        return Via;
    }

    public void setVia(int via) {
        Via = via;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }

    public String getNombreVendedor() {
        return NombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        NombreVendedor = nombreVendedor;
    }

    public float getPresupuesto() {
        return Presupuesto;
    }

    public void setPresupuesto(float presupuesto) {
        Presupuesto = presupuesto;
    }

    public float getAvance() {
        return Avance;
    }

    public void setAvance(float avance) {
        Avance = avance;
    }

    public float getTendencia() {
        return Tendencia;
    }

    public void setTendencia(float tendencia) {
        Tendencia = tendencia;
    }

    public int getCobertura() {
        return Cobertura;
    }

    public void setCobertura(int cobertura) {
        Cobertura = cobertura;
    }

    public String getObjeto() {
        return Objeto;
    }

    public void setObjeto(String objeto) {
        Objeto = objeto;
    }

    public float getCoberturaPorcentaje() {
        return CoberturaPorcentaje;
    }

    public void setCoberturaPorcentaje(float coberturaPorcentaje) {
        CoberturaPorcentaje = coberturaPorcentaje;
    }
}
