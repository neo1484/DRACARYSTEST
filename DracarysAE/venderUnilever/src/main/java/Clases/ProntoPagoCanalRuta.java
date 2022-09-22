package Clases;

public class ProntoPagoCanalRuta 
{
	private int _prontoPagoId;
	private int _canalRutaId;
	
	public ProntoPagoCanalRuta() {}
	
	public ProntoPagoCanalRuta(int _prontoPagoId, int _canalRutaId) 
	{
		super();
		this._prontoPagoId = _prontoPagoId;
		this._canalRutaId = _canalRutaId;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public int get_canalRutaId() {
		return _canalRutaId;
	}

	public void set_canalRutaId(int _canalRutaId) {
		this._canalRutaId = _canalRutaId;
	}
	
	
}
