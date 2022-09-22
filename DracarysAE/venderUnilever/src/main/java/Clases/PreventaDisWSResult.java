package Clases;

public class PreventaDisWSResult
{
    private int Id;
    private int PreVentaId;
    private int Dia;
    private int Mes;
    private int Anio;
    private int ClienteId;
    private float Monto;
    private float Descuento;
    private float MontoFinal;
    private int TipoPagoId;
    private String TipoPagoFD;
    private String ClienteFD;
    private String FechaFD;
    private String MontoFD;
    private int EstadoEntrega;
    private boolean EstadoSincronizacion;
    private int EmpleadoId;
    private String Nit;
    private String NombreFactura;
    private String Observacion;
    private float Descuento2;
    private int DosificacionId;
    private float DescuentoCanal;
    private float DescuentoAjuste;
    private float DescuentoProntoPago;
    private float DescuentoObjetivo;
    private int ProntoPagoId;
    private String CodTransferencia;
    private boolean FromApk;
    private boolean FromShopping;
    private float DescuentoSocio;
    private float DescuentoIncentivo;

    public PreventaDisWSResult(int id, int preVentaId, int dia, int mes, int anio, int clienteId, float monto,
                               float descuento, float montoFinal, int tipoPagoId, String tipoPagoFD, String clienteFD,
                               String fechaFD, String montoFD, int estadoEntrega, boolean estadoSincronizacion,
                               int empleadoId, String nit, String nombreFactura, String observacion, float descuento2,
                               int dosificacionId, float descuentoCanal, float descuentoAjuste, float descuentoProntoPago,
                               float descuentoObjetivo, int prontoPagoId, String codTransferencia, boolean fromApk,
                               boolean fromShopping, float descuentoSocio, float descuentoIncentivo) {
        Id = id;
        PreVentaId = preVentaId;
        Dia = dia;
        Mes = mes;
        Anio = anio;
        ClienteId = clienteId;
        Monto = monto;
        Descuento = descuento;
        MontoFinal = montoFinal;
        TipoPagoId = tipoPagoId;
        TipoPagoFD = tipoPagoFD;
        ClienteFD = clienteFD;
        FechaFD = fechaFD;
        MontoFD = montoFD;
        EstadoEntrega = estadoEntrega;
        EstadoSincronizacion = estadoSincronizacion;
        EmpleadoId = empleadoId;
        Nit = nit;
        NombreFactura = nombreFactura;
        Observacion = observacion;
        Descuento2 = descuento2;
        DosificacionId = dosificacionId;
        DescuentoCanal = descuentoCanal;
        DescuentoAjuste = descuentoAjuste;
        DescuentoProntoPago = descuentoProntoPago;
        DescuentoObjetivo = descuentoObjetivo;
        ProntoPagoId = prontoPagoId;
        CodTransferencia = codTransferencia;
        FromApk = fromApk;
        FromShopping = fromShopping;
        DescuentoSocio = descuentoSocio;
        DescuentoIncentivo = descuentoIncentivo;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPreVentaId() {
        return PreVentaId;
    }

    public void setPreVentaId(int preVentaId) {
        PreVentaId = preVentaId;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
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

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float descuento) {
        Descuento = descuento;
    }

    public float getMontoFinal() {
        return MontoFinal;
    }

    public void setMontoFinal(float montoFinal) {
        MontoFinal = montoFinal;
    }

    public int getTipoPagoId() {
        return TipoPagoId;
    }

    public void setTipoPagoId(int tipoPagoId) {
        TipoPagoId = tipoPagoId;
    }

    public String getTipoPagoFD() {
        return TipoPagoFD;
    }

    public void setTipoPagoFD(String tipoPagoFD) {
        TipoPagoFD = tipoPagoFD;
    }

    public String getClienteFD() {
        return ClienteFD;
    }

    public void setClienteFD(String clienteFD) {
        ClienteFD = clienteFD;
    }

    public String getFechaFD() {
        return FechaFD;
    }

    public void setFechaFD(String fechaFD) {
        FechaFD = fechaFD;
    }

    public String getMontoFD() {
        return MontoFD;
    }

    public void setMontoFD(String montoFD) {
        MontoFD = montoFD;
    }

    public int getEstadoEntrega() {
        return EstadoEntrega;
    }

    public void setEstadoEntrega(int estadoEntrega) {
        EstadoEntrega = estadoEntrega;
    }

    public boolean isEstadoSincronizacion() {
        return EstadoSincronizacion;
    }

    public void setEstadoSincronizacion(boolean estadoSincronizacion) {
        EstadoSincronizacion = estadoSincronizacion;
    }

    public int getEmpleadoId() {
        return EmpleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        EmpleadoId = empleadoId;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    public String getNombreFactura() {
        return NombreFactura;
    }

    public void setNombreFactura(String nombreFactura) {
        NombreFactura = nombreFactura;
    }

    public String getObservacion() {
        return Observacion;
    }

    public void setObservacion(String observacion) {
        Observacion = observacion;
    }

    public float getDescuento2() {
        return Descuento2;
    }

    public void setDescuento2(float descuento2) {
        Descuento2 = descuento2;
    }

    public int getDosificacionId() {
        return DosificacionId;
    }

    public void setDosificacionId(int dosificacionId) {
        DosificacionId = dosificacionId;
    }

    public float getDescuentoCanal() {
        return DescuentoCanal;
    }

    public void setDescuentoCanal(float descuentoCanal) {
        DescuentoCanal = descuentoCanal;
    }

    public float getDescuentoAjuste() {
        return DescuentoAjuste;
    }

    public void setDescuentoAjuste(float descuentoAjuste) {
        DescuentoAjuste = descuentoAjuste;
    }

    public float getDescuentoProntoPago() {
        return DescuentoProntoPago;
    }

    public void setDescuentoProntoPago(float descuentoProntoPago) {
        DescuentoProntoPago = descuentoProntoPago;
    }

    public float getDescuentoObjetivo() {
        return DescuentoObjetivo;
    }

    public void setDescuentoObjetivo(float descuentoObjetivo) {
        DescuentoObjetivo = descuentoObjetivo;
    }

    public int getProntoPagoId() {
        return ProntoPagoId;
    }

    public void setProntoPagoId(int prontoPagoId) {
        ProntoPagoId = prontoPagoId;
    }

    public String getCodTransferencia() {
        return CodTransferencia;
    }

    public void setCodTransferencia(String codTransferencia) {
        CodTransferencia = codTransferencia;
    }

    public boolean isFromApk() {
        return FromApk;
    }

    public void setFromApk(boolean fromApk) {
        FromApk = fromApk;
    }

    public boolean isFromShopping() {
        return FromShopping;
    }

    public void setFromShopping(boolean fromShopping) {
        FromShopping = fromShopping;
    }

    public float getDescuentoSocio() {
        return DescuentoSocio;
    }

    public void setDescuentoSocio(float descuentoSocio) {
        DescuentoSocio = descuentoSocio;
    }

    public float getDescuentoIncentivo() {
        return DescuentoIncentivo;
    }

    public void setDescuentoIncentivo(float descuentoIncentivo) {
        DescuentoIncentivo = descuentoIncentivo;
    }
}
