package Clases;

public class ClienteFoto 
{
	private int _id;
	private int _clienteIdAndroid;
	private int _clienteIdServer;
	private byte[] _foto;
	private boolean _estado;
	private int _fotoCategoriaId;
	private int _fotoIdServer;
	
	public ClienteFoto(){}

	public ClienteFoto(int _id, int _clienteIdAndroid, int _clienteIdServer,
			byte[] _foto, boolean _estado,int _fotoCategoriaId,int _fotoIdServer) {
		super();
		this._id = _id;
		this._clienteIdAndroid = _clienteIdAndroid;
		this._clienteIdServer = _clienteIdServer;
		this._foto = _foto;
		this._estado = _estado;
		this._fotoCategoriaId = _fotoCategoriaId;
		this._fotoIdServer = _fotoIdServer;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_clienteIdAndroid() {
		return _clienteIdAndroid;
	}

	public void set_clienteIdAndroid(int _clienteIdAndroid) {
		this._clienteIdAndroid = _clienteIdAndroid;
	}

	public int get_clienteIdServer() {
		return _clienteIdServer;
	}

	public void set_clienteIdServer(int _clienteIdServer) {
		this._clienteIdServer = _clienteIdServer;
	}

	public byte[] get_foto() {
		return _foto;
	}

	public void set_foto(byte[] _foto) {
		this._foto = _foto;
	}

	public boolean is_estado() {
		return _estado;
	}

	public void set_estado(boolean _estado) {
		this._estado = _estado;
	}

	public int get_fotoCategoriaId() {
		return _fotoCategoriaId;
	}

	public void set_fotoCategoriaId(int _fotoCategoriaId) {
		this._fotoCategoriaId = _fotoCategoriaId;
	}

	public int get_fotoIdServer() {
		return _fotoIdServer;
	}

	public void set_fotoIdServer(int _fotoIdServer) {
		this._fotoIdServer = _fotoIdServer;
	}
}
