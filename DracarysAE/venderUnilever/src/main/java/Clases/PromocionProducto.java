package Clases;

public class PromocionProducto 
{
	private int _promocionId;
	private int _productoId;
	private int _cantidad;
	private int _cantidadPaquete;
	private float _descuento;
	private float _descuentoPaquete;
	private int _precioListaId;
	private int _costoId;
	private float _precio;
	private float _precioPaquete;
	
	public PromocionProducto(int _promocionId,int _productoId, int _cantidad,
			int _cantidadPaquete,float _descuento,float _descuentoPaquete,
			int _precioListaId,int _costoId,float _precio,float _precioPaquete) 
	{
		super();
		this._promocionId = _promocionId;
		this._productoId = _productoId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._descuento = _descuento;
		this._descuentoPaquete = _descuentoPaquete;
		this._precioListaId = _precioListaId;
		this._costoId = _costoId;
		this._precio = _precio;
		this._precioPaquete = _precioPaquete;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
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

	public float get_descuento() {
		return _descuento;
	}

	public void set_descuento(float _descuento) {
		this._descuento = _descuento;
	}

	public float get_descuentoPaquete() {
		return _descuentoPaquete;
	}

	public void set_descuentoPaquete(float _descuentoPaquete) {
		this._descuentoPaquete = _descuentoPaquete;
	}

	public int get_precioListaId() {
		return _precioListaId;
	}

	public void set_precioListaId(int _precioListaId) {
		this._precioListaId = _precioListaId;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}

	public float get_precio() {
		return _precio;
	}

	public void set_precio(float _precio) {
		this._precio = _precio;
	}

	public float get_precioPaquete() {
		return _precioPaquete;
	}

	public void set_precioPaquete(float _precioPaquete) {
		this._precioPaquete = _precioPaquete;
	}
}
