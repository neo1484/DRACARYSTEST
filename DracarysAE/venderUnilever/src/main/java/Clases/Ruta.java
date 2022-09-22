package Clases;

public class Ruta 
{
	private int _rutaId;
	private String _descripcion;
	
	public Ruta(){}

	public Ruta(int _rutaId, String _descripcion) 
	{
		super();
		this._rutaId = _rutaId;
		this._descripcion = _descripcion;
	}

	public int get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(int _rutaId) {
		this._rutaId = _rutaId;
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
