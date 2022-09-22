package Clases;

public class LogimEmpleadoWS
{
    private String _usuario;
    private String _password;
    private int _anio;
    private int _mes;
    private int _dia;
    private String _imei;
    private int _versionMayor;
    private int _versionMenor;

    public LogimEmpleadoWS(){}

    public LogimEmpleadoWS(String _usuario, String _password, int _anio, int _mes, int _dia, String _imei, int _versionMayor, int _versionMenor) {
        this._usuario = _usuario;
        this._password = _password;
        this._anio = _anio;
        this._mes = _mes;
        this._dia = _dia;
        this._imei = _imei;
        this._versionMayor = _versionMayor;
        this._versionMenor = _versionMenor;
    }

    public String get_usuario() {
        return _usuario;
    }

    public String get_password() {
        return _password;
    }

    public int get_anio() {
        return _anio;
    }

    public int get_mes() {
        return _mes;
    }

    public int get_dia() {
        return _dia;
    }

    public String get_imei() {
        return _imei;
    }

    public int get_versionMayor() {
        return _versionMayor;
    }

    public int get_versionMenor() {
        return _versionMenor;
    }
}
