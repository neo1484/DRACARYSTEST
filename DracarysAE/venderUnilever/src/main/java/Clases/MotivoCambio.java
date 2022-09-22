package Clases;

public class MotivoCambio 
{
	private int _motivoCambioId;
	private String _descripcion;
	
	public MotivoCambio(){}
	
	public MotivoCambio(int _motivoCambioId, String _descripcion) 
	{
		super();
		this._motivoCambioId = _motivoCambioId;
		this._descripcion = _descripcion;
	}

	public int get_motivoCambioId() {
		return _motivoCambioId;
	}

	public void set_motivoCambioId(int _motivoCambioId) {
		this._motivoCambioId = _motivoCambioId;
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
