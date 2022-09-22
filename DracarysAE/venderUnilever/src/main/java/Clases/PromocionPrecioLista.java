package Clases;

public class PromocionPrecioLista 
{
	private int _promocionId;
	private int _precioListaId;

	public PromocionPrecioLista(){}

	public PromocionPrecioLista(int _promocionId,int _precioListaId) 
	{
		super();
		this._promocionId = _promocionId;
		this._precioListaId = _precioListaId;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public int get_precioListaId() {
		return _precioListaId;
	}

	public void set_precioListaId(int _precioListaId) {
		this._precioListaId = _precioListaId;
	}
}
