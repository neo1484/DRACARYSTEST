package Clases;

public class RutaVendedor 
{
	private int _vendedorId;
	private int _rutaId;
	private int _diaId;
	private int _diaVisitaId;
	private String _descripcion;
	
	public RutaVendedor(){}

	public RutaVendedor(int _vendedorId, int _rutaId, int _diaId, int _diaVisitaId, String _descripcion) 
	{
		super();
		this._vendedorId = _vendedorId;
		this._rutaId = _rutaId;
		this._diaId = _diaId;
		this._diaVisitaId = _diaVisitaId;
		this._descripcion = _descripcion;
	}

	public int get_vendedorId() {
		return _vendedorId;
	}

	public void set_vendedorId(int _vendedorId) {
		this._vendedorId = _vendedorId;
	}

	public int get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(int _rutaId) {
		this._rutaId = _rutaId;
	}

	public int get_diaId() {
		return _diaId;
	}

	public void set_diaId(int _diaId) {
		this._diaId = _diaId;
	}

	public int get_diaVisitaId() {
		return _diaVisitaId;
	}

	public void set_diaVisitaId(int _diaVisitaId) {
		this._diaVisitaId = _diaVisitaId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
}
