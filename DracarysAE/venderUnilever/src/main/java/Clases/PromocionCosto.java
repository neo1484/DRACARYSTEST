package Clases;

public class PromocionCosto 
{
	private int _promocionId;
	private int _costoId;
	
	public PromocionCosto(){}

	public PromocionCosto(int _promocionId, int _costoId) 
	{
		super();
		this._promocionId = _promocionId;
		this._costoId = _costoId;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}
}
