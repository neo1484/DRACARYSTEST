package Clases;

public class TipoCliente 
{
	private int _clienteTipoId;
	private String _descripcion;
	
	public TipoCliente(){}

	public TipoCliente(int _clienteTipoId, String _descripcion) {
		super();
		this._clienteTipoId = _clienteTipoId;
		this._descripcion = _descripcion;
	}

	public int get_clienteTipoId() {
		return _clienteTipoId;
	}

	public void set_clienteTipoId(int _clienteTipoId) {
		this._clienteTipoId = _clienteTipoId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
}
