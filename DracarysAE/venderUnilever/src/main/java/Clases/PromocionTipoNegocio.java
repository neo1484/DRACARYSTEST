package Clases;

public class PromocionTipoNegocio 
{
	private int _registroId;
	private int _promocionId;
	private int _tipoNegocioId;
	
	public PromocionTipoNegocio(){}

	public PromocionTipoNegocio(int _registroId, int _promocionId, int _tipoNegocioId) 
	{
		super();
		this._registroId = _registroId;
		this._promocionId = _promocionId;
		this._tipoNegocioId = _tipoNegocioId;
	}

	public int get_registroId() {
		return _registroId;
	}

	public void set_registroId(int _registroId) {
		this._registroId = _registroId;
	}

	public int get_promocionId() {
		return _promocionId;
	}

	public void set_promocionId(int _promocionId) {
		this._promocionId = _promocionId;
	}

	public int get_tipoNegocioId() {
		return _tipoNegocioId;
	}

	public void set_tipoNegocioId(int _tipoNegocioId) {
		this._tipoNegocioId = _tipoNegocioId;
	}
}
