package Clases;

public class DosificacionWSResult
{
    private int DosificacionId;
    private int DiaCreacion;
    private int MesCreacion;
    private int AnioCreacion;
    private String CodigoAutorizacion;
    private int Cantidad;
    private String NumeroInicial;
    private String NumeroFinal;
    private int DiaLimiteEmision;
    private int MesLimiteEmision;
    private int AnioLimiteEmision;
    private String FacturaTipoId;
    private int AlmacenId;
    private boolean Activa;
    private boolean Bloqueada;
    private String LlaveDosificacion;
    private int CantidadDisponible;
    private String Ley;
    private String Sfc;
    private String Sucursal;
    private String Mensaje1;
    private String Mensaje2;
    private String Actividad;

    public DosificacionWSResult(int dosificacionId, int diaCreacion, int mesCreacion, int anioCreacion,
                                String codigoAutorizacion, int cantidad, String numeroInicial,
                                String numeroFinal, int diaLimiteEmision, int mesLimiteEmision,
                                int anioLimiteEmision, String facturaTipoId, int almacenId,
                                boolean activa, boolean bloqueada, String llaveDosificacion,
                                int cantidadDisponible, String ley, String sfc, String sucursal,
                                String mensaje1, String mensaje2, String actividad) {
        DosificacionId = dosificacionId;
        DiaCreacion = diaCreacion;
        MesCreacion = mesCreacion;
        AnioCreacion = anioCreacion;
        CodigoAutorizacion = codigoAutorizacion;
        Cantidad = cantidad;
        NumeroInicial = numeroInicial;
        NumeroFinal = numeroFinal;
        DiaLimiteEmision = diaLimiteEmision;
        MesLimiteEmision = mesLimiteEmision;
        AnioLimiteEmision = anioLimiteEmision;
        FacturaTipoId = facturaTipoId;
        AlmacenId = almacenId;
        Activa = activa;
        Bloqueada = bloqueada;
        LlaveDosificacion = llaveDosificacion;
        CantidadDisponible = cantidadDisponible;
        Ley = ley;
        Sfc = sfc;
        Sucursal = sucursal;
        Mensaje1 = mensaje1;
        Mensaje2 = mensaje2;
        Actividad = actividad;
    }

    public int getDosificacionId() {
        return DosificacionId;
    }

    public void setDosificacionId(int dosificacionId) {
        DosificacionId = dosificacionId;
    }

    public int getDiaCreacion() {
        return DiaCreacion;
    }

    public void setDiaCreacion(int diaCreacion) {
        DiaCreacion = diaCreacion;
    }

    public int getMesCreacion() {
        return MesCreacion;
    }

    public void setMesCreacion(int mesCreacion) {
        MesCreacion = mesCreacion;
    }

    public int getAnioCreacion() {
        return AnioCreacion;
    }

    public void setAnioCreacion(int anioCreacion) {
        AnioCreacion = anioCreacion;
    }

    public String getCodigoAutorizacion() {
        return CodigoAutorizacion;
    }

    public void setCodigoAutorizacion(String codigoAutorizacion) {
        CodigoAutorizacion = codigoAutorizacion;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int cantidad) {
        Cantidad = cantidad;
    }

    public String getNumeroInicial() {
        return NumeroInicial;
    }

    public void setNumeroInicial(String numeroInicial) {
        NumeroInicial = numeroInicial;
    }

    public String getNumeroFinal() {
        return NumeroFinal;
    }

    public void setNumeroFinal(String numeroFinal) {
        NumeroFinal = numeroFinal;
    }

    public int getDiaLimiteEmision() {
        return DiaLimiteEmision;
    }

    public void setDiaLimiteEmision(int diaLimiteEmision) {
        DiaLimiteEmision = diaLimiteEmision;
    }

    public int getMesLimiteEmision() {
        return MesLimiteEmision;
    }

    public void setMesLimiteEmision(int mesLimiteEmision) {
        MesLimiteEmision = mesLimiteEmision;
    }

    public int getAnioLimiteEmision() {
        return AnioLimiteEmision;
    }

    public void setAnioLimiteEmision(int anioLimiteEmision) {
        AnioLimiteEmision = anioLimiteEmision;
    }

    public String getFacturaTipoId() {
        return FacturaTipoId;
    }

    public void setFacturaTipoId(String facturaTipoId) {
        FacturaTipoId = facturaTipoId;
    }

    public int getAlmacenId() {
        return AlmacenId;
    }

    public void setAlmacenId(int almacenId) {
        AlmacenId = almacenId;
    }

    public boolean isActiva() {
        return Activa;
    }

    public void setActiva(boolean activa) {
        Activa = activa;
    }

    public boolean isBloqueada() {
        return Bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
        Bloqueada = bloqueada;
    }

    public String getLlaveDosificacion() {
        return LlaveDosificacion;
    }

    public void setLlaveDosificacion(String llaveDosificacion) {
        LlaveDosificacion = llaveDosificacion;
    }

    public int getCantidadDisponible() {
        return CantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        CantidadDisponible = cantidadDisponible;
    }

    public String getLey() {
        return Ley;
    }

    public void setLey(String ley) {
        Ley = ley;
    }

    public String getSfc() {
        return Sfc;
    }

    public void setSfc(String sfc) {
        Sfc = sfc;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getMensaje1() {
        return Mensaje1;
    }

    public void setMensaje1(String mensaje1) {
        Mensaje1 = mensaje1;
    }

    public String getMensaje2() {
        return Mensaje2;
    }

    public void setMensaje2(String mensaje2) {
        Mensaje2 = mensaje2;
    }

    public String getActividad() {
        return Actividad;
    }

    public void setActividad(String actividad) {
        Actividad = actividad;
    }
}
