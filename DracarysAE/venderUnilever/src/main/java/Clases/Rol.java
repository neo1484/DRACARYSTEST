package Clases;

public class Rol 
{
	private int _rowId;
	private int _empleadoId;
	private String _rol;
	
	public Rol(int _empleadoId, String _rol) 
	{
		super();
		this._empleadoId = _empleadoId;
		this._rol = _rol;
	}

	public int get_rowId() {
		return _rowId;
	}

	public void set_rowId(int _rowId) {
		this._rowId = _rowId;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public String get_rol() {
		return _rol;
	}

	public void set_rol(String _rol) {
		this._rol = _rol;
	}
	
}
