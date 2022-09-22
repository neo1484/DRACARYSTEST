package Clases;

public class ParametroGeneralWSResult 
{
    private float MargenMinimo;
    private float MargenMinimoEmpleado;
    private boolean DescuentoPromocion;
    private String Nit;
    private String NombreEmpresaFactura;
    private String DireccionReporte;
    private int ListaPrecioId;
    private int TipoPagoId;
    private boolean HabilitarTiposPago;
    private boolean FacturarTodo;
    private boolean SincronizarWifi;
    private String TipoImpresionFactura;
    private boolean MercadoRequerido;
    private float MontoCi;
    private float MontoBancarizacion;
    private boolean HabilitarPop;
    private boolean HabilitarVentaDirecta;
    private boolean HabilitarFechaEntrega;
    private float MontoNit;
    private boolean HabilitarCambio;
    private boolean HabilitarMatcheo;
    private boolean EditarPreventas;
    private boolean HabilitarMultiplePreventa;
    private boolean BloquearCondicionTributaria;
    private float MontoCondicionTributaria;
    private boolean CambiarNit;
    private String TextoSinNombre;
    private boolean CreditoSobreCredito;
    private boolean RespetarTipoPago;
    private boolean MostrarAlertaMora;
    private boolean ModificarTipoNegocio;
    private boolean ZonaRequerida;
    private boolean ClienteVisita;
    private boolean ZonaMercadoRequerida;
    private boolean HabilitarModificacionCliente;
    private boolean ActivarGps;
    private float DistanciaCliente;
    private boolean ProvinciaRequerida;
    private boolean MostrarListaPrecio;
    private boolean MostrarTipoPago;
    private boolean MostrarSecuenciaVisita;
    private boolean MostrarAdicionarNit;

    public ParametroGeneralWSResult(float margenMinimo, float margenMinimoEmpleado, boolean descuentoPromocion, String nit, String nombreEmpresaFactura,
                                    String direccionReporte, int listaPrecioId, int tipoPagoId, boolean habilitarTiposPago, boolean facturarTodo,
                                    boolean sincronizarWifi, String tipoImpresionFactura, boolean mercadoRequerido, float montoCi, float montoBancarizacion,
                                    boolean habilitarPop, boolean habilitarVentaDirecta, boolean habilitarFechaEntrega, float montoNit, boolean habilitarCambio,
                                    boolean habilitarMatcheo, boolean editarPreventas, boolean habilitarMultiplePreventa, boolean bloquearCondicionTributaria,
                                    float montoCondicionTributaria, boolean cambiarNit, String textoSinNombre, boolean creditoSobreCredito, boolean respetarTipoPago,
                                    boolean mostrarAlertaMora, boolean modificarTipoNegocio, boolean zonaRequerida, boolean clienteVisita, boolean zonaMercadoRequerida,
                                    boolean habilitarModificacionCliente, boolean activarGps, float distanciaCliente, boolean provinciaRequerida, boolean mostrarListaPrecio,
                                    boolean mostrarTipoPago, boolean mostrarSecuenciaVisita, boolean mostrarAdicionarNit) {
        MargenMinimo = margenMinimo;
        MargenMinimoEmpleado = margenMinimoEmpleado;
        DescuentoPromocion = descuentoPromocion;
        Nit = nit;
        NombreEmpresaFactura = nombreEmpresaFactura;
        DireccionReporte = direccionReporte;
        ListaPrecioId = listaPrecioId;
        TipoPagoId = tipoPagoId;
        HabilitarTiposPago = habilitarTiposPago;
        FacturarTodo = facturarTodo;
        SincronizarWifi = sincronizarWifi;
        TipoImpresionFactura = tipoImpresionFactura;
        MercadoRequerido = mercadoRequerido;
        MontoCi = montoCi;
        MontoBancarizacion = montoBancarizacion;
        HabilitarPop = habilitarPop;
        HabilitarVentaDirecta = habilitarVentaDirecta;
        HabilitarFechaEntrega = habilitarFechaEntrega;
        MontoNit = montoNit;
        HabilitarCambio = habilitarCambio;
        HabilitarMatcheo = habilitarMatcheo;
        EditarPreventas = editarPreventas;
        HabilitarMultiplePreventa = habilitarMultiplePreventa;
        BloquearCondicionTributaria = bloquearCondicionTributaria;
        MontoCondicionTributaria = montoCondicionTributaria;
        CambiarNit = cambiarNit;
        TextoSinNombre = textoSinNombre;
        CreditoSobreCredito = creditoSobreCredito;
        RespetarTipoPago = respetarTipoPago;
        MostrarAlertaMora = mostrarAlertaMora;
        ModificarTipoNegocio = modificarTipoNegocio;
        ZonaRequerida = zonaRequerida;
        ClienteVisita = clienteVisita;
        ZonaMercadoRequerida = zonaMercadoRequerida;
        HabilitarModificacionCliente = habilitarModificacionCliente;
        ActivarGps = activarGps;
        DistanciaCliente = distanciaCliente;
        ProvinciaRequerida = provinciaRequerida;
        MostrarListaPrecio = mostrarListaPrecio;
        MostrarTipoPago = mostrarTipoPago;
        MostrarSecuenciaVisita = mostrarSecuenciaVisita;
        MostrarAdicionarNit = mostrarAdicionarNit;
    }

    public float getMargenMinimo() {
        return MargenMinimo;
    }

    public void setMargenMinimo(float margenMinimo) {
        MargenMinimo = margenMinimo;
    }

    public float getMargenMinimoEmpleado() {
        return MargenMinimoEmpleado;
    }

    public void setMargenMinimoEmpleado(float margenMinimoEmpleado) {
        MargenMinimoEmpleado = margenMinimoEmpleado;
    }

    public boolean isDescuentoPromocion() {
        return DescuentoPromocion;
    }

    public void setDescuentoPromocion(boolean descuentoPromocion) {
        DescuentoPromocion = descuentoPromocion;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String nit) {
        Nit = nit;
    }

    public String getNombreEmpresaFactura() {
        return NombreEmpresaFactura;
    }

    public void setNombreEmpresaFactura(String nombreEmpresaFactura) {
        NombreEmpresaFactura = nombreEmpresaFactura;
    }

    public String getDireccionReporte() {
        return DireccionReporte;
    }

    public void setDireccionReporte(String direccionReporte) {
        DireccionReporte = direccionReporte;
    }

    public int getListaPrecioId() {
        return ListaPrecioId;
    }

    public void setListaPrecioId(int listaPrecioId) {
        ListaPrecioId = listaPrecioId;
    }

    public int getTipoPagoId() {
        return TipoPagoId;
    }

    public void setTipoPagoId(int tipoPagoId) {
        TipoPagoId = tipoPagoId;
    }

    public boolean isHabilitarTiposPago() {
        return HabilitarTiposPago;
    }

    public void setHabilitarTiposPago(boolean habilitarTiposPago) {
        HabilitarTiposPago = habilitarTiposPago;
    }

    public boolean isFacturarTodo() {
        return FacturarTodo;
    }

    public void setFacturarTodo(boolean facturarTodo) {
        FacturarTodo = facturarTodo;
    }

    public boolean isSincronizarWifi() {
        return SincronizarWifi;
    }

    public void setSincronizarWifi(boolean sincronizarWifi) {
        SincronizarWifi = sincronizarWifi;
    }

    public String getTipoImpresionFactura() {
        return TipoImpresionFactura;
    }

    public void setTipoImpresionFactura(String tipoImpresionFactura) {
        TipoImpresionFactura = tipoImpresionFactura;
    }

    public boolean isMercadoRequerido() {
        return MercadoRequerido;
    }

    public void setMercadoRequerido(boolean mercadoRequerido) {
        MercadoRequerido = mercadoRequerido;
    }

    public float getMontoCi() {
        return MontoCi;
    }

    public void setMontoCi(float montoCi) {
        MontoCi = montoCi;
    }

    public float getMontoBancarizacion() {
        return MontoBancarizacion;
    }

    public void setMontoBancarizacion(float montoBancarizacion) {
        MontoBancarizacion = montoBancarizacion;
    }

    public boolean isHabilitarPop() {
        return HabilitarPop;
    }

    public void setHabilitarPop(boolean habilitarPop) {
        HabilitarPop = habilitarPop;
    }

    public boolean isHabilitarVentaDirecta() {
        return HabilitarVentaDirecta;
    }

    public void setHabilitarVentaDirecta(boolean habilitarVentaDirecta) {
        HabilitarVentaDirecta = habilitarVentaDirecta;
    }

    public boolean isHabilitarFechaEntrega() {
        return HabilitarFechaEntrega;
    }

    public void setHabilitarFechaEntrega(boolean habilitarFechaEntrega) {
        HabilitarFechaEntrega = habilitarFechaEntrega;
    }

    public float getMontoNit() {
        return MontoNit;
    }

    public void setMontoNit(float montoNit) {
        MontoNit = montoNit;
    }

    public boolean isHabilitarCambio() {
        return HabilitarCambio;
    }

    public void setHabilitarCambio(boolean habilitarCambio) {
        HabilitarCambio = habilitarCambio;
    }

    public boolean isHabilitarMatcheo() {
        return HabilitarMatcheo;
    }

    public void setHabilitarMatcheo(boolean habilitarMatcheo) {
        HabilitarMatcheo = habilitarMatcheo;
    }

    public boolean isEditarPreventas() {
        return EditarPreventas;
    }

    public void setEditarPreventas(boolean editarPreventas) {
        EditarPreventas = editarPreventas;
    }

    public boolean isHabilitarMultiplePreventa() {
        return HabilitarMultiplePreventa;
    }

    public void setHabilitarMultiplePreventa(boolean habilitarMultiplePreventa) {
        HabilitarMultiplePreventa = habilitarMultiplePreventa;
    }

    public boolean isBloquearCondicionTributaria() {
        return BloquearCondicionTributaria;
    }

    public void setBloquearCondicionTributaria(boolean bloquearCondicionTributaria) {
        BloquearCondicionTributaria = bloquearCondicionTributaria;
    }

    public float getMontoCondicionTributaria() {
        return MontoCondicionTributaria;
    }

    public void setMontoCondicionTributaria(float montoCondicionTributaria) {
        MontoCondicionTributaria = montoCondicionTributaria;
    }

    public boolean isCambiarNit() {
        return CambiarNit;
    }

    public void setCambiarNit(boolean cambiarNit) {
        CambiarNit = cambiarNit;
    }

    public String getTextoSinNombre() {
        return TextoSinNombre;
    }

    public void setTextoSinNombre(String textoSinNombre) {
        TextoSinNombre = textoSinNombre;
    }

    public boolean isCreditoSobreCredito() {
        return CreditoSobreCredito;
    }

    public void setCreditoSobreCredito(boolean creditoSobreCredito) {
        CreditoSobreCredito = creditoSobreCredito;
    }

    public boolean isRespetarTipoPago() {
        return RespetarTipoPago;
    }

    public void setRespetarTipoPago(boolean respetarTipoPago) {
        RespetarTipoPago = respetarTipoPago;
    }

    public boolean isMostrarAlertaMora() {
        return MostrarAlertaMora;
    }

    public void setMostrarAlertaMora(boolean mostrarAlertaMora) {
        MostrarAlertaMora = mostrarAlertaMora;
    }

    public boolean isModificarTipoNegocio() {
        return ModificarTipoNegocio;
    }

    public void setModificarTipoNegocio(boolean modificarTipoNegocio) {
        ModificarTipoNegocio = modificarTipoNegocio;
    }

    public boolean isZonaRequerida() {
        return ZonaRequerida;
    }

    public void setZonaRequerida(boolean zonaRequerida) {
        ZonaRequerida = zonaRequerida;
    }

    public boolean isClienteVisita() {
        return ClienteVisita;
    }

    public void setClienteVisita(boolean clienteVisita) {
        ClienteVisita = clienteVisita;
    }

    public boolean isZonaMercadoRequerida() {
        return ZonaMercadoRequerida;
    }

    public void setZonaMercadoRequerida(boolean zonaMercadoRequerida) {
        ZonaMercadoRequerida = zonaMercadoRequerida;
    }

    public boolean isHabilitarModificacionCliente() {
        return HabilitarModificacionCliente;
    }

    public void setHabilitarModificacionCliente(boolean habilitarModificacionCliente) {
        HabilitarModificacionCliente = habilitarModificacionCliente;
    }

    public boolean isActivarGps() {
        return ActivarGps;
    }

    public void setActivarGps(boolean activarGps) {
        ActivarGps = activarGps;
    }

    public float getDistanciaCliente() {
        return DistanciaCliente;
    }

    public void setDistanciaCliente(float distanciaCliente) {
        DistanciaCliente = distanciaCliente;
    }

    public boolean isProvinciaRequerida() {
        return ProvinciaRequerida;
    }

    public void setProvinciaRequerida(boolean provinciaRequerida) {
        ProvinciaRequerida = provinciaRequerida;
    }

    public boolean isMostrarListaPrecio() {
        return MostrarListaPrecio;
    }

    public void setMostrarListaPrecio(boolean mostrarListaPrecio) {
        MostrarListaPrecio = mostrarListaPrecio;
    }

    public boolean isMostrarTipoPago() {
        return MostrarTipoPago;
    }

    public void setMostrarTipoPago(boolean mostrarTipoPago) {
        MostrarTipoPago = mostrarTipoPago;
    }

    public boolean isMostrarSecuenciaVisita() {
        return MostrarSecuenciaVisita;
    }

    public void setMostrarSecuenciaVisita(boolean mostrarSecuenciaVisita) {
        MostrarSecuenciaVisita = mostrarSecuenciaVisita;
    }

    public boolean isMostrarAdicionarNit() {
        return MostrarAdicionarNit;
    }

    public void setMostrarAdicionarNit(boolean mostrarAdicionarNit) {
        MostrarAdicionarNit = mostrarAdicionarNit;
    }
}
