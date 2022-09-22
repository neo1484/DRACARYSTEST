package Clases;

public class MotivoCHANGE 
{
	private int _motivoPopId;
	private String _descripcion;
	
	public MotivoCHANGE(){}

	public MotivoCHANGE(int _motivoPopId, String _descripcion) 
	{
		super();
		this._motivoPopId = _motivoPopId;
		this._descripcion = _descripcion;
	}

	public int get_motivoPopId() {
		return _motivoPopId;
	}

	public void set_motivoPopId(int _motivoPopId) {
		this._motivoPopId = _motivoPopId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
}
