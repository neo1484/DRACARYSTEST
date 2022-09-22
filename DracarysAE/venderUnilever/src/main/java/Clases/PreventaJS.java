package Clases;

import java.util.ArrayList;

public class PreventaJS 
{
	private int _empleadoId;
	private int _clienteId;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _tipoPagoId;
	private double _latitud;
	private double _longitud;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _hora;
	private int _minuto;
	private String _nombreFactura;
	private String _nit;
	private boolean _nitNuevo;
	private int _almacenId;
	private int _rutaId;
	private int _diaId;
	private int _nroPreVenta;
	private String _observacion;
	private boolean _aplicarBonificacion;
	private int _diaEntrega;
	private int _mesEntrega;
	private int _anioEntrega;
	private int _dosificacionId;
	private String _tipoNit;
	private String _ruta;
	private String _tipoVisita;
	private int _zonaVentaId;
	private int _prontoPagoId;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private float _descuentoProntoPago;
	private float _descuentoObjetivo;
	private int _formaPagoId;
	private String _codTransferencia;
	private boolean _fromApk;
	ArrayList<PreventaProductoTempJS> _listaPreventaProductoTempJS;
	
	public PreventaJS(){}

	public PreventaJS(int _empleadoId, int _clienteId, float _monto, float _descuento, float _montoFinal, int _tipoPagoId, 
			double _latitud, double _longitud, int _dia, int _mes, int _anio, int _hora, int _minuto, String _nombreFactura, 
			String _nit, boolean _nitNuevo, int _almacenId, int _rutaId, int _diaId, int _nroPreventa, 
			String _observacion, boolean _aplicarBonificacion, int _diaEntrega, int _mesEntrega, int _anioEntrega,
			int _dosificacionId, String _tipoNit, String _ruta, String _tipoVisita, int _zonaVentaId, int _prontoPagoId,
			float _descuentoCanal, float _descuentoAjuste, float _descuentoProntoPago, float _descuentoObjetivo,
			int _formaPagoId, String _codTransferencia, boolean _fromApk, ArrayList<PreventaProductoTempJS> _listaPreventaProductoTempJS) 
	{
		super();
		this._empleadoId = _empleadoId;
		this._clienteId = _clienteId;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._tipoPagoId = _tipoPagoId;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._hora = _hora;
		this._minuto = _minuto;
		this._nombreFactura = _nombreFactura;
		this._nit = _nit;
		this._nitNuevo = _nitNuevo;
		this._almacenId = _almacenId;
		this._rutaId = _rutaId;
		this._diaId = _diaId;
		this._nroPreVenta = _nroPreventa;
		this._observacion = _observacion;
		this._aplicarBonificacion = _aplicarBonificacion;
		this._diaEntrega = _diaEntrega;
		this._mesEntrega = _mesEntrega;
		this._anioEntrega = _anioEntrega;
		this._dosificacionId = _dosificacionId;
		this._tipoNit = _tipoNit;
		this._ruta = _ruta;
		this._tipoVisita = _tipoVisita;
		this._zonaVentaId = _zonaVentaId;
		this._prontoPagoId = _prontoPagoId;
		this._descuentoCanal = _descuentoCanal;
		this._descuentoAjuste = _descuentoAjuste;
		this._descuentoProntoPago = _descuentoProntoPago;
		this._descuentoObjetivo = _descuentoObjetivo;
		this._formaPagoId = _formaPagoId;
		this._codTransferencia = _codTransferencia;
		this._fromApk = _fromApk;
		this._listaPreventaProductoTempJS = _listaPreventaProductoTempJS;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
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

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public int get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(int _rutaId) {
		this._rutaId = _rutaId;
	}

	public int get_diaId() {
		return _diaId;
	}

	public void set_diaId(int _diaId) {
		this._diaId = _diaId;
	}

	public int get_nroPreVenta() {
		return _nroPreVenta;
	}

	public void set_nroPreVenta(int _nroPreVenta) {
		this._nroPreVenta = _nroPreVenta;
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

	public int get_diaEntrega() {
		return _diaEntrega;
	}

	public void set_diaEntrega(int _diaEntrega) {
		this._diaEntrega = _diaEntrega;
	}

	public int get_mesEntrega() {
		return _mesEntrega;
	}

	public void set_mesEntrega(int _mesEntrega) {
		this._mesEntrega = _mesEntrega;
	}

	public int get_anioEntrega() {
		return _anioEntrega;
	}

	public void set_anioEntrega(int _anioEntrega) {
		this._anioEntrega = _anioEntrega;
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

	public String get_ruta() {
		return _ruta;
	}

	public void set_ruta(String _ruta) {
		this._ruta = _ruta;
	}

	public String get_tipoVisita() {
		return _tipoVisita;
	}

	public void set_tipoVisita(String _tipoVisita) {
		this._tipoVisita = _tipoVisita;
	}

	public int get_zonaVentaId() {
		return _zonaVentaId;
	}

	public void set_zonaVentaId(int _zonaVentaId) {
		this._zonaVentaId = _zonaVentaId;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
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

	public ArrayList<PreventaProductoTempJS> get_listaPreventaProductoTempJS() {
		return _listaPreventaProductoTempJS;
	}

	public void set_listaPreventaProductoTempJS(ArrayList<PreventaProductoTempJS> _listaPreventaProductoTempJS) {
		this._listaPreventaProductoTempJS = _listaPreventaProductoTempJS;
	}
}
