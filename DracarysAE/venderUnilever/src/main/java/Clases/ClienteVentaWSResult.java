package Clases;

public class ClienteVentaWSResult
{
    private int Id;
    private int ClienteId;
    private String Codigo;
    private int TipoNegocioId;
    private String NombreCompleto;
    private double Latitud;
    private double Longitud;
    private String Direccion;
    private String Telefono;
    private String Celular;
    private int PrecioListaId;
    private float Descuento;
    private int TipoPagoId;
    private int DiasPago;
    private float TopeCredito;
    private int RutaId;
    private String RutaDescripcion;
    private String RazonSocial;
    private boolean Autoventa;
    private boolean EstadoAtendido;
    private boolean ClientePunteoSincronizado;
    private boolean EstadoSincronizacion;
    private String NombreFactura;
    private String Nit;
    private int DiaId;
    private float MontoPendienteCredito;
    private int ProvinciaId;
    private int CanalRutaId;
    private String Observacion;
    private String PedidoExternoId;

    public ClienteVentaWSResult(int id, int clienteId, String codigo, int tipoNegocioId, String nombreCompleto,
                                double latitud, double longitud, String direccion, String telefono, String celular,
                                int precioListaId, float descuento, int tipoPagoId, int diasPago, float topeCredito,
                                int rutaId, String rutaDescripcion, String razonSocial, boolean autoventa,
                                boolean estadoAtendido, boolean clientePunteoSincronizado, boolean estadoSincronizacion,
                                String nombreFactura, String nit, int diaId, float montoPendienteCredito, int provinciaId,
                                int canalRutaId, String observacion, String pedidoExternoId) {
        Id = id;
        ClienteId = clienteId;
        Codigo = codigo;
        TipoNegocioId = tipoNegocioId;
        NombreCompleto = nombreCompleto;
        Latitud = latitud;
        Longitud = longitud;
        Direccion = direccion;
        Telefono = telefono;
        Celular = celular;
        PrecioListaId = precioListaId;
        Descuento = descuento;
        TipoPagoId = tipoPagoId;
        DiasPago = diasPago;
        TopeCredito = topeCredito;
        RutaId = rutaId;
        RutaDescripcion = rutaDescripcion;
        RazonSocial = razonSocial;
        Autoventa = autoventa;
        EstadoAtendido = estadoAtendido;
        ClientePunteoSincronizado = clientePunteoSincronizado;
        EstadoSincronizacion = estadoSincronizacion;
        NombreFactura = nombreFactura;
        Nit = nit;
        DiaId = diaId;
        MontoPendienteCredito = montoPendienteCredito;
        ProvinciaId = provinciaId;
        CanalRutaId = canalRutaId;
        Observacion = observacion;
        PedidoExternoId = pedidoExternoId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getNombreCompleto() {
        return NombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        NombreCompleto = nombreCompleto;
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

    public String getCelular() {
        return Celular;
    }

    public void setCelular(String celular) {
        Celular = celular;
    }

    public int getPrecioListaId() {
        return PrecioListaId;
    }

    public void setPrecioListaId(int precioListaId) {
        PrecioListaId = precioListaId;
    }

    public float getDescuento() {
        return Descuento;
    }

    public void setDescuento(float descuento) {
        Descuento = descuento;
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

    public String getRutaDescripcion() {
        return RutaDescripcion;
    }

    public void setRutaDescripcion(String rutaDescripcion) {
        RutaDescripcion = rutaDescripcion;
    }

    public String getRazonSocial() {
        return RazonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        RazonSocial = razonSocial;
    }

    public boolean isAutoventa() {
        return Autoventa;
    }

    public void setAutoventa(boolean autoventa) {
        Autoventa = autoventa;
    }

    public boolean isEstadoAtendido() {
        return EstadoAtendido;
    }

    public void setEstadoAtendido(boolean estadoAtendido) {
        EstadoAtendido = estadoAtendido;
    }

    public boolean isClientePunteoSincronizado() {
        return ClientePunteoSincronizado;
    }

    public void setClientePunteoSincronizado(boolean clientePunteoSincronizado) {
        ClientePunteoSincronizado = clientePunteoSincronizado;
    }

    public boolean isEstadoSincronizacion() {
        return EstadoSincronizacion;
    }

    public void setEstadoSincronizacion(boolean estadoSincronizacion) {
        EstadoSincronizacion = estadoSincronizacion;
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

    public int getDiaId() {
        return DiaId;
    }

    public void setDiaId(int diaId) {
        DiaId = diaId;
    }

    public float getMontoPendienteCredito() {
        return MontoPendienteCredito;
    }

    public void setMontoPendienteCredito(float montoPendienteCredito) {
        MontoPendienteCredito = montoPendienteCredito;
    }

    public int getProvinciaId() {
        return ProvinciaId;
    }

    public void setProvinciaId(int provinciaId) {
        ProvinciaId = provinciaId;
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

    public String getPedidoExternoId() {
        return PedidoExternoId;
    }

    public void setPedidoExternoId(String pedidoExternoId) {
        PedidoExternoId = pedidoExternoId;
    }
}
