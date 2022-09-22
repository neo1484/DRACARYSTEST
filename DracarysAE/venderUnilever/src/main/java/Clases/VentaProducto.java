package Clases;

public class VentaProducto 
{
	private int _id;
	private int _ventaId;
	private int _productoId;
	private int _promocionId;
	private int _cantidad;
	private int _cantidadPaquete;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _motivoId;
	private boolean _estadoSincronizacion;
	private float _costo;
	private int _costoId;
	private int _precioId;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private int _canalPrecioRutaId;
	private float _descuentoProntoPago;
	
	public VentaProducto(){}

	public VentaProducto(int _id, int _ventaId, int _productoId,
			int _promocionId, int _cantidad, int _cantidadPaquete,
			float _monto, float _descuento, float _montoFinal, 
			int _motivoId,boolean _estadoSincronizacion,float _costo,
			int _costoId, int _precioId, float _descuentoCanal,
			float _descuentoAjuste, int _canalPrecioRutaId, 
			float _descuentoProntoPago)
	{
		super();
		this._id = _id;
		this._ventaId = _ventaId;
		this._productoId = _productoId;
		this._promocionId = _promocionId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._motivoId = _motivoId;
		this._estadoSincronizacion = _estadoSincronizacion;
		this._costo = _costo;
		this._costoId = _costoId;
		this._precioId = _precioId;
		this._descuentoCanal = _descuentoCanal;
		this._descuentoAjuste = _descuentoAjuste;
		this._canalPrecioRutaId = _canalPrecioRutaId;
		this._descuentoProntoPago = _descuentoProntoPago;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
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

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public float get_costo() {
		return _costo;
	}

	public void set_costo(float _costo) {
		this._costo = _costo;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}

	public int get_precioId() {
		return _precioId;
	}

	public void set_precioId(int _precioId) {
		this._precioId = _precioId;
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
}
