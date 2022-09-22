package Clases;

public class PreventaProducto 
{
	private int _id;
	private int _preventaId;
	private int _productoId;
	private int _cantidad;
	private int _cantidadPaquete;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _empleadoId;
	private int _promocionId;
	private boolean _estado;
	private float _costo;
	private int _costoId;
	private int _noPreventa;
	private int _precioId;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private int _canalPrecioRutaId;
	private float _descuentoProntoPago;
	
	public PreventaProducto(){}

	public PreventaProducto(int _id, int _preventaId,
			int _productoId,int _cantidad, int _cantidadPaquete, float _monto,
			float _descuento, float _montoFinal, int _empleadoId,
			int _promocionId, boolean _estado,float _costo,int _costoId,
			int _noPreventa,int _precioId, float _descuentoCanal, float _descuentoAjuste,
			int _canalPrecioRutaId, float _descuentoProntoPago) 
	{
		super();
		this._id = _id;
		this._preventaId = _preventaId;
		this._productoId = _productoId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._empleadoId = _empleadoId;
		this._promocionId = _promocionId;
		this._estado = _estado;
		this._costo = _costo;
		this._costoId = _costoId;
		this._noPreventa = _noPreventa;
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

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
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

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public boolean is_estado() {
		return _estado;
	}

	public void set_estado(boolean _estado) {
		this._estado = _estado;
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

	public int get_noPreventa() {
		return _noPreventa;
	}

	public void set_noPreventa(int _noPreventa) {
		this._noPreventa = _noPreventa;
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
