package Clases;

public class DosificacionProveedor 
{
	private int _dosificacionId;
	private int _proveedorId;
	private boolean _activa;
	private String _descripcion;
	
	public DosificacionProveedor(){}

	public DosificacionProveedor(int _dosificacionId, int _proveedorId, boolean _activa,String _descripcion) 
	{
		super();
		this._dosificacionId = _dosificacionId;
		this._proveedorId = _proveedorId;
		this._activa = _activa;
		this._descripcion = _descripcion;
	}

	public int get_dosificacionId() {
		return _dosificacionId;
	}

	public void set_dosificacionId(int _dosificacionId) {
		this._dosificacionId = _dosificacionId;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}

	public boolean is_activa() {
		return _activa;
	}

	public void set_activa(boolean _activa) {
		this._activa = _activa;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	} 
	
	public String toString()
	{
		return this._descripcion;
	}	
	
}
