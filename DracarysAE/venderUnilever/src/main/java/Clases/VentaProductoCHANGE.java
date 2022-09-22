package Clases;

public class VentaProductoCHANGE 
{
	private int _id;
	private int _ventaCHANGEId;
	private int _ventaCHANGEIdServer;
	private int _productoCHANGEId;
	private int _cantidad;
	private int _clienteId;
	private boolean _syncro;
	private String _observacion;
	private int _motivoCHANGEId;
	
	public VentaProductoCHANGE(){}

	public VentaProductoCHANGE(int _id, int _ventaCHANGEId, int _ventaCHANGEIdServer, int _productoCHANGEId,
			int _cantidad, int _clienteId, boolean _syncro, String _observacion, int _motivoCHANGEId) 
	{
		super();
		this._id = _id;
		this._ventaCHANGEId = _ventaCHANGEId;
		this._ventaCHANGEIdServer = _ventaCHANGEIdServer;
		this._productoCHANGEId = _productoCHANGEId;
		this._cantidad = _cantidad;
		this._clienteId = _clienteId;
		this._syncro = _syncro;
		this._observacion = _observacion;
		this._motivoCHANGEId = _motivoCHANGEId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_ventaCHANGEId() {
		return _ventaCHANGEId;
	}

	public void set_ventaCHANGEId(int _ventaCHANGEId) {
		this._ventaCHANGEId = _ventaCHANGEId;
	}

	public int get_ventaCHANGEIdServer() {
		return _ventaCHANGEIdServer;
	}

	public void set_ventaCHANGEIdServer(int _ventaCHANGEIdServer) {
		this._ventaCHANGEIdServer = _ventaCHANGEIdServer;
	}

	public int get_productoCHANGEId() {
		return _productoCHANGEId;
	}

	public void set_productoCHANGEId(int _productoCHANGEId) {
		this._productoCHANGEId = _productoCHANGEId;
	}

	public int get_cantidad() {
		return _cantidad;
	}

	public void set_cantidad(int _cantidad) {
		this._cantidad = _cantidad;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public boolean is_syncro() {
		return _syncro;
	}

	public void set_syncro(boolean _syncro) {
		this._syncro = _syncro;
	}

	public String get_observacion() {
		return _observacion;
	}

	public void set_observacion(String _observacion) {
		this._observacion = _observacion;
	}

	public int get_motivoCHANGEId() {
		return _motivoCHANGEId;
	}

	public void set_motivoCHANGEId(int _motivoCHANGEId) {
		this._motivoCHANGEId = _motivoCHANGEId;
	}
}
