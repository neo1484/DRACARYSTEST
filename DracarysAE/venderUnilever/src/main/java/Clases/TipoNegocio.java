package Clases;

public class TipoNegocio 
{
	private int _tipoNegocioId;
	private String _descripcion;

	public TipoNegocio(){}

	public TipoNegocio(int _tipoNegocioId, String _descripcion) {
		super();
		this._tipoNegocioId = _tipoNegocioId;
		this._descripcion = _descripcion;
	}

	public int get_tipoNegocioId() {
		return _tipoNegocioId;
	}

	public void set_tipoNegocioId(int _tipoNegocioId) {
		this._tipoNegocioId = _tipoNegocioId;
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
