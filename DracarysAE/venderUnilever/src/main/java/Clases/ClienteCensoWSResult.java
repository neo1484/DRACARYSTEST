package Clases;

public class ClienteCensoWSResult
{
    private int Id;
    private String Codigo;
    private String Referencia;
    private int TipoNegocioIdVender;
    private String TipoNegocio;
    private String Contacto;
    private double Latitud;
    private double Longitud;
    private String Nombres;
    private String Paterno;
    private int CreadorId;
    private double LatitudCreador;
    private double LongitudCreador;
    private int ZonaId;
    private int RutaId;
    private int DiaId;
    private int MercadoId;
    private int DiaCreacion;
    private int MesCreacion;
    private int AnioCreacion;
    private int Estado;
    private int ClienteId;
    private boolean Sincro;
    private int MotivoEliminacionId;

    public ClienteCensoWSResult(int id, String codigo, String referencia, int tipoNegocioIdVender, String tipoNegocio,
                                String contacto, double latitud, double longitud, String nombres, String paterno,
                                int creadorId, double latitudCreador, double longitudCreador, int zonaId, int rutaId,
                                int diaId, int mercadoId, int diaCreacion, int mesCreacion, int anioCreacion,
                                int estado, int clienteId, boolean sincro, int motivoEliminacionId) {
        Id = id;
        Codigo = codigo;
        Referencia = referencia;
        TipoNegocioIdVender = tipoNegocioIdVender;
        TipoNegocio = tipoNegocio;
        Contacto = contacto;
        Latitud = latitud;
        Longitud = longitud;
        Nombres = nombres;
        Paterno = paterno;
        CreadorId = creadorId;
        LatitudCreador = latitudCreador;
        LongitudCreador = longitudCreador;
        ZonaId = zonaId;
        RutaId = rutaId;
        DiaId = diaId;
        MercadoId = mercadoId;
        DiaCreacion = diaCreacion;
        MesCreacion = mesCreacion;
        AnioCreacion = anioCreacion;
        Estado = estado;
        ClienteId = clienteId;
        Sincro = sincro;
        MotivoEliminacionId = motivoEliminacionId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public int getTipoNegocioIdVender() {
        return TipoNegocioIdVender;
    }

    public void setTipoNegocioIdVender(int tipoNegocioIdVender) {
        TipoNegocioIdVender = tipoNegocioIdVender;
    }

    public String getTipoNegocio() {
        return TipoNegocio;
    }

    public void setTipoNegocio(String tipoNegocio) {
        TipoNegocio = tipoNegocio;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String contacto) {
        Contacto = contacto;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String nombres) {
        Nombres = nombres;
    }

    public String getPaterno() {
        return Paterno;
    }

    public void setPaterno(String paterno) {
        Paterno = paterno;
    }

    public int getCreadorId() {
        return CreadorId;
    }

    public void setCreadorId(int creadorId) {
        CreadorId = creadorId;
    }

    public double getLatitudCreador() {
        return LatitudCreador;
    }

    public void setLatitudCreador(double latitudCreador) {
        LatitudCreador = latitudCreador;
    }

    public double getLongitudCreador() {
        return LongitudCreador;
    }

    public void setLongitudCreador(double longitudCreador) {
        LongitudCreador = longitudCreador;
    }

    public int getZonaId() {
        return ZonaId;
    }

    public void setZonaId(int zonaId) {
        ZonaId = zonaId;
    }

    public int getRutaId() {
        return RutaId;
    }

    public void setRutaId(int rutaId) {
        RutaId = rutaId;
    }

    public int getDiaId() {
        return DiaId;
    }

    public void setDiaId(int diaId) {
        DiaId = diaId;
    }

    public int getMercadoId() {
        return MercadoId;
    }

    public void setMercadoId(int mercadoId) {
        MercadoId = mercadoId;
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

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int estado) {
        Estado = estado;
    }

    public int getClienteId() {
        return ClienteId;
    }

    public void setClienteId(int clienteId) {
        ClienteId = clienteId;
    }

    public boolean isSincro() {
        return Sincro;
    }

    public void setSincro(boolean sincro) {
        Sincro = sincro;
    }

    public int getMotivoEliminacionId() {
        return MotivoEliminacionId;
    }

    public void setMotivoEliminacionId(int motivoEliminacionId) {
        MotivoEliminacionId = motivoEliminacionId;
    }
}
