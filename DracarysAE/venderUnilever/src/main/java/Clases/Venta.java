package Clases;

public class Venta 
{
	private int _id;
	private int _ventaIdServer;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _clienteId;
	private int _distribuidorId;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _preventaId;
	private int _tipoPagoId;
	private double _latitud;
	private double _longitud;
	private boolean _diferencia;
	private boolean _estadoSincronizacion;
	private int _hora;
	private int _minuto;
	private String _observacion;
	private boolean _aplicarBonificacion;
	private int _dosificacionId;
	private String _tipoNit;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private int _prontoPagoId;
	private float _descuentoProntoPago;
	private float _descuentoObjetivo;
	private int _formaPagoId;
	private String _codTransferencia;
	private boolean _fromApk;
	private boolean _fromShopp;

	public Venta(){}

	public Venta(int _id, int _ventaIdServer, int _dia, int _mes_, int _anio,
			int _clienteId, int _distribuidorId, float _monto,
			float _descuento, float _montoFinal, int _preventaId,
			int _tipoPagoId, double _latitud, double _longitud,
			boolean _diferencia, boolean _estadoSincronizacion,
			int _hora,int _minuto,String _observacion,boolean _aplicarBonificacion,
			int _dosificacionId,String _tipoNit,float _descuentoCanal, float _descuentoAjuste,
			int _prontoPagoId, float _descuentoProntoPago, float _descuentoObjetivo, int _formaPagoId,
			String _codTransferencia, boolean _fromApk, boolean _fromShopp) 
	{
		super();
		this._id = _id;
		this._ventaIdServer = _ventaIdServer;
		this._dia = _dia;
		this._mes = _mes_;
		this._anio = _anio;
		this._clienteId = _clienteId;
		this._distribuidorId = _distribuidorId;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._preventaId = _preventaId;
		this._tipoPagoId = _tipoPagoId;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._diferencia = _diferencia;
		this._estadoSincronizacion = _estadoSincronizacion;
		this._hora = _hora;
		this._minuto = _minuto;
		this._observacion = _observacion;
		this._aplicarBonificacion = _aplicarBonificacion;
		this._dosificacionId = _dosificacionId;
		this._tipoNit = _tipoNit;
		this._descuentoCanal = _descuentoCanal;
		this._descuentoAjuste = _descuentoAjuste;
		this._prontoPagoId = _prontoPagoId;
		this._descuentoProntoPago = _descuentoProntoPago;
		this._descuentoObjetivo = _descuentoObjetivo;
		this._formaPagoId = _formaPagoId;
		this._codTransferencia = _codTransferencia;
		this._fromApk = _fromApk;
		this._fromShopp = _fromShopp;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_ventaIdServer() {
		return _ventaIdServer;
	}

	public void set_ventaIdServer(int _ventaIdServer) {
		this._ventaIdServer = _ventaIdServer;
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

	public void set_mes(int _mes_) {
		this._mes = _mes_;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
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

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}

	public int get_tipoPagoId() {
		return _tipoPagoId;
	}

	public void set_tipoPagoId(int _tipoPagoId) {
		this._tipoPagoId = _tipoPagoId;
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

	public boolean is_diferencia() {
		return _diferencia;
	}

	public void set_diferencia(boolean _diferencia) {
		this._diferencia = _diferencia;
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

	public String get_observacion() {
		return _observacion;
	}

	public void set_observacion(String _observacion) {
		this._observacion = _observacion;
	}

	public boolean is_aplicarBonificacion() {
		return _aplicarBonificacion;
	}

	public void set_aplicarBonificacion(boolean _aplicarBonificacion) {
		this._aplicarBonificacion = _aplicarBonificacion;
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

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public float get_descuentoProntoPago() {
		return _descuentoProntoPago;
	}

	public void set_descuentoProntoPago(float _descuentoProntoPago) {
		this._descuentoProntoPago = _descuentoProntoPago;
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

	public boolean is_fromApk() {
		return _fromApk;
	}

	public void set_fromApk(boolean _fromApk) {
		this._fromApk = _fromApk;
	}

	public boolean is_fromShopp() {
		return _fromShopp;
	}

	public void set_fromShopp(boolean _fromShopp) {
		this._fromShopp = _fromShopp;
	}
}
