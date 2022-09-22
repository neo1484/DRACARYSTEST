package Clases;

public class DevolucionDistribuidorProducto 
{
	private int _id;
	private int _almacenDevolucionId;
	private int _productoId;
	private int _cantidad;
	private int _cantidadPaquete;
	private boolean _estadoSincronizacion;
	
	public DevolucionDistribuidorProducto(){}

	public DevolucionDistribuidorProducto(int _id, int _almacenDevolucionId,
			int _productoId, int _cantidad, int _cantidadPaquete,
			boolean _estadoSincronizacion) 
	{
		super();
		this._id = _id;
		this._almacenDevolucionId = _almacenDevolucionId;
		this._productoId = _productoId;
		this._cantidad = _cantidad;
		this._cantidadPaquete = _cantidadPaquete;
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_almacenDevolucionId() {
		return _almacenDevolucionId;
	}

	public void set_almacenDevolucionId(int _almacenDevolucionId) {
		this._almacenDevolucionId = _almacenDevolucionId;
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

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}
}
