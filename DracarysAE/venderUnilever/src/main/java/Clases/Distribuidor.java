package Clases;

public class Distribuidor 
{
	private int _distribuidorId;
	private String _nombreCompleto;
	
	public Distribuidor(){}

	public Distribuidor(int _distribuidorId, String _nombreCompleto) 
	{
		super();
		this._distribuidorId = _distribuidorId;
		this._nombreCompleto = _nombreCompleto;
	}

	public int get_distribuidorId() {
		return _distribuidorId;
	}

	public void set_distribuidorId(int _distribuidorId) {
		this._distribuidorId = _distribuidorId;
	}

	public String get_nombreCompleto() {
		return _nombreCompleto;
	}

	public void set_nombreCompleto(String _nombreCompleto) {
		this._nombreCompleto = _nombreCompleto;
	}
	
	public String toString()
	{
		return _nombreCompleto;
	}
	
}
