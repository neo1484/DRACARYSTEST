package Clases;

public class Ciudad 
{
	private int _ciudadId;
	private String _nombre;
	
	public Ciudad(){}

	public Ciudad(int _ciudadId, String _nombre) 
	{
		super();
		this._ciudadId = _ciudadId;
		this._nombre = _nombre;
	}

	public int get_ciudadId() {
		return _ciudadId;
	}

	public void set_ciudadId(int _ciudadId) {
		this._ciudadId = _ciudadId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	public String toString()
	{
		return this._nombre;
	}
}
