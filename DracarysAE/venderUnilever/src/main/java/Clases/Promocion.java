package Clases;

public class Promocion 
{
	private int _promocionId;
	private String _descripcion;
	private String _descripcion25;
	private boolean _activa;
	private int _proveedorId;
	
	public Promocion(){}

	public Promocion(int _promocionId,String _descripcion,String _descripcion25,boolean _activa,
						int _proveedorId) 
	{
		super();
		this._promocionId = _promocionId;
		this._descripcion = _descripcion;
		this._descripcion25 = _descripcion25;
		this._activa = _activa;
		this._proveedorId = _proveedorId;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}

	public String get_descripcion25() {
		return _descripcion25;
	}

	public void set_descripcion25(String _descripcion25) {
		this._descripcion25 = _descripcion25;
	}

	public boolean is_activa() {
		return _activa;
	}

	public void set_activa(boolean _activa) {
		this._activa = _activa;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}
	
	public String toString()
	{
	    return this._descripcion;
	}
}
