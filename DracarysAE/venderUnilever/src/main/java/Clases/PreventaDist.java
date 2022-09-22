package Clases;

public class PreventaDist 
{
	private int _id;
	private int _preventaId;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _clienteId;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _tipoPagoId;
	private String _tipoPagoFD;
	private String _clienteFD;
	private String _fechaFD;
	private String _montoFD;
	private int _estadoEntrega;
	private boolean _estadoSincronizacion;
	private int _empleadoId;
	private String _nit;
	private String _nombreFactura;
	private String _observacion;
	private float _descuento2;
	private int _dosificacionId;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private float _descuentoProntoPago;
	private float _descuentoObjetivo;
	private int _prontoPagoId;
	private String _codTransferencia;
	private boolean _fromApk;
	private boolean _fromShopp;
	private float _descuentoSocio;
	private float _descuentoIncentivo;
	
	public PreventaDist(){}

	public PreventaDist(int _id, int _preventaId, int _dia, int _mes,
			int _anio, int _clienteId, float _monto, float _descuento,
			float _montoFinal, int _tipoPagoId, String _tipoPagoFD,
			String _clienteFD, String _fechaFD, String _montoFD,
			int _estadoEntrega, boolean _estadoSincronizacion,int _empleadoId,
			String _nit,String _nombreFactura,String _observacion,
			float _descuento2,int _dosificacionId, float _descuentoCanal, 
			float _descuentoAjuste, float _descuentoProntoPago, float _descuentoObjetivo,
			int _prontoPagoId, String _codTransferencia, boolean _fromApk, 
			boolean _fromShopp, float _descuentoSocio, float _descuentoIncentivo)
	{
		super();
		this._id = _id;
		this._preventaId = _preventaId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._clienteId = _clienteId;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._tipoPagoId = _tipoPagoId;
		this._tipoPagoFD = _tipoPagoFD;
		this._clienteFD = _clienteFD;
		this._fechaFD = _fechaFD;
		this._montoFD = _montoFD;
		this._estadoEntrega = _estadoEntrega;
		this._estadoSincronizacion = _estadoSincronizacion;
		this._empleadoId = _empleadoId;
		this._nit = _nit;
		this._nombreFactura = _nombreFactura;
		this._observacion = _observacion;
		this._descuento2 = _descuento2;
		this._dosificacionId = _dosificacionId;
		this._descuentoCanal = _descuentoCanal;
		this._descuentoAjuste = _descuentoAjuste;
		this._descuentoProntoPago = _descuentoProntoPago;
		this._descuentoObjetivo = _descuentoObjetivo;
		this._prontoPagoId = _prontoPagoId;
		this._codTransferencia = _codTransferencia;
		this._fromApk = _fromApk;
		this._fromShopp = _fromShopp;
		this._descuentoSocio = _descuentoSocio;
		this._descuentoObjetivo = _descuentoObjetivo;
		this._descuentoIncentivo = _descuentoIncentivo;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
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

	public String get_tipoPagoFD() {
		return _tipoPagoFD;
	}

	public void set_tipoPagoFD(String _tipoPagoFD) {
		this._tipoPagoFD = _tipoPagoFD;
	}

	public String get_clienteFD() {
		return _clienteFD;
	}

	public void set_clienteFD(String _clienteFD) {
		this._clienteFD = _clienteFD;
	}

	public String get_fechaFD() {
		return _fechaFD;
	}

	public void set_fechaFD(String _fechaFD) {
		this._fechaFD = _fechaFD;
	}

	public String get_montoFD() {
		return _montoFD;
	}

	public void set_montoFD(String _montoFD) {
		this._montoFD = _montoFD;
	}

	public int get_estadoEntrega() {
		return _estadoEntrega;
	}

	public void set_estadoEntrega(int _estadoEntrega) {
		this._estadoEntrega = _estadoEntrega;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public String get_nombreFactura() {
		return _nombreFactura;
	}

	public void set_nombreFactura(String _nombreFactura) {
		this._nombreFactura = _nombreFactura;
	}

	public String get_observacion() {
		return _observacion;
	}

	public void set_observacion(String _observacion) {
		this._observacion = _observacion;
	}

	public float get_descuento2() {
		return _descuento2;
	}

	public void set_descuento2(float _descuento2) {
		this._descuento2 = _descuento2;
	}

	public int get_dosificacionId() {
		return _dosificacionId;
	}

	public void set_dosificacionId(int _dosificacionId) {
		this._dosificacionId = _dosificacionId;
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

	public float get_descuentoSocio() {
		return _descuentoSocio;
	}

	public void set_descuentoSocio(float _descuentoSocio) {
		this._descuentoSocio = _descuentoSocio;
	}

	public float get_descuentoIncentivo() {
		return _descuentoIncentivo;
	}

	public void set_descuentoIncentivo(float _descuentoIncentivo) {
		this._descuentoIncentivo = _descuentoIncentivo;
	}
}
