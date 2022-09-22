package Clases;

public class ClienteEncuestado 
{
	private int _clienteId;
	private int _encuestaId;
	
	public ClienteEncuestado(){}
	
	public ClienteEncuestado(int _clienteId, int _encuestaId)
	{
		this._clienteId = _clienteId;
		this._encuestaId = _encuestaId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_encuestaId() {
		return _encuestaId;
	}

	public void set_encuestaId(int _encuestaId) {
		this._encuestaId = _encuestaId;
	}
}
