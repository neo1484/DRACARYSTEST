package Clases;

public class MotivoEliminacionMatch 
{
	private int _motivoId;
	private String _motivo;
	
	public MotivoEliminacionMatch(){}

	public MotivoEliminacionMatch(int _motivoId, String _motivo)
	{
		super();
		this._motivoId = _motivoId;
		this._motivo = _motivo;
	}

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
	}

	public String getMotivo() {
		return _motivo;
	}

	public void setMotivo(String motivo) {
		this._motivo = motivo;
	}
	
	public String toString()
	{
	    return this._motivo;
	}
}
