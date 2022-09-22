package Clases;

public class EncuestaLista 
{
	private int _detalleId;
	private String _valor;
	
	public EncuestaLista(){}

	public EncuestaLista(int _detalleId, String _valor) 
	{
		super();
		this._detalleId = _detalleId;
		this._valor = _valor;
	}

	public int get_detalleId() {
		return _detalleId;
	}

	public void set_detalleId(int _detalleId) {
		this._detalleId = _detalleId;
	}

	public String get_valor() {
		return _valor;
	}

	public void set_valor(String _valor) {
		this._valor = _valor;
	}
	
	public String toString()
	{
		return this._valor;
	}
}
