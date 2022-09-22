package Clases;

public class Mercado 
{
	private int _mercadoId;
	private String _nombre;
	
	public Mercado(){}

	public Mercado(int _mercadoId, String _nombre)
	{
		super();
		this._mercadoId = _mercadoId;
		this._nombre = _nombre;
	}

	public int get_mercadoId() {
		return _mercadoId;
	}

	public void set_mercadoId(int _mercadoId) {
		this._mercadoId = _mercadoId;
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
