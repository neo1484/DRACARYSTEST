package Clases;

public class LoginEmpleadoWSResult
{
    private int EmpleadoId;
    private String EmpleadoNombre;
    private String EmpleadoUsuario;
    private int Dia;
    private int Mes;
    private int Anio;
    private int Estado;
    private String Mensaje;
    private int AlmacenId;
    private int RutaId;
    private boolean CreditoPermitido;
    private boolean ModificarClienteApk;
    private boolean RutaCompleta;
    private String Token;

    public LoginEmpleadoWSResult(){}

    public LoginEmpleadoWSResult(int empleadoId, String empleadoNombre, String empleadoUsuario, int dia, int mes, int anio,
                                 int estado, String mensaje, int almacenId, int rutaId, boolean creditoPermitido,
                                 boolean modificarClienteApk, boolean rutaCompleta, String token) {
        super();
        EmpleadoId = empleadoId;
        EmpleadoNombre = empleadoNombre;
        EmpleadoUsuario = empleadoUsuario;
        Dia = dia;
        Mes = mes;
        Anio = anio;
        Estado = estado;
        Mensaje = mensaje;
        AlmacenId = almacenId;
        RutaId = rutaId;
        CreditoPermitido = creditoPermitido;
        ModificarClienteApk = modificarClienteApk;
        RutaCompleta = rutaCompleta;
        Token = token;
    }

    public int getEmpleadoId() {
        return EmpleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        EmpleadoId = empleadoId;
    }

    public String getEmpleadoNombre() {
        return EmpleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        EmpleadoNombre = empleadoNombre;
    }

    public String getEmpleadoUsuario() {
        return EmpleadoUsuario;
    }

    public void setEmpleadoUsuario(String empleadoUsuario) {
        EmpleadoUsuario = empleadoUsuario;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int anio) {
        Anio = anio;
    }

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public int getAlmacenId() {
        return AlmacenId;
    }

    public void setAlmacenId(int almacenId) {
        AlmacenId = almacenId;
    }

    public int getRutaId() {
        return RutaId;
    }

    public void setRutaId(int rutaId) {
        RutaId = rutaId;
    }

    public boolean isCreditoPermitido() {
        return CreditoPermitido;
    }

    public void setCreditoPermitido(boolean creditoPermitido) {
        CreditoPermitido = creditoPermitido;
    }

    public boolean isModificarClienteApk() {
        return ModificarClienteApk;
    }

    public void setModificarClienteApk(boolean modificarClienteApk) {
        ModificarClienteApk = modificarClienteApk;
    }

    public boolean isRutaCompleta() {
        return RutaCompleta;
    }

    public void setRutaCompleta(boolean rutaCompleta) {
        RutaCompleta = rutaCompleta;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
