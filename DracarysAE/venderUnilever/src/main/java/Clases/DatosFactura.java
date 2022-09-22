package Clases;

public class DatosFactura 
{
	private String _nombreEmpresa;
	private String _nit;
	
	public DatosFactura(){}
	
	public DatosFactura(String _nombreEmpresa, String _nit) 
	{
		super();
		this._nombreEmpresa = _nombreEmpresa;
		this._nit = _nit;
	}

	public String get_nombreEmpresa() {
		return _nombreEmpresa;
	}

	public void set_nombreEmpresa(String _nombreEmpresa) {
		this._nombreEmpresa = _nombreEmpresa;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}
}
