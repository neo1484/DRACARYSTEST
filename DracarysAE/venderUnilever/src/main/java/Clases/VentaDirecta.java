package Clases;

public class VentaDirecta 
{
	private int _id;
	private int _ventaDirectaIdServer;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _clienteId;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _tipoPagoId;
	private double _latitud;
	private double _longitud;
	private int _hora;
	private int _minuto;
	private String _observacion;
	private int _empleadoId;
	private String _factura;
	private String _nit;
	private boolean _nitNuevo;
	private int _noVentaDirecta;
	private boolean _estado;
	private boolean _aplicarBonificacion;
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
	private float _descuentoIncentivo;
	 
	public VentaDirecta(){}

	public VentaDirecta(int _id, int _ventaIdServer, int _dia, int _mes,
			int _anio, int _clienteId, float _monto, float _descuento,
			float _montoFinal, int _tipoPagoId, double _latitud,
			double _longitud, int _hora, int _minuto, String _observacion,
			int _empleadoId,String _factura,String _nit,boolean _nitNuevo,
			int _noVentaDirecta,boolean _estado,boolean _aplicarBonificacion,
			String _tipoNit,String _ruta,String _tipoVisita,int _zonaVentaId,
			int _prontoPagoId, float _descuentoCanal, float _descuentoAjuste,
			float _descuentoProntoPago, float _descuentoObjetivo, int _formaPagoId,
			String _codTransferencia, float _descuentoIncentivo)
	{
		super();
		this._id = _id;
		this._ventaDirectaIdServer = _ventaIdServer;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._clienteId = _clienteId;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._tipoPagoId = _tipoPagoId;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._hora = _hora;
		this._minuto = _minuto;
		this._observacion = _observacion;
		this._empleadoId = _empleadoId;
		this._factura = _factura;
		this._nit = _nit;
		this._nitNuevo = _nitNuevo;
		this._noVentaDirecta = _noVentaDirecta;
		this._estado = _estado;
		this._aplicarBonificacion = _aplicarBonificacion;
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
		this._descuentoIncentivo = _descuentoIncentivo;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_ventaIdServer() {
		return _ventaDirectaIdServer;
	}

	public void set_ventaIdServer(int _ventaIdServer) {
		this._ventaDirectaIdServer = _ventaIdServer;
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

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public String get_factura() {
		return _factura;
	}

	public void set_factura(String _factura) {
		this._factura = _factura;
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

	public int get_noVentaDirecta() {
		return _noVentaDirecta;
	}

	public void set_noVentaDirecta(int _noVentaDirecta) {
		this._noVentaDirecta = _noVentaDirecta;
	}

	public boolean is_estado() {
		return _estado;
	}

	public void set_estado(boolean _estado) {
		this._estado = _estado;
	}

	public int get_ventaDirectaIdServer() {
		return _ventaDirectaIdServer;
	}

	public void set_ventaDirectaIdServer(int _ventaDirectaIdServer) {
		this._ventaDirectaIdServer = _ventaDirectaIdServer;
	}

	public boolean is_aplicarBonificacion() {
		return _aplicarBonificacion;
	}

	public void set_aplicarBonificacion(boolean _aplicarBonificacion) {
		this._aplicarBonificacion = _aplicarBonificacion;
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

	public float get_descuentoIncentivo() {
		return _descuentoIncentivo;
	}

	public void set_descuentoIncentivo(float _descuentoIncentivo) {
		this._descuentoIncentivo = _descuentoIncentivo;
	}
}
