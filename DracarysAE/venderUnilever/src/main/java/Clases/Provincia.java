package Clases;

public class Provincia 
{
	private int _provinciaId;
	private String _nombre;
	private int _ciudadId;
	
	public Provincia(){}

	public Provincia(int _provinciaId, String _nombre, int _ciudadId) 
	{
		super();
		this._provinciaId = _provinciaId;
		this._nombre = _nombre;
		this._ciudadId = _ciudadId;
	}

	public int get_provinciaId() {
		return _provinciaId;
	}

	public void set_provinciaId(int _provinciaId) {
		this._provinciaId = _provinciaId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	public int get_ciudadId() {
		return _ciudadId;
	}

	public void set_ciudadId(int _ciudadId) {
		this._ciudadId = _ciudadId;
	}

	public String toString()
	{
		return this._nombre;
	}
}
