package Clases;

public class PreventaBonificacion 
{
	private int _preventaId;
	private int _bonificacionId;
	
	public PreventaBonificacion(){}
	
	public PreventaBonificacion(int _preventaId, int _bonificacionId) 
	{
		super();
		this._preventaId = _preventaId;
		this._bonificacionId = _bonificacionId;
	}

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}

	public int get_bonificacionId() {
		return _bonificacionId;
	}

	public void set_bonificacionId(int _bonificacionId) {
		this._bonificacionId = _bonificacionId;
	}
}
