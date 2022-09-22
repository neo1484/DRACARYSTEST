package Clases;

public class CanalRuta 
{
	private int _canalRutaId;
	private String _descripcion;
	
	public CanalRuta(){}
	
	public CanalRuta(int _canalRutaId, String _descripcion) 
	{
		super();
		this._canalRutaId = _canalRutaId;
		this._descripcion = _descripcion;
	}

	public int get_canalRutaId() {
		return _canalRutaId;
	}

	public void set_canalRutaId(int _canalRutaId) {
		this._canalRutaId = _canalRutaId;
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
