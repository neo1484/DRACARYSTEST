package Clases;

public class VentaProductoPOP 
{
	private int _id;
	private int _ventaPOPId;
	private int _ventaPOPIdServer;
	private int _productoPOPId;
	private int _cantidad;
	private int _clienteId;
	private boolean _syncro;
	private String _observacion;
	private int _motivoPopId;
	
	public VentaProductoPOP(){}

	public VentaProductoPOP(int _id,int _ventaPOPId,int _ventaPOPIdServer,int _productoPOPId, int _cantidad,int _clienteId,boolean _syncro,
								String _observacion,int _motivoPopId) 
	{
		super();
		this._id = _id;
		this._ventaPOPId = _ventaPOPId;
		this._ventaPOPIdServer = _ventaPOPIdServer;
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

	public int get_ventaPOPId() {
		return _ventaPOPId;
	}

	public void set_ventaPOPId(int _ventaPOPId) {
		this._ventaPOPId = _ventaPOPId;
	}

	public int get_ventaPOPIdServer() {
		return _ventaPOPIdServer;
	}

	public void set_ventaPOPIdServer(int _ventaPOPIdServer) {
		this._ventaPOPIdServer = _ventaPOPIdServer;
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
