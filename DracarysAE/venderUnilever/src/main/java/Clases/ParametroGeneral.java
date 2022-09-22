package Clases;

public class ParametroGeneral 
{
	private float _margenMinimo;
	private float _margenMinimoEmpleado;
	private boolean _descuentoPromocion;
	private String _nit;
	private String _nombreEmpresaFactura;
	private String _direccionReporte;
	private int _listaPrecioId;
	private int _tipoPagoId;
	private boolean _habilitarTiposPago;
	private boolean _facturarTodo;
	private boolean _sincronizarWifi;
	private String _tipoImpresionFactura;
	private boolean _mercadoRequerido;
	private float _montoCi;
	private float _montoBancarizacion;
	private boolean _habilitarPop;
	private boolean _habilitarVentaDirecta;
	private boolean _habilitarFechaEntrega;
	private float _montoNit;
	private boolean _habilitarCambio;
	private boolean _habilitarMatcheo;
	private boolean _editarPreventas;
	private boolean _habilitarMultiplesPreventas;
	private boolean _bloquearCondicionTributaria;
	private float _montoCondicionTributaria;
	private boolean _cambiarNit;
	private String _textoSinNombre;
	private boolean _creditoSobreCredito;
	private boolean _respetarTipoPago;
	private boolean _mostrarAlertaMora;
	private boolean _modificarTipoNegocio;
	private boolean _zonaRequerida;
	private boolean _clienteVisitaRequerida;
	private boolean _zonaMercadoRequerida;
	private boolean _edicionCliente;
	private boolean _activarGps;
	private float _distanciaCliente;
	private boolean _provinciaRequerida;
	private boolean _mostrarListaPrecio;
	private boolean _mostrarTipoPago;
	private boolean _mostrarSecuenciaVisita;
	private boolean _mostrarAdicionarNit;
	
	public ParametroGeneral(){}

	public ParametroGeneral(float _margenMinimo, float _margenMinimoEmpleado,
			boolean _descuentoPromocion,String _nit, String _nombreEmpresaFactura,
			String _direccionReporte, int _listaPrecioId, int _tipoPagoId,
			boolean _habilitarTiposPago,boolean _facturarTodo,boolean _sincronizarWifi,
			String _tipoImpresionFactura,boolean _mercadoRequerido,float _montoCi,
			float _montoBancarizacion,boolean _habilitarPop,boolean _habilitarVentaDirecta,
			boolean _habilitarFechaEntrega,float _montoNit,boolean _habilitarCambio,
			boolean _habilitarMatcheo,boolean _editarPreventas,boolean _habilitarMultiplesPreventas,
			boolean _bloquearCondicionTributaria,float _montoCondicionTributaria,boolean _cambiarNit,
			String _textoSinNombre,boolean _creditoSobreCredito,boolean _respetarTipoPago,
			boolean _mostrarAlertaMora,boolean _modificarTipoNegocio,boolean _zonaRequerida,
			boolean _clienteVisitaRequerida,boolean _zonaMercadoRequerida,boolean _edicionCliente,
			boolean _activarGps,float _distanciaCliente,boolean _provinciaRequerida,boolean _mostrarListaPrecio,
			boolean _mostrarTipoPago,boolean _mostrarSecuenciaVisita,boolean _mostrarAdicionarNit) 
	{
		super();
		this._margenMinimo = _margenMinimo;
		this._margenMinimoEmpleado = _margenMinimoEmpleado;
		this._descuentoPromocion = _descuentoPromocion;
		this._nit = _nit;
		this._nombreEmpresaFactura = _nombreEmpresaFactura;
		this._direccionReporte = _direccionReporte;
		this._listaPrecioId = _listaPrecioId;
		this._tipoPagoId = _tipoPagoId;
		this._habilitarTiposPago = _habilitarTiposPago;
		this._facturarTodo = _facturarTodo;
		this._sincronizarWifi = _sincronizarWifi;
		this._tipoImpresionFactura = _tipoImpresionFactura;
		this._mercadoRequerido = _mercadoRequerido;
		this._montoCi = _montoCi;
		this._montoBancarizacion = _montoBancarizacion;
		this._habilitarPop = _habilitarPop;
		this._habilitarVentaDirecta = _habilitarVentaDirecta;
		this._habilitarFechaEntrega = _habilitarFechaEntrega;
		this._montoNit = _montoNit;
		this._habilitarCambio = _habilitarCambio;
		this._habilitarMatcheo = _habilitarMatcheo;
		this._editarPreventas = _editarPreventas;
		this._habilitarMultiplesPreventas = _habilitarMultiplesPreventas;
		this._bloquearCondicionTributaria = _bloquearCondicionTributaria;
		this._montoCondicionTributaria = _montoCondicionTributaria;
		this._cambiarNit = _cambiarNit;
		this._textoSinNombre = _textoSinNombre;
		this._creditoSobreCredito = _creditoSobreCredito;
		this._respetarTipoPago = _respetarTipoPago;
		this._mostrarAlertaMora = _mostrarAlertaMora;
		this._modificarTipoNegocio = _modificarTipoNegocio;
		this._zonaRequerida = _zonaRequerida;
		this._clienteVisitaRequerida = _clienteVisitaRequerida;
		this._zonaMercadoRequerida = _zonaMercadoRequerida;
		this._edicionCliente = _edicionCliente;
		this._activarGps = _activarGps;
		this._distanciaCliente = _distanciaCliente;
		this._provinciaRequerida = _provinciaRequerida;
		this._mostrarListaPrecio = _mostrarListaPrecio;
		this._mostrarTipoPago = _mostrarTipoPago;
		this._mostrarSecuenciaVisita = _mostrarSecuenciaVisita;
		this._mostrarAdicionarNit = _mostrarAdicionarNit;
	}

	public float get_margenMinimo() {
		return _margenMinimo;
	}

	public void set_margenMinimo(float _margenMinimo) {
		this._margenMinimo = _margenMinimo;
	}
	
	public float get_margenMinimoEmpleado() {
		return _margenMinimoEmpleado;
	}

	public void set_margenMinimoEmpleado(float _margenMinimoEmpleado) {
		this._margenMinimoEmpleado = _margenMinimoEmpleado;
	}

	public boolean get_descuentoPromocion() {
		return _descuentoPromocion;
	}

	public void set_descuentoPromocion(boolean _descuentoPromocion) {
		this._descuentoPromocion = _descuentoPromocion;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public String get_nombreEmpresaFactura() {
		return _nombreEmpresaFactura;
	}

	public void set_nombreEmpresaFactura(String _nombreEmpresaFactura) {
		this._nombreEmpresaFactura = _nombreEmpresaFactura;
	}

	public String get_direccionReporte() {
		return _direccionReporte;
	}

	public void set_direccionReporte(String _direccionReporte) {
		this._direccionReporte = _direccionReporte;
	}

	public int get_listaPrecioId() {
		return _listaPrecioId;
	}

	public void set_listaPrecioId(int _listaPrecioId) {
		this._listaPrecioId = _listaPrecioId;
	}

	public int get_tipoPagoId() {
		return _tipoPagoId;
	}

	public void set_tipoPagoId(int _tipoPagoId) {
		this._tipoPagoId = _tipoPagoId;
	}

	public boolean is_habilitarTiposPago() {
		return _habilitarTiposPago;
	}

	public void set_habilitarTiposPago(boolean _habilitarTiposPago) {
		this._habilitarTiposPago = _habilitarTiposPago;
	}

	public boolean is_facturarTodo() {
		return _facturarTodo;
	}

	public void set_facturarTodo(boolean _facturarTodo) {
		this._facturarTodo = _facturarTodo;
	}

	public boolean is_sincronizarWifi() {
		return _sincronizarWifi;
	}

	public void set_sincronizarWifi(boolean _sincronizarWifi) {
		this._sincronizarWifi = _sincronizarWifi;
	}

	public String get_tipoImpresionFactura() {
		return _tipoImpresionFactura;
	}

	public void set_tipoImpresionFactura(String _tipoImpresionFactura) {
		this._tipoImpresionFactura = _tipoImpresionFactura;
	}

	public boolean is_mercadoRequerido() {
		return _mercadoRequerido;
	}

	public void set_mercadoRequerido(boolean _mercadoRequerido) {
		this._mercadoRequerido = _mercadoRequerido;
	}

	public float get_montoCi() {
		return _montoCi;
	}

	public void set_montoCi(float _montoCi) {
		this._montoCi = _montoCi;
	}

	public float get_montoBancarizacion() {
		return _montoBancarizacion;
	}

	public void set_montoBancarizacion(float _montoBancarizacion) {
		this._montoBancarizacion = _montoBancarizacion;
	}

	public boolean is_habilitarPop() {
		return _habilitarPop;
	}

	public void set_habilitarPop(boolean _habilitarPop) {
		this._habilitarPop = _habilitarPop;
	}

	public boolean is_habilitarVentaDirecta() {
		return _habilitarVentaDirecta;
	}

	public void set_habilitarVentaDirecta(boolean _habilitarVentaDirecta) {
		this._habilitarVentaDirecta = _habilitarVentaDirecta;
	}

	public boolean is_habilitarFechaEntrega() {
		return _habilitarFechaEntrega;
	}

	public void set_habilitarFechaEntrega(boolean _habilitarFechaEntrega) {
		this._habilitarFechaEntrega = _habilitarFechaEntrega;
	}

	public float get_montoNit() {
		return _montoNit;
	}

	public void set_montoNit(float _montoNit) {
		this._montoNit = _montoNit;
	}

	public boolean is_habilitarCambio() {
		return _habilitarCambio;
	}

	public void set_habilitarCambio(boolean _habilitarCambio) {
		this._habilitarCambio = _habilitarCambio;
	}

	public boolean is_habilitarMatcheo() {
		return _habilitarMatcheo;
	}

	public void set_habilitarMatcheo(boolean _habilitarMatcheo) {
		this._habilitarMatcheo = _habilitarMatcheo;
	}

	public boolean is_editarPreventas() {
		return _editarPreventas;
	}

	public void set_editarPreventas(boolean _editarPreventas) {
		this._editarPreventas = _editarPreventas;
	}

	public boolean is_habilitarMultiplesPreventas() {
		return _habilitarMultiplesPreventas;
	}

	public void set_habilitarMultiplesPreventas(boolean _habilitarMultiplesPreventas) {
		this._habilitarMultiplesPreventas = _habilitarMultiplesPreventas;
	}

	public boolean is_bloquearCondicionTributaria() {
		return _bloquearCondicionTributaria;
	}

	public void set_bloquearCondicionTributaria(boolean _bloquearCondicionTributaria) {
		this._bloquearCondicionTributaria = _bloquearCondicionTributaria;
	}

	public float get_montoCondicionTributaria() {
		return _montoCondicionTributaria;
	}

	public void set_montoCondicionTributaria(float _montoCondicionTributaria) {
		this._montoCondicionTributaria = _montoCondicionTributaria;
	}

	public boolean is_cambiarNit() {
		return _cambiarNit;
	}

	public void set_cambiarNit(boolean _cambiarNit) {
		this._cambiarNit = _cambiarNit;
	}

	public String get_textoSinNombre() {
		return _textoSinNombre;
	}

	public void set_textoSinNombre(String _textoSinNombre) {
		this._textoSinNombre = _textoSinNombre;
	}

	public boolean is_creditoSobreCredito() {
		return _creditoSobreCredito;
	}

	public void set_creditoSobreCredito(boolean _creditoSobreCredito) {
		this._creditoSobreCredito = _creditoSobreCredito;
	}

	public boolean is_respetarTipoPago() {
		return _respetarTipoPago;
	}

	public void set_respetarTipoPago(boolean _respetarTipoPago) {
		this._respetarTipoPago = _respetarTipoPago;
	}

	public boolean is_mostrarAlertaMora() {
		return _mostrarAlertaMora;
	}

	public void set_mostrarAlertaMora(boolean _mostrarAlertaMora) {
		this._mostrarAlertaMora = _mostrarAlertaMora;
	}

	public boolean is_modificarTipoNegocio() {
		return _modificarTipoNegocio;
	}

	public void set_modificarTipoNegocio(boolean _modificarTipoNegocio) {
		this._modificarTipoNegocio = _modificarTipoNegocio;
	}

	public boolean is_zonaRequerida() {
		return _zonaRequerida;
	}

	public void set_zonaRequerida(boolean _zonaRequerida) {
		this._zonaRequerida = _zonaRequerida;
	}

	public boolean is_clienteVisitaRequerida() {
		return _clienteVisitaRequerida;
	}

	public void set_clienteVisitaRequerida(boolean _clienteVisitaRequerida) {
		this._clienteVisitaRequerida = _clienteVisitaRequerida;
	}

	public boolean is_zonaMercadoRequerida() {
		return _zonaMercadoRequerida;
	}

	public void set_zonaMercadoRequerida(boolean _zonaMercadoRequerida) {
		this._zonaMercadoRequerida = _zonaMercadoRequerida;
	}

	public boolean is_edicionCliente() {
		return _edicionCliente;
	}

	public void set_edicionCliente(boolean _edicionCliente) {
		this._edicionCliente = _edicionCliente;
	}

	public boolean is_activarGps() {
		return _activarGps;
	}

	public void set_activarGps(boolean _activarGps) {
		this._activarGps = _activarGps;
	}

	public float get_distanciaCliente() {
		return _distanciaCliente;
	}

	public void set_distanciaCliente(float _distanciaCliente) {
		this._distanciaCliente = _distanciaCliente;
	}

	public boolean is_provinciaRequerida() {
		return _provinciaRequerida;
	}

	public void set_provinciaRequerida(boolean _provinciaRequerida) {
		this._provinciaRequerida = _provinciaRequerida;
	}

	public boolean is_mostrarListaPrecio() {
		return _mostrarListaPrecio;
	}

	public void set_mostrarListaPrecio(boolean _mostrarListaPrecio) {
		this._mostrarListaPrecio = _mostrarListaPrecio;
	}

	public boolean is_mostrarTipoPago() {
		return _mostrarTipoPago;
	}

	public void set_mostrarTipoPago(boolean _mostrarTipoPago) {
		this._mostrarTipoPago = _mostrarTipoPago;
	}

	public boolean is_mostrarSecuenciaVisita() {
		return _mostrarSecuenciaVisita;
	}

	public void set_mostrarSecuenciaVisita(boolean _mostrarSecuenciaVisita) {
		this._mostrarSecuenciaVisita = _mostrarSecuenciaVisita;
	}

	public boolean is_mostrarAdicionarNit() {
		return _mostrarAdicionarNit;
	}

	public void set_mostrarAdicionarNit(boolean _mostrarAdicionarNit) {
		this._mostrarAdicionarNit = _mostrarAdicionarNit;
	}
}
