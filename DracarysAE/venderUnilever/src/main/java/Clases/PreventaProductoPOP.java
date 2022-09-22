package Clases;

public class PreventaProductoPOP 
{
	private int _id;
	private int _preventaPOPId;
	private int _preventaPOPIdServer;
	private int _productoPOPId;
	private int _cantidad;
	private int _clienteId;
	private boolean _syncro;
	private String _observacion;
	private int _motivoPopId;
	
	public PreventaProductoPOP(){}

	public PreventaProductoPOP(int _id,int _preventaPOPId,int _preventaPOPIdServer,int _productoPOPId, int _cantidad,int _clienteId,boolean _syncro,
								String _observacion,int _motivoPopId) 
	{
		super();
		this._id = _id;
		this._preventaPOPId = _preventaPOPId;
		this._preventaPOPIdServer = _preventaPOPIdServer;
		this._productoPOPId = _productoPOPId;
		this._cantidad = _cantidad;
		this._clienteId = _clienteId;
		this._syncro = _syncro;
		this._observacion = _observacion;
		this._motivoPopId = _motivoPopId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_preventaPOPId() {
		return _preventaPOPId;
	}

	public void set_preventaPOPId(int _preventaPOPId) {
		this._preventaPOPId = _preventaPOPId;
	}

	public int get_preventaPOPIdServer() {
		return _preventaPOPIdServer;
	}

	public void set_preventaPOPIdServer(int _preventaPOPIdServer) {
		this._preventaPOPIdServer = _preventaPOPIdServer;
	}
	
	public int get_productoPOPId() {
		return _productoPOPId;
	}

	public void set_productoPOPId(int _productoPOPId) {
		this._productoPOPId = _productoPOPId;
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

	public int get_motivoPopId() {
		return _motivoPopId;
	}

	public void set_motivoPopId(int _motivoPopId) {
		this._motivoPopId = _motivoPopId;
	}
}
