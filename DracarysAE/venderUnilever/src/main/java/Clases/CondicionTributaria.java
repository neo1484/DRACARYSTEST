package Clases;

public class CondicionTributaria 
{
	private String _nit;
	private int _anio;
	private float _montoAcumulado;
	
	public CondicionTributaria(String _nit, int _anio, float _montoAcumulado) 
	{
		super();
		this._nit = _nit;
		this._anio = _anio;
		this._montoAcumulado = _montoAcumulado;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
	}

	public float get_montoAcumulado() {
		return _montoAcumulado;
	}

	public void set_montoAcumulado(float _montoAcumulado) {
		this._montoAcumulado = _montoAcumulado;
	}
}
