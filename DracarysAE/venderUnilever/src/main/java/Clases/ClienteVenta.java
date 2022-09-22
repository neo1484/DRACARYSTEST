package Clases;

public class ClienteVenta 
{
	private int _id;
	private int _clienteId;
	private int _codigo;
	private int _clienteTipoNegocioId;
	private String _nombreCompleto;
	private double _latitud;
	private double _longitud;
	private String _direccion;
	private String _telefono;
	private String _celular;
	private int _precioListaId;
	private float _descuento;
	private int _tipoPagoId;
	private int _diasPago;
	private float _topeCredito;
	private int _rutaId;
	private String _rutaDescripcion;
	private String _razonSocial;
	private boolean _autoventa;
	private boolean _estadoAtendido;
	private boolean _clientePunteoSincronizado;
	private boolean _estadoSincronizacion;
	private String _nombreFactura;
	private String _nit;
	private int _diaId;
	private float _montoPendienteCredito;
	private int _provinciaId;
	private int _canalRutaId;
	private String _observacion;
	private String _pedidoExternoId;

	public ClienteVenta(){}

	public ClienteVenta(int _id, int _clienteId, int _codigo, int _clienteTipoNegocioId,
			String _nombreCompleto, double _latitud, double _longitud,
			String _direccion, String _telefono, String _celular,
			int _precioListaId, float _descuento, int _tipoPagoId,
			int _diasPago, float _topeCredito, int _rutaId,
			String _rutaDescripcion, String _razonSocial, boolean _autoventa,
			boolean _estadoAtendido,boolean _clientePunteoSincronizado,boolean _estadoSincronizacion,
			String _nombreFactura,String _nit,int _diaId,float _montoPendienteCredito,int _provinciaId,
			int _canalRutaId, String _observacion, String _pedidoExternoId) 
	{
		super();
		this._id = _id;
		this._clienteId = _clienteId;
		this._codigo = _codigo;
		this._clienteTipoNegocioId = _clienteTipoNegocioId;
		this._nombreCompleto = _nombreCompleto;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._direccion = _direccion;
		this._telefono = _telefono;
		this._celular = _celular;
		this._precioListaId = _precioListaId;
		this._descuento = _descuento;
		this._tipoPagoId = _tipoPagoId;
		this._diasPago = _diasPago;
		this._topeCredito = _topeCredito;
		this._rutaId = _rutaId;
		this._rutaDescripcion = _rutaDescripcion;
		this._razonSocial = _razonSocial;
		this._autoventa = _autoventa;
		this._estadoAtendido = _estadoAtendido;
		this._clientePunteoSincronizado = _clientePunteoSincronizado;
		this._estadoSincronizacion = _estadoSincronizacion;
		this._nombreFactura = _nombreFactura;
		this._nit = _nit;
		this._diaId = _diaId;
		this._montoPendienteCredito = _montoPendienteCredito;
		this._provinciaId = _provinciaId;
		this._canalRutaId = _canalRutaId;
		this._observacion = _observacion;
		this._pedidoExternoId = _pedidoExternoId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_codigo() {
		return _codigo;
	}

	public void set_codigo(int _codigo) {
		this._codigo = _codigo;
	}

	public int get_clienteTipoNegocioId() {
		return _clienteTipoNegocioId;
	}

	public void set_clienteTipoNegocioId(int _clienteTipoNegocioId) {
		this._clienteTipoNegocioId = _clienteTipoNegocioId;
	}

	public String get_nombreCompleto() {
		return _nombreCompleto;
	}

	public void set_nombreCompleto(String _nombreCompleto) {
		this._nombreCompleto = _nombreCompleto;
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

	public String get_direccion() {
		return _direccion;
	}

	public void set_direccion(String _direccion) {
		this._direccion = _direccion;
	}

	public String get_telefono() {
		return _telefono;
	}

	public void set_telefono(String _telefono) {
		this._telefono = _telefono;
	}

	public String get_celular() {
		return _celular;
	}

	public void set_celular(String _celular) {
		this._celular = _celular;
	}

	public int get_precioListaId() {
		return _precioListaId;
	}

	public void set_precioListaId(int _precioListaId) {
		this._precioListaId = _precioListaId;
	}

	public float get_descuento() {
		return _descuento;
	}

	public void set_descuento(float _descuento) {
		this._descuento = _descuento;
	}

	public int get_tipoPagoId() {
		return _tipoPagoId;
	}

	public void set_tipoPagoId(int _tipoPagoId) {
		this._tipoPagoId = _tipoPagoId;
	}

	public int get_diasPago() {
		return _diasPago;
	}

	public void set_diasPago(int _diasPago) {
		this._diasPago = _diasPago;
	}

	public float get_topeCredito() {
		return _topeCredito;
	}

	public void set_topeCredito(float _topeCredito) {
		this._topeCredito = _topeCredito;
	}

	public int get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(int _rutaId) {
		this._rutaId = _rutaId;
	}

	public String get_rutaDescripcion() {
		return _rutaDescripcion;
	}

	public void set_rutaDescripcion(String _rutaDescripcion) {
		this._rutaDescripcion = _rutaDescripcion;
	}

	public String get_razonSocial() {
		return _razonSocial;
	}

	public void set_razonSocial(String _razonSocial) {
		this._razonSocial = _razonSocial;
	}

	public boolean is_autoventa() {
		return _autoventa;
	}

	public void set_autoventa(boolean _autoventa) {
		this._autoventa = _autoventa;
	}

	public boolean is_estadoAtendido() {
		return _estadoAtendido;
	}

	public void set_estadoAtendido(boolean _estadoAtendido) {
		this._estadoAtendido = _estadoAtendido;
	}

	public boolean is_clientePunteoSincronizado() {
		return _clientePunteoSincronizado;
	}

	public void set_clientePunteoSincronizado(boolean _clientePunteoSincronizado) {
		this._clientePunteoSincronizado = _clientePunteoSincronizado;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
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

	public int get_diaId() {
		return _diaId;
	}

	public void set_diaId(int _diaId) {
		this._diaId = _diaId;
	}

	public float get_montoPendienteCredito() {
		return _montoPendienteCredito;
	}

	public void set_montoPendienteCredito(float _montoPendienteCredito) {
		this._montoPendienteCredito = _montoPendienteCredito;
	}

	public int get_provinciaId() {
		return _provinciaId;
	}

	public void set_provinciaId(int _provinciaId) {
		this._provinciaId = _provinciaId;
	}

	public int get_canalRutaId() {
		return _canalRutaId;
	}

	public void set_canalRutaId(int _canalRutaId) {
		this._canalRutaId = _canalRutaId;
	}

	public String get_observacion() {
		return _observacion;
	}

	public void set_observacion(String _observacion) {
		this._observacion = _observacion;
	}

	public String get_pedidoExternoId() {
		return _pedidoExternoId;
	}

	public void set_pedidoExternoId(String _pedidoExternoId) {
		this._pedidoExternoId = _pedidoExternoId;
	}
}
