package Clases;

public class TipoCalle 
{
	private int _tipoCalleId;
	private String _descripcion;

	public TipoCalle(){}

	public TipoCalle(int _tipoCalleId, String _descripcion) 
	{
		super();
		this._tipoCalleId = _tipoCalleId;
		this._descripcion = _descripcion;
	}


	public int get_tipoCalleId() {
		return _tipoCalleId;
	}

	public void set_tipoCalleId(int _tipoCalleId) {
		this._tipoCalleId = _tipoCalleId;
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
