package Clases;

public class TipoNit 
{
	private String _tipoNit;

	public TipoNit(String _tipoNit) 
	{
		super();
		this._tipoNit = _tipoNit;
	}

	public String get_tipoNit() {
		return _tipoNit;
	}

	public void set_tipoNit(String _tipoNit) {
		this._tipoNit = _tipoNit;
	}
	
	public String toString()
	{
	    return this._tipoNit;
	}
}
