package Clases;

public class Factura 
{
	private int _rowId;
	private String _numero;
	private String _nombre;
	private String _nit;
	private int _fechaDia;
	private int _fechaMes;
	private int _fechaAnio;
	private int _fechaHora;
	private int _fechaMinuto;
	private int _fechaLimiteEmisionDia;
	private int _fechaLimiteEmisionMes;
	private int _fechaLimiteEmisionAnio;
	private int _fechaLimiteEmisionHora;
	private int _fechaLimiteEmisionMinuto;
	private float _montoTotal;
	private float _descuento;
	private float _montoFinal;
	private String _montoPalabras;
	private String _codigoAutorizacion;
	private String _codigoControl;
	private String _facturaTipoId;
	private int _almacenId;
	private boolean _anulada;
	private int _anulacionUsuarioId;
	private String _anulacionMotivo;
	private int _anulacionFechaDia;
	private int _anulacionFechaMes;
	private int _anulacionFechaAnio;
	private int _dosificacionId;
	private int _empleadoId;
	private long _qrTamano;
	private String _qrExtension;
	private String _qrBinario;
	private String _qrMimeType;
	private int _clienteId;
	private int _numeroItems;
	private boolean _sincronizacion;
	private boolean _impreso;
	private int _ventaId;
	private int _serverVentaId;
	private boolean _nitNuevo;
	private int _noFactura;
	private String _tipoNit;
	
	public Factura(int _rowId,String _numero, String _nombre, String _nit, int _fechaDia,
			int _fechaMes, int _fechaAnio, int _fechaHora, int _fechaMinuto,
			int _fechaLimiteEmisionDia, int _fechaLimiteEmisionMes,
			int _fechaLimiteEmisionAnio, int _fechaLimiteEmisionHora,
			int _fechaLimiteEmisionMinuto, float _montoTotal, float _descuento,
			float _montoFinal, String _montoPalabras,
			String _codigoAutorizacion, String _codigoControl,
			String _facturaTipoId, int _almacenId, boolean _anulada,
			int _anulacionUsuarioId, String _anulacionMotivo,
			int _anulacionFechaDia, int _anulacionFechaMes,
			int _anulacionFechaAnio, int _dosificacionId, int _empleadoId,
			long _qrTamano, String _qrExtension, String _qrBinario,
			String _qrMimeType, int _clienteId, int _numeroItems,
			boolean _sincronizacion,boolean _impreso,int _ventaId,
			int _serverVentaId,boolean _nitNuevo,int _noFactura,String _tipoNit) 
	{
		super();
		this._rowId = _rowId;
		this._numero = _numero;
		this._nombre = _nombre;
		this._nit = _nit;
		this._fechaDia = _fechaDia;
		this._fechaMes = _fechaMes;
		this._fechaAnio = _fechaAnio;
		this._fechaHora = _fechaHora;
		this._fechaMinuto = _fechaMinuto;
		this._fechaLimiteEmisionDia = _fechaLimiteEmisionDia;
		this._fechaLimiteEmisionMes = _fechaLimiteEmisionMes;
		this._fechaLimiteEmisionAnio = _fechaLimiteEmisionAnio;
		this._fechaLimiteEmisionHora = _fechaLimiteEmisionHora;
		this._fechaLimiteEmisionMinuto = _fechaLimiteEmisionMinuto;
		this._montoTotal = _montoTotal;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._montoPalabras = _montoPalabras;
		this._codigoAutorizacion = _codigoAutorizacion;
		this._codigoControl = _codigoControl;
		this._facturaTipoId = _facturaTipoId;
		this._almacenId = _almacenId;
		this._anulada = _anulada;
		this._anulacionUsuarioId = _anulacionUsuarioId;
		this._anulacionMotivo = _anulacionMotivo;
		this._anulacionFechaDia = _anulacionFechaDia;
		this._anulacionFechaMes = _anulacionFechaMes;
		this._anulacionFechaAnio = _anulacionFechaAnio;
		this._dosificacionId = _dosificacionId;
		this._empleadoId = _empleadoId;
		this._qrTamano = _qrTamano;
		this._qrExtension = _qrExtension;
		this._qrBinario = _qrBinario;
		this._qrMimeType = _qrMimeType;
		this._clienteId = _clienteId;
		this._numeroItems = _numeroItems;
		this._sincronizacion = _sincronizacion;
		this._impreso = _impreso;
		this._ventaId = _ventaId;
		this._serverVentaId = _serverVentaId;
		this._nitNuevo = _nitNuevo;
		this._noFactura = _noFactura;
		this._tipoNit = _tipoNit;
	}

	public int get_rowId() {
		return _rowId;
	}

	public void set_rowId(int _rowId) {
		this._rowId = _rowId;
	}

	public String get_numero() {
		return _numero;
	}

	public void set_numero(String _numero) {
		this._numero = _numero;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public int get_fechaDia() {
		return _fechaDia;
	}

	public void set_fechaDia(int _fechaDia) {
		this._fechaDia = _fechaDia;
	}

	public int get_fechaMes() {
		return _fechaMes;
	}

	public void set_fechaMes(int _fechaMes) {
		this._fechaMes = _fechaMes;
	}

	public int get_fechaAnio() {
		return _fechaAnio;
	}

	public void set_fechaAnio(int _fechaAnio) {
		this._fechaAnio = _fechaAnio;
	}

	public int get_fechaHora() {
		return _fechaHora;
	}

	public void set_fechaHora(int _fechaHora) {
		this._fechaHora = _fechaHora;
	}

	public int get_fechaMinuto() {
		return _fechaMinuto;
	}

	public void set_fechaMinuto(int _fechaMinuto) {
		this._fechaMinuto = _fechaMinuto;
	}

	public int get_fechaLimiteEmisionDia() {
		return _fechaLimiteEmisionDia;
	}

	public void set_fechaLimiteEmisionDia(int _fechaLimiteEmisionDia) {
		this._fechaLimiteEmisionDia = _fechaLimiteEmisionDia;
	}

	public int get_fechaLimiteEmisionMes() {
		return _fechaLimiteEmisionMes;
	}

	public void set_fechaLimiteEmisionMes(int _fechaLimiteEmisionMes) {
		this._fechaLimiteEmisionMes = _fechaLimiteEmisionMes;
	}

	public int get_fechaLimiteEmisionAnio() {
		return _fechaLimiteEmisionAnio;
	}

	public void set_fechaLimiteEmisionAnio(int _fechaLimiteEmisionAnio) {
		this._fechaLimiteEmisionAnio = _fechaLimiteEmisionAnio;
	}

	public int get_fechaLimiteEmisionHora() {
		return _fechaLimiteEmisionHora;
	}

	public void set_fechaLimiteEmisionHora(int _fechaLimiteEmisionHora) {
		this._fechaLimiteEmisionHora = _fechaLimiteEmisionHora;
	}

	public int get_fechaLimiteEmisionMinuto() {
		return _fechaLimiteEmisionMinuto;
	}

	public void set_fechaLimiteEmisionMinuto(int _fechaLimiteEmisionMinuto) {
		this._fechaLimiteEmisionMinuto = _fechaLimiteEmisionMinuto;
	}

	public float get_montoTotal() {
		return _montoTotal;
	}

	public void set_montoTotal(float _montoTotal) {
		this._montoTotal = _montoTotal;
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

	public String get_montoPalabras() {
		return _montoPalabras;
	}

	public void set_montoPalabras(String _montoPalabras) {
		this._montoPalabras = _montoPalabras;
	}

	public String get_codigoAutorizacion() {
		return _codigoAutorizacion;
	}

	public void set_codigoAutorizacion(String _codigoAutorizacion) {
		this._codigoAutorizacion = _codigoAutorizacion;
	}

	public String get_codigoControl() {
		return _codigoControl;
	}

	public void set_codigoControl(String _codigoControl) {
		this._codigoControl = _codigoControl;
	}

	public String get_facturaTipoId() {
		return _facturaTipoId;
	}

	public void set_facturaTipoId(String _facturaTipoId) {
		this._facturaTipoId = _facturaTipoId;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public boolean is_anulada() {
		return _anulada;
	}

	public void set_anulada(boolean _anulada) {
		this._anulada = _anulada;
	}

	public int get_anulacionUsuarioId() {
		return _anulacionUsuarioId;
	}

	public void set_anulacionUsuarioId(int _anulacionUsuarioId) {
		this._anulacionUsuarioId = _anulacionUsuarioId;
	}

	public String get_anulacionMotivo() {
		return _anulacionMotivo;
	}

	public void set_anulacionMotivo(String _anulacionMotivo) {
		this._anulacionMotivo = _anulacionMotivo;
	}

	public int get_anulacionFechaDia() {
		return _anulacionFechaDia;
	}

	public void set_anulacionFechaDia(int _anulacionFechaDia) {
		this._anulacionFechaDia = _anulacionFechaDia;
	}

	public int get_anulacionFechaMes() {
		return _anulacionFechaMes;
	}

	public void set_anulacionFechaMes(int _anulacionFechaMes) {
		this._anulacionFechaMes = _anulacionFechaMes;
	}

	public int get_anulacionFechaAnio() {
		return _anulacionFechaAnio;
	}

	public void set_anulacionFechaAnio(int _anulacionFechaAnio) {
		this._anulacionFechaAnio = _anulacionFechaAnio;
	}

	public int get_dosificacionId() {
		return _dosificacionId;
	}

	public void set_dosificacionId(int _dosificacionId) {
		this._dosificacionId = _dosificacionId;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public long get_qrTamano() {
		return _qrTamano;
	}

	public void set_qrTamano(long _qrTamano) {
		this._qrTamano = _qrTamano;
	}

	public String get_qrExtension() {
		return _qrExtension;
	}

	public void set_qrExtension(String _qrExtension) {
		this._qrExtension = _qrExtension;
	}

	public String get_qrBinario() {
		return _qrBinario;
	}

	public void set_qrBinario(String _qrBinario) {
		this._qrBinario = _qrBinario;
	}

	public String get_qrMimeType() {
		return _qrMimeType;
	}

	public void set_qrMimeType(String _qrMimeType) {
		this._qrMimeType = _qrMimeType;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_numeroItems() {
		return _numeroItems;
	}

	public void set_numeroItems(int _numeroItems) {
		this._numeroItems = _numeroItems;
	}

	public boolean is_sincronizacion() {
		return _sincronizacion;
	}

	public void set_sincronizacion(boolean _sincronizacion) {
		this._sincronizacion = _sincronizacion;
	}

	public boolean is_impreso() {
		return _impreso;
	}

	public void set_impreso(boolean _impreso) {
		this._impreso = _impreso;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
	}

	public int get_serverVentaId() {
		return _serverVentaId;
	}

	public void set_serverVentaId(int _serverVentaId) {
		this._serverVentaId = _serverVentaId;
	}

	public boolean is_nitNuevo() {
		return _nitNuevo;
	}

	public void set_nitNuevo(boolean _nitNuevo) {
		this._nitNuevo = _nitNuevo;
	}

	public int get_noFactura() {
		return _noFactura;
	}

	public void set_noFactura(int _noFactura) {
		this._noFactura = _noFactura;
	}
	
	public String get_tipoNit() {
		return _tipoNit;
	}

	public void set_tipoNit(String _tipoNit) {
		this._tipoNit = _tipoNit;
	}
}
