package Clases;

public class ApkClienteWS {
    private int ClienteId;
    private String Codigo;
    private int TipoNegocioId;
    private String Nombres;
    private String Paterno;
    private String Materno;
    private String ApellidoCasada;
    private String NombreCompleto;
    private String RazonSocial;
    private double Latitud;
    private double Longitud;
    private String Direccion;
    private String Telefono;
    private float Descuento;
    private int PrecioListaId;
    private String Celular;
    private int TipoPagoId;
    private int DiasPago;
    private float TopeCredito;
    private int RutaId;
    private int DiaId;
    private boolean AutoVenta;
    private String NombreFactura;
    private String Nit;
    private String Referencia;
    private float PromedioVenta;
    private String Ci;
    private float MontoPendienteCredito;
    private float MontoPendienteCuenta;
    private String TipoVisita;
    private String Ruta;
    private int ZonaVentaId;
    private String Zona;
    private String Mercado;
    private int CanalRutaId;
    private String Observacion;
    private boolean VerificadoFoto;
    private String PedidoExternoId;
    private String NombreVendedor;
    private String CelularVendedor;

    public ApkClienteWS(){}

    public ApkClienteWS(int clienteId, String codigo, int tipoNegocioId, String nombres, String paterno, String materno,
                        String apellidoCasada, String nombreCompleto, String razonSocial, double latitud, double longitud,
                        String direccion, String telefono, float descuento, int precioListaId, String celular, int tipoPagoId,
                        int diasPago, float topeCredito, int rutaId, int diaId, boolean autoVenta, String nombreFactura, String nit,
                        String referencia, float promedioVenta, String ci, float montoPendienteCredito, float montoPendienteCuenta,
                        String tipoVisita, String ruta, int zonaVentaId, String zona, String mercado, int canalRutaId,
                        String observacion, boolean verificadoFoto, String pedidoExternoId, String nombreVendedor,
                        String celularVendedor)
    {
        super();
        ClienteId = clienteId;
        Codigo = codigo;
        TipoNegocioId = tipoNegocioId;
        Nombres = nombres;
        Paterno = paterno;
        Materno = materno;
        ApellidoCasada = apellidoCasada;
        NombreCompleto = nombreCompleto;
        RazonSocial = razonSocial;
        Latitud = latitud;
        Longitud = longitud;
        Direccion = direccion;
        Telefono = telefono;
        Descuento = descuento;
        PrecioListaId = precioListaId;
        Celular = celular;
        TipoPagoId = tipoPagoId;
        DiasPago = diasPago;
        TopeCredito = topeCredito;
        RutaId = rutaId;
        DiaId = diaId;
        AutoVenta = autoVenta;
        NombreFactura = nombreFactura;
        Nit = nit;
        Referencia = referencia;
        PromedioVenta = promedioVenta;
        Ci = ci;
        MontoPendienteCredito = montoPendienteCredito;
        MontoPendienteCuenta = montoPendienteCuenta;
        TipoVisita = tipoVisita;
        Ruta = ruta;
        ZonaVentaId = zonaVentaId;
        Zona = zona;
        Mercado = mercado;
        CanalRutaId = canalRutaId;
        Observacion = observacion;
        VerificadoFoto = verificadoFoto;
        PedidoExternoId = pedidoExternoId;
        NombreVendedor = nombreVendedor;
        CelularVendedor = celularVendedor;
    }

    public int getClienteId() {
        return ClienteId;
    }
    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }
    public String getCodigo() {
        return Codigo;
    }
    public void setCodigo(String codigo) {
        Codigo = codigo;
    }
    public int getTipoNegocioId() {
        return TipoNegocioId;
    }
    public void setTipoNegocioId(int tipoNegocioId) {
        TipoNegocioId = tipoNegocioId;
    }
    public String getNombres() {
        return Nombres;
    }
    public void setNombres(String nombres) {
        Nombres = nombres;
    }
    public String getPaterno() {
        return Paterno;
    }
    public void setPaterno(String paterno) {
        Paterno = paterno;
    }
    public String getMaterno() {
        return Materno;
    }
    public void setMaterno(String materno) {
        Materno = materno;
    }
    public String getApellidoCasada() {
        return ApellidoCasada;
    }
    public void setApellidoCasada(String apellidoCasada) {
        ApellidoCasada = apellidoCasada;
    }
    public String getNombreCompleto() {
        return NombreCompleto;
    }
    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
    }
    public String getRazonSocial() {
        return RazonSocial;
    }
    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }
    public double getLatitud() {
        return Latitud;
    }
    public void setLatitud(double latitud) {
        Latitud = latitud;
    }
    public double getLongitud() {
        return Longitud;
    }
    public void setLongitud(double longitud) {
        Longitud = longitud;
    }
    public String getDireccion() {
        return Direccion;
    }
    public void setDireccion(String direccion) {
        Direccion = direccion;
    }
    public String getTelefono() {
        return Telefono;
    }
    public void setTelefono(String telefono) {
        Telefono = telefono;
    }
    public float getDescuento() {
        return Descuento;
    }
    public void setDescuento(float descuento) {
        Descuento = descuento;
    }
    public int getPrecioListaId() {
        return PrecioListaId;
    }
    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }
    public String getCelular() {
        return Celular;
    }
    public void setCelular(String celular) {
        Celular = celular;
    }
    public int getTipoPagoId() {
        return TipoPagoId;
    }
    public void setTipoPagoId(int tipoPagoId) {
        TipoPagoId = tipoPagoId;
    }
    public int getDiasPago() {
        return DiasPago;
    }
    public void setDiasPago(int diasPago) {
        DiasPago = diasPago;
    }
    public float getTopeCredito() {
        return TopeCredito;
    }
    public void setTopeCredito(float topeCredito) {
        TopeCredito = topeCredito;
    }
    public int getRutaId() {
        return RutaId;
    }
    public void setRutaId(int rutaId) {
        RutaId = rutaId;
    }
    public int getDiaId() {
        return DiaId;
    }
    public void setDiaId(int diaId) {
        DiaId = diaId;
    }
    public boolean isAutoVenta() {
        return AutoVenta;
    }
    public void setAutoVenta(boolean autoVenta) {
        AutoVenta = autoVenta;
    }
    public String getNombreFactura() {
        return NombreFactura;
    }
    public void setNombreFactura(String nombreFactura) {
        NombreFactura = nombreFactura;
    }
    public String getNit() {
        return Nit;
    }
    public void setNit(String nit) {
        Nit = nit;
    }
    public String getReferencia() {
        return Referencia;
    }
    public void setReferencia(String referencia) {
        Referencia = referencia;
    }
    public float getPromedioVenta() {
        return PromedioVenta;
    }
    public void setPromedioVenta(float promedioVenta) {
        PromedioVenta = promedioVenta;
    }
    public String getCi() {
        return Ci;
    }
    public void setCi(String ci) {
        Ci = ci;
    }
    public float getMontoPendienteCredito() {
        return MontoPendienteCredito;
    }
    public void setMontoPendienteCredito(float montoPendienteCredito) {
        MontoPendienteCredito = montoPendienteCredito;
    }
    public float getMontoPendienteCuenta() {
        return MontoPendienteCuenta;
    }
    public void setMontoPendienteCuenta(float montoPendienteCuenta) {
        MontoPendienteCuenta = montoPendienteCuenta;
    }
    public String getTipoVisita() {
        return TipoVisita;
    }
    public void setTipoVisita(String tipoVisita) {
        TipoVisita = tipoVisita;
    }
    public String getRuta() {
        return Ruta;
    }
    public void setRuta(String ruta) {
        Ruta = ruta;
    }
    public int getZonaVentaId() {
        return ZonaVentaId;
    }
    public void setZonaVentaId(int zonaVentaId) {
        ZonaVentaId = zonaVentaId;
    }
    public String getZona() {
        return Zona;
    }
    public void setZona(String zona) {
        Zona = zona;
    }
    public String getMercado() {
        return Mercado;
    }
    public void setMercado(String mercado) {
        Mercado = mercado;
    }
    public int getCanalRutaId() {
        return CanalRutaId;
    }
    public void setCanalRutaId(int canalRutaId) {
        CanalRutaId = canalRutaId;
    }
    public String getObservacion() {
        return Observacion;
    }
    public void setObservacion(String observacion) {
        Observacion = observacion;
    }
    public boolean isVerificadoFoto() {
        return VerificadoFoto;
    }
    public void setVerificadoFoto(boolean verificadoFoto) {
        VerificadoFoto = verificadoFoto;
    }
    public String getPedidoExternoId() {
        return PedidoExternoId;
    }
    public void setPedidoExternoId(String pedidoExternoId) {
        PedidoExternoId = pedidoExternoId;
    }
    public String getNombreVendedor() {
        return NombreVendedor;
    }
    public void setNombreVendedor(String nombreVendedor) {
        NombreVendedor = nombreVendedor;
    }
    public String getCelularVendedor() {
        return CelularVendedor;
    }
    public void setCelularVendedor(String celularVendedor) {
        CelularVendedor = celularVendedor;
    }
}
