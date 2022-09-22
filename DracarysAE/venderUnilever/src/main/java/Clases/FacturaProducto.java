package Clases;

public class FacturaProducto 
{
	private int _rowId;
	private int _facturaId;
	private int _productoId;
	private int _promocionId;
	private int _cantidad;
	private int _cantidadPaquete;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _clienteId;
	private int _empleadoId;
	private boolean _sincronizacion;
	private int _precioId;
	private int _noFactura;
	
	public FacturaProducto(int _rowId,int _facturaId,int _productoId, int _promocionId, int _cantidad,
			int _cantidadPaquete, float _monto, float _descuento,
			float _montoFinal, int _clienteId, int _empleadoId,
			boolean _sincronizacion,int _precioId,int _noFactura) 
	{
		super();
		this._rowId = _rowId;
		this._facturaId = _facturaId;
		this._productoId = _productoId;
		this._promocionId = _promocionId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._clienteId = _clienteId;
		this._empleadoId = _empleadoId;
		this._sincronizacion = _sincronizacion;
		this._precioId = _precioId;
		this._noFactura = _noFactura;
	}
		
	public int get_rowId() {
		return _rowId;
	}

	public void set_rowId(int _rowId) {
		this._rowId = _rowId;
	}

	public int get_facturaId() {
		return _facturaId;
	}

	public void set_facturaId(int _facturaId) {
		this._facturaId = _facturaId;
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

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public boolean is_sincronizacion() {
		return _sincronizacion;
	}

	public void set_sincronizacion(boolean _sincronizacion) {
		this._sincronizacion = _sincronizacion;
	}

	public int get_precioId() {
		return _precioId;
	}

	public void set_precioId(int _precioId) {
		this._precioId = _precioId;
	}

	public int get_noFactura() {
		return _noFactura;
	}

	public void set_noFactura(int _noFactura) {
		this._noFactura = _noFactura;
	}
}
