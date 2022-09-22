package Clases;

public class ClienteDatosVentaJS {
	private int _vendedorId;
	private int _diaId;
	
	public ClienteDatosVentaJS(){}

	public ClienteDatosVentaJS(int _vendedorId, int _diaId) {
		super();
		this._vendedorId = _vendedorId;
		this._diaId = _diaId;
	}

	public int get_vendedorId() {
		return _vendedorId;
	}

	public void set_vendedorId(int _vendedorId) {
		this._vendedorId = _vendedorId;
	}

	public int get_diaId() {
		return _diaId;
	}

	public void set_diaId(int _diaId) {
		this._diaId = _diaId;
	}
}
