package Clases;

public class VentaBonificacion 
{
	public int _ventaId;
	public int _bonificacionId;
	
	public VentaBonificacion(){}

	public VentaBonificacion(int _ventaId, int _bonificacionId) 
	{
		super();
		this._ventaId = _ventaId;
		this._bonificacionId = _bonificacionId;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
	}

	public int get_bonificacionId() {
		return _bonificacionId;
	}

	public void set_bonificacionId(int _bonificacionId) {
		this._bonificacionId = _bonificacionId;
	}
}
