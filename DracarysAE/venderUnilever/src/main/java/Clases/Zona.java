package Clases;

public class Zona 
{
	private int _zonaId;
	private String _nombre;
	
	public Zona(){}

	public Zona(int _zonaId, String _nombre) 
	{
		super();
		this._zonaId = _zonaId;
		this._nombre = _nombre;
	}

	public int get_zonaId() {
		return _zonaId;
	}

	public void set_zonaId(int _zonaId) {
		this._zonaId = _zonaId;
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
