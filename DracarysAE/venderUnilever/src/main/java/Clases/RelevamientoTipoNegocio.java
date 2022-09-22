package Clases;

public class RelevamientoTipoNegocio 
{
	private int _tipoNegocioId;
	private String _tipoNegocio;
	
	public RelevamientoTipoNegocio(int _tipoNegocioId, String _tipoNegocio) {
		super();
		this._tipoNegocioId = _tipoNegocioId;
		this._tipoNegocio = _tipoNegocio;
	}

	public int get_tipoNegocioId() {
		return _tipoNegocioId;
	}

	public void set_tipoNegocioId(int _tipoNegocioId) {
		this._tipoNegocioId = _tipoNegocioId;
	}

	public String get_tipoNegocio() {
		return _tipoNegocio;
	}

	public void set_tipoNegocio(String _tipoNegocio) {
		this._tipoNegocio = _tipoNegocio;
	}
	
	public String toString() { return this._tipoNegocio; }
}
