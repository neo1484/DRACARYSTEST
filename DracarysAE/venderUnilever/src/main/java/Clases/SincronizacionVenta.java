package Clases;

public class SincronizacionVenta 
{
	private int _id;
	private int _ventaProductoTempRowId;
	private int _preventaId;
	private int _clienteId;
	private int _distribuidorId;
	private int _productoId;
	private int _promocionId;
	private int _cantidad;
	private int _cantidadPaquete;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _tipoPagoId;
	private int _cantidadNueva;
	private int _cantidadPaqueteNueva;
	private float _montoNuevo;
	private float _descuentoNuevo;
	private float _montoFinalNuevo;
	private int _numeroDeItems;
	private int _motivoId;
	private int _dia;
	private int _mes;
	private int _anio;
	private double _latitud;
	private double _longitud;
	private boolean _confirmado;
	private int _tipoSincronizacion;
	private boolean _estadoSincronizacion;
	private int _hora;
	private int _minuto;
	private boolean _autoventa;
	private String _nombreFactura;
	private String _nit;
	private boolean _nitNuevo;
	private float _costo;
	private int _ventaIdServer;
	private int _ventaId;
	private int _costoId;
	private String _observacion;
	private int _precioId;
	private boolean _aplicarBonificacion;
	private int _noAutoventa;
	private int _dosificacionId;
	private String _tipoNit;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private int _canalPrecioRutaId;
	private float _descuentoProntoPago;
	private int _prontoPagoId;
	private float _descuentoObjetivo;
	private int _formaPagoId;
	private String _codTransferencia;
	private boolean _fromShopping;
	
	public SincronizacionVenta(int _id,int _ventaProductoTempRowId,int _preventaId,
			int _clienteId,int _distribuidorId, int _productoId, int _promocionId,
			int _cantidad,int _cantidadPaquete,float _monto, float _descuento, 
			float _montoFinal, int _tipoPagoId,int _cantidadNueva, 
			int _cantidadPaqueteNueva, float _montoNuevo,float _descuentoNuevo, 
			float _montoFinalNuevo, int _numeroDeItems,int _motivoId, int _dia, 
			int _mes, int _anio, double _latitud,double _longitud, 
			boolean _confirmado,int _tipoSincronizacion,boolean _estadoSincronizacion,
			int _hora,int _minuto,boolean _autoventa,String _nombreFactura,String _nit,
			boolean _nitNuevo,float _costo,int _ventaIdServer,int _ventaId,int _costoId,
			String _observacion,int _precioId,boolean _aplicarBonificacion,int _noAutoventa,
			int _dosificacionId,String _tipoNit, float _descuentoCanal, float _descuentoAjuste,
			int _canalPrecioRutaId, float _descuentoProntoPago, int _prontoPagoId,
			float _descuentoObjetivo, int _formaPagoId, String _codTransferencia,
		   	boolean _fromShopping)
	{
		super();
		this._id = _id;
		this._ventaProductoTempRowId = _ventaProductoTempRowId;
		this._preventaId = _preventaId;
		this._clienteId = _clienteId;
		this._distribuidorId = _distribuidorId;
		this._productoId = _productoId;
		this._promocionId = _promocionId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._tipoPagoId = _tipoPagoId;
		this._cantidadNueva = _cantidadNueva;
		this._cantidadPaqueteNueva = _cantidadPaqueteNueva;
		this._montoNuevo = _montoNuevo;
		this._descuentoNuevo = _descuentoNuevo;
		this._montoFinalNuevo = _montoFinalNuevo;
		this._numeroDeItems = _numeroDeItems;
		this._motivoId = _motivoId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._confirmado = _confirmado;
		this._tipoSincronizacion = _tipoSincronizacion;
		this._estadoSincronizacion = _estadoSincronizacion;
		this._hora = _hora;
		this._minuto = _minuto;
		this._autoventa = _autoventa;
		this._nombreFactura = _nombreFactura;
		this._nit = _nit;
		this._nitNuevo = _nitNuevo;
		this._costo = _costo;
		this._ventaIdServer = _ventaIdServer;
		this._ventaId = _ventaId;
		this._costoId = _costoId;
		this._observacion = _observacion;
		this._precioId = _precioId;
		this._aplicarBonificacion = _aplicarBonificacion;
		this._noAutoventa = _noAutoventa;
		this._dosificacionId = _dosificacionId;
		this._tipoNit = _tipoNit;
		this._descuentoCanal = _descuentoCanal;
		this._descuentoAjuste = _descuentoAjuste;
		this._canalPrecioRutaId = _canalPrecioRutaId;
		this._descuentoProntoPago = _descuentoProntoPago;
		this._prontoPagoId = _prontoPagoId;
		this._descuentoObjetivo = _descuentoObjetivo;
		this._formaPagoId = _formaPagoId;
		this._codTransferencia = _codTransferencia;
		this._fromShopping = _fromShopping;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_ventaProductoTempRowId() {
		return _ventaProductoTempRowId;
	}

	public void set_ventaProductoTempRowId(int _ventaProductoTempRowId) {
		this._ventaProductoTempRowId = _ventaProductoTempRowId;
	}

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_distribuidorId() {
		return _distribuidorId;
	}

	public void set_distribuidorId(int _distribuidorId) {
		this._distribuidorId = _distribuidorId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public int get_cantidad() {
		return _cantidad;
	}

	public void set_cantidad(int _cantidad) {
		this._cantidad = _cantidad;
	}

	public int get_cantidadPaquete() {
		return _cantidadPaquete;
	}

	public void set_cantidadPaquete(int _cantidadPaquete) {
		this._cantidadPaquete = _cantidadPaquete;
	}

	public float get_monto() {
		return _monto;
	}

	public void set_monto(float _monto) {
		this._monto = _monto;
	}

	public float get_descuento() {
		return _descuento;
	}

	public void set_descuento(float _descuento) {
		this._descuento = _descuento;
	}

	public float get_montoFinal() {
		return _montoFinal;
	}

	public void set_montoFinal(float _montoFinal) {
		this._montoFinal = _montoFinal;
	}

	public int get_tipoPagoId() {
		return _tipoPagoId;
	}

	public void set_tipoPagoId(int _tipoPagoId) {
		this._tipoPagoId = _tipoPagoId;
	}

	public int get_cantidadNueva() {
		return _cantidadNueva;
	}

	public void set_cantidadNueva(int _cantidadNueva) {
		this._cantidadNueva = _cantidadNueva;
	}

	public int get_cantidadPaqueteNueva() {
		return _cantidadPaqueteNueva;
	}

	public void set_cantidadPaqueteNueva(int _cantidadPaqueteNueva) {
		this._cantidadPaqueteNueva = _cantidadPaqueteNueva;
	}

	public float get_montoNuevo() {
		return _montoNuevo;
	}

	public void set_montoNuevo(float _montoNuevo) {
		this._montoNuevo = _montoNuevo;
	}

	public float get_descuentoNuevo() {
		return _descuentoNuevo;
	}

	public void set_descuentoNuevo(float _descuentoNuevo) {
		this._descuentoNuevo = _descuentoNuevo;
	}

	public float get_montoFinalNuevo() {
		return _montoFinalNuevo;
	}

	public void set_montoFinalNuevo(float _montoFinalNuevo) {
		this._montoFinalNuevo = _montoFinalNuevo;
	}

	public int get_numeroDeItems() {
		return _numeroDeItems;
	}

	public void set_numeroDeItems(int _numeroDeItems) {
		this._numeroDeItems = _numeroDeItems;
	}

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
	}

	public int get_dia() {
		return _dia;
	}

	public void set_dia(int _dia) {
		this._dia = _dia;
	}

	public int get_mes() {
		return _mes;
	}

	public void set_mes(int _mes) {
		this._mes = _mes;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
	}

	public double get_latitud() {
		return _latitud;
	}

	public void set_latitud(double _latitud) {
		this._latitud = _latitud;
	}

	public double get_longitud() {
		return _longitud;
	}

	public void set_longitud(double _longitud) {
		this._longitud = _longitud;
	}

	public boolean is_confirmado() {
		return _confirmado;
	}

	public void set_confirmado(boolean _confirmado) {
		this._confirmado = _confirmado;
	}

	public int get_tipoSincronizacion() {
		return _tipoSincronizacion;
	}

	public void set_tipoSincronizacion(int _tipoSincronizacion) {
		this._tipoSincronizacion = _tipoSincronizacion;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public int get_hora() {
		return _hora;
	}

	public void set_hora(int _hora) {
		this._hora = _hora;
	}

	public int get_minuto() {
		return _minuto;
	}

	public void set_minuto(int _minuto) {
		this._minuto = _minuto;
	}

	public boolean is_autoventa() {
		return _autoventa;
	}

	public void set_autoventa(boolean _autoventa) {
		this._autoventa = _autoventa;
	}

	public String get_nombreFactura() {
		return _nombreFactura;
	}

	public void set_nombreFactura(String _nombreFactura) {
		this._nombreFactura = _nombreFactura;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public boolean is_nitNuevo() {
		return _nitNuevo;
	}

	public void set_nitNuevo(boolean _nitNuevo) {
		this._nitNuevo = _nitNuevo;
	}

	public float get_costo() {
		return _costo;
	}

	public void set_costo(float _costo) {
		this._costo = _costo;
	}

	public int get_ventaIdServer() {
		return _ventaIdServer;
	}

	public void set_ventaIdServer(int _ventaIdServer) {
		this._ventaIdServer = _ventaIdServer;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}

	public String get_observacion() {
		return _observacion;
	}

	public void set_observacion(String _observacion) {
		this._observacion = _observacion;
	}

	public int get_precioId() {
		return _precioId;
	}

	public void set_precioId(int _precioId) {
		this._precioId = _precioId;
	}

	public boolean is_aplicarBonificacion() {
		return _aplicarBonificacion;
	}

	public void set_aplicarBonificacion(boolean _aplicarBonificacion) {
		this._aplicarBonificacion = _aplicarBonificacion;
	}

	public int get_noAutoventa() {
		return _noAutoventa;
	}

	public void set_noAutoventa(int _noAutoventa) {
		this._noAutoventa = _noAutoventa;
	}

	public int get_dosificacionId() {
		return _dosificacionId;
	}

	public void set_dosificacionId(int _dosificacionId) {
		this._dosificacionId = _dosificacionId;
	}

	public String get_tipoNit() {
		return _tipoNit;
	}

	public void set_tipoNit(String _tipoNit) {
		this._tipoNit = _tipoNit;
	}

	public float get_descuentoCanal() {
		return _descuentoCanal;
	}

	public void set_descuentoCanal(float _descuentoCanal) {
		this._descuentoCanal = _descuentoCanal;
	}

	public float get_descuentoAjuste() {
		return _descuentoAjuste;
	}

	public void set_descuentoAjuste(float _descuentoAjuste) {
		this._descuentoAjuste = _descuentoAjuste;
	}

	public int get_canalPrecioRutaId() {
		return _canalPrecioRutaId;
	}

	public void set_canalPrecioRutaId(int _canalPrecioRutaId) {
		this._canalPrecioRutaId = _canalPrecioRutaId;
	}

	public float get_descuentoProntoPago() {
		return _descuentoProntoPago;
	}

	public void set_descuentoProntoPago(float _descuentoProntoPago) {
		this._descuentoProntoPago = _descuentoProntoPago;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public float get_descuentoObjetivo() {
		return _descuentoObjetivo;
	}

	public void set_descuentoObjetivo(float _descuentoObjetivo) {
		this._descuentoObjetivo = _descuentoObjetivo;
	}

	public int get_formaPagoId() {
		return _formaPagoId;
	}

	public void set_formaPagoId(int _formaPagoId) {
		this._formaPagoId = _formaPagoId;
	}

	public String get_codTransferencia() {
		return _codTransferencia;
	}

	public void set_codTransferencia(String _codTransferencia) {
		this._codTransferencia = _codTransferencia;
	}

	public boolean is_fromShopping() {
		return _fromShopping;
	}

	public void set_fromShopping(boolean _fromShopping) {
		this._fromShopping = _fromShopping;
	}
}
