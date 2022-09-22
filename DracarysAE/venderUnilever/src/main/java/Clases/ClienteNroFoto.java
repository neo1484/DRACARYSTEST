package Clases;

public class ClienteNroFoto 
{
	private int _clienteId;
	private int _numero;
	
	public ClienteNroFoto(){}

	public ClienteNroFoto(int _clienteId, int _numero) 
	{
		super();
		this._clienteId = _clienteId;
		this._numero = _numero;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_numero() {
		return _numero;
	}

	public void set_numero(int _numero) {
		this._numero = _numero;
	}
}
