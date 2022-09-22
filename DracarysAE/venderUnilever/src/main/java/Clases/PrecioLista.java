package Clases;

public class PrecioLista 
{
	private int _precioListaId;
	private int _productoId;
	private float _precio;
	private float _precioPaquete;
	private float _descuentoUnidad;
	private float _descuentoPaquete;
	private float _margenUnidad;
	private float _margenPaquete;
	private int _precioId;
	private boolean _activo;
	
	public PrecioLista(){}

	public PrecioLista(int _precioListaId, int _productoId, float _precio,
			float _precioPaquete, float _descuentoUnidad, float _descuentoPaquete,
			float _margenUnidad,float _margenPaquete,int _precioId,boolean _activo) 
	{
		super();
		this._precioListaId = _precioListaId;
		this._productoId = _productoId;
		this._precio = _precio;
		this._precioPaquete = _precioPaquete;
		this._descuentoUnidad = _descuentoUnidad;
		this._descuentoPaquete = _descuentoPaquete;
		this._margenUnidad = _margenUnidad;
		this._margenPaquete = _margenPaquete;
		this._precioId = _precioId;
		this._activo = _activo;
	}

	public int get_precioListaId() {
		return _precioListaId;
	}

	public void set_precioListaId(int _precioListaId) {
		this._precioListaId = _precioListaId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
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

	public float get_descuentoUnidad() {
		return _descuentoUnidad;
	}

	public void set_descuentoUnidad(float _descuentoUnidad) {
		this._descuentoUnidad = _descuentoUnidad;
	}

	public float get_descuentoPaquete() {
		return _descuentoPaquete;
	}

	public void set_descuentoPaquete(float _descuentoPaquete) {
		this._descuentoPaquete = _descuentoPaquete;
	}

	public float get_margenUnidad() {
		return _margenUnidad;
	}

	public void set_margenUnidad(float _margenUnidad) {
		this._margenUnidad = _margenUnidad;
	}

	public float get_margenPaquete() {
		return _margenPaquete;
	}

	public void set_margenPaquete(float _margenPaquete) {
		this._margenPaquete = _margenPaquete;
	}

	public int get_precioId() {
		return _precioId;
	}

	public void set_precioId(int _precioId) {
		this._precioId = _precioId;
	}

	public boolean is_activo() {
		return _activo;
	}

	public void set_activo(boolean _activo) {
		this._activo = _activo;
	}
}
