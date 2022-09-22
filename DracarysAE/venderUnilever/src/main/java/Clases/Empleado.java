package Clases;

public class Empleado 
{
	private int _empleadoId;
	private String _nombreCompleto;
	
	public Empleado(){}

	public Empleado(int _empleadoId, String _nombreCompleto) 
	{
		super();
		this._empleadoId = _empleadoId;
		this._nombreCompleto = _nombreCompleto;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public String get_nombreCompleto() {
		return _nombreCompleto;
	}

	public void set_nombreCompleto(String _nombreCompleto) {
		this._nombreCompleto = _nombreCompleto;
	}
	
	public String toString()
	{
	    return this._nombreCompleto;
	}
}
