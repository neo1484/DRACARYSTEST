package Clases;

public class PreventaProductoCambio 
{
	private int _id;
	private int _preventaId;
	private int _preventaIdServer;
	private int _productoId;
	private int _cantidad;
	private int _clienteId;
	private boolean _sincro;
	private int _motivoCambioId;
	
	public PreventaProductoCambio(){}

	public PreventaProductoCambio(int _id,int _preventaId, int _preventaIdServer, int _productoId, 
			int _cantidad,int _clienteId, boolean _sincro, int _motivoCambioId) 
	{
		super();
		this._id = _id;
		this._preventaId = _preventaId;
		this._preventaIdServer = _preventaIdServer;
		this._productoId = _productoId;
		this._cantidad = _cantidad;
		this._clienteId = _clienteId;
		this._sincro = _sincro;
		this._motivoCambioId = _motivoCambioId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}

	public int get_preventaIdServer() {
		return _preventaIdServer;
	}

	public void set_preventaIdServer(int _preventaIdServer) {
		this._preventaIdServer = _preventaIdServer;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
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

	public boolean is_sincro() {
		return _sincro;
	}

	public void set_sincro(boolean _sincro) {
		this._sincro = _sincro;
	}

	public int get_motivoCambioId() {
		return _motivoCambioId;
	}

	public void set_motivoCambioId(int _motivoCambioId) {
		this._motivoCambioId = _motivoCambioId;
	}
}
