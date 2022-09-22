package Clases;

public class AvanceVendedorDiaWSResult
{
    private int VendedorId;
    private String NombreVendedor;
    private int Anio;
    private int Mes;
    private int Dia;
    private float Presupuesto;
    private float Avance;
    private float Tendencia;
    private float Cobertura;
    private String Proveedor;
    private int NroPreVentas;
    private float CoberturaPorcentaje;

    public AvanceVendedorDiaWSResult(int vendedorId, String nombreVendedor, int anio, int mes,
                                     int dia, float presupuesto, float avance, float tendencia,
                                     float cobertura, String proveedor, int nroPreVentas,
                                     float coberturaPorcentaje) {
        VendedorId = vendedorId;
        NombreVendedor = nombreVendedor;
        Anio = anio;
        Mes = mes;
        Dia = dia;
        Presupuesto = presupuesto;
        Avance = avance;
        Tendencia = tendencia;
        Cobertura = cobertura;
        Proveedor = proveedor;
        NroPreVentas = nroPreVentas;
        CoberturaPorcentaje = coberturaPorcentaje;
    }

    public int getVendedorId() {
        return VendedorId;
    }

    public void setVendedorId(int vendedorId) {
        VendedorId = vendedorId;
    }

    public String getNombreVendedor() {
        return NombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        NombreVendedor = nombreVendedor;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
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

    public float getCobertura() {
        return Cobertura;
    }

    public void setCobertura(float cobertura) {
        Cobertura = cobertura;
    }

    public String getProveedor() {
        return Proveedor;
    }

    public void setProveedor(String proveedor) {
        Proveedor = proveedor;
    }

    public int getNroPreVentas() {
        return NroPreVentas;
    }

    public void setNroPreVentas(int nroPreVentas) {
        NroPreVentas = nroPreVentas;
    }

    public float getCoberturaPorcentaje() {
        return CoberturaPorcentaje;
    }

    public void setCoberturaPorcentaje(float coberturaPorcentaje) {
        CoberturaPorcentaje = coberturaPorcentaje;
    }
}
