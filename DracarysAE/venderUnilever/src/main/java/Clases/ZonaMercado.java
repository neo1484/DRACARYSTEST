package Clases;

public class ZonaMercado 
{
	private int _zonaMercadoId;
	private String _nombre;
	
	public ZonaMercado(){}
	
	public ZonaMercado(int _zonaMercadoId, String _nombre) 
	{
		super();
		this._zonaMercadoId = _zonaMercadoId;
		this._nombre = _nombre;
	}

	public int get_zonaMercadoId() {
		return _zonaMercadoId;
	}

	public void set_zonaMercadoId(int _zonaMercadoId) {
		this._zonaMercadoId = _zonaMercadoId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	public String toString(){ return this._nombre;}
}
