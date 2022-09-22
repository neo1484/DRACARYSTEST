package Clases;

public class VentaDirectaProductoTemp 
{
	private int _id;
	private int _ventaDirectaIdServer;
	private int _productoId;
	private int _promocionId;
	private int _cantidad;
	private int _cantidadPaquete;
	private float _monto;
	private float _descuento;
	private float _montoFinal;
	private int _costoId;
	private int _precioId;
	private int _noVentaDirecta;
	private int _clienteId;
	private int _tempId;
	private int _empleadoId;
	private float _descuentoCanal;
	private float _descuentoAjuste;
	private int _canalPrecioRutaId;	
	private float _descuentoProntoPago;

	public VentaDirectaProductoTemp(){}

	public VentaDirectaProductoTemp(int _id, int _ventaId, int _productoId,
			int _promocionId, int _cantidad, int _cantidadPaquete,
			float _monto, float _descuento, float _montoFinal, int _costoId,
			int _precioId,int _noVentaDirecta,int _clienteId,int _tempId,
			int _empleadoId, float _descuentoCanal, float _descuentoAjuste,
			int _canalPrecioRutaId, float _descuentoProntoPago) 
	{
		super();
		this._id = _id;
		this._ventaDirectaIdServer = _ventaId;
		this._productoId = _productoId;
		this._promocionId = _promocionId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._monto = _monto;
		this._descuento = _descuento;
		this._montoFinal = _montoFinal;
		this._costoId = _costoId;
		this._precioId = _precioId;
		this._noVentaDirecta = _noVentaDirecta;
		this._clienteId = _clienteId;
		this._tempId = _tempId;
		this._empleadoId = _empleadoId;
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
		return _ventaDirectaIdServer;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaDirectaIdServer = _ventaId;
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

	public int get_noVentaDirecta() {
		return _noVentaDirecta;
	}

	public void set_noVentaDirecta(int _noVentaDirecta) {
		this._noVentaDirecta = _noVentaDirecta;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_tempId() {
		return _tempId;
	}

	public void set_tempId(int _tempId) {
		this._tempId = _tempId;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public int get_ventaDirectaIdServer() {
		return _ventaDirectaIdServer;
	}

	public void set_ventaDirectaIdServer(int _ventaDirectaIdServer) {
		this._ventaDirectaIdServer = _ventaDirectaIdServer;
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