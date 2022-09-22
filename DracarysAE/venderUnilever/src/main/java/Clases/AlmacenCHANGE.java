package Clases;

public class AlmacenCHANGE 
{
	private int _almacenId;
	private String _descripcion;
	
	public AlmacenCHANGE(){}

	public AlmacenCHANGE(int _almacenId, String _descripcion) 
	{
		super();
		this._almacenId = _almacenId;
		this._descripcion = _descripcion;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
}
