package Clases;

public class PreventaCambio 
{
	private int _preventaId;
	
	public PreventaCambio(){}

	public PreventaCambio(int _preventaId)
	{
		super();
		this._preventaId = _preventaId;
	}

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}
}
