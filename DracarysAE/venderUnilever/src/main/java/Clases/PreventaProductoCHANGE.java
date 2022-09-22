package Clases;

public class PreventaProductoCHANGE 
{
	private int _id;
	private int _preventaCHANGEId;
	private int _preventaCHANGEIdServer;
	private int _productoCHANGEId;
	private int _cantidad;
	private int _clienteId;
	private boolean _syncro;
	private String _observacion;
	private int _motivoCHANGEId;
	
	public PreventaProductoCHANGE(){}

	public PreventaProductoCHANGE(int _id, int _preventaCHANGEId, int _preventaCHANGEIdServer, int _productoCHANGEId,
			int _cantidad, int _clienteId, boolean _syncro, String _observacion, int _motivoCHANGEId) 
	{
		super();
		this._id = _id;
		this._preventaCHANGEId = _preventaCHANGEId;
		this._preventaCHANGEIdServer = _preventaCHANGEIdServer;
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

	public int get_preventaCHANGEId() {
		return _preventaCHANGEId;
	}

	public void set_preventaCHANGEId(int _preventaCHANGEId) {
		this._preventaCHANGEId = _preventaCHANGEId;
	}

	public int get_preventaCHANGEIdServer() {
		return _preventaCHANGEIdServer;
	}

	public void set_preventaCHANGEIdServer(int _preventaCHANGEIdServer) {
		this._preventaCHANGEIdServer = _preventaCHANGEIdServer;
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
