package Clases;

public class JSonParser
{
    public String GenerarLoginJson(LogimEmpleadoWS loginEmpleadoApk)
    {
        String json = "";
        json += "{";
        json += "'_usuario':'" + loginEmpleadoApk.get_usuario() +"',";
        json += "'_password':'" + loginEmpleadoApk.get_password() +"',";
        json += "'_anio':" + loginEmpleadoApk.get_anio() +",";
        json += "'_mes':" + loginEmpleadoApk.get_mes() +",";
        json += "'_dia':" + loginEmpleadoApk.get_dia() +",";
        json += "'_imei':'" + loginEmpleadoApk.get_imei() +"',";
        json += "'_versionMayor':" + loginEmpleadoApk.get_versionMayor() +",";
        json += "'_versionMenor':" + loginEmpleadoApk.get_versionMenor() +"}";
        return json;
    }

    public String GenerarVendedorDiaJson(VendedorDiaWS vendedorDiaWS)
    {
        String json = "";
        json += "{";
        json += "'_vendedorId':" + vendedorDiaWS.get_vendedorId() +",";
        json += "'_diaId':" + vendedorDiaWS.get_diaId() +",";
        json += "'_username':'" + vendedorDiaWS.get_username() +"',";
        json += "'_token':'" + vendedorDiaWS.get_token() +"'}";
        return json;
    }

    public String GenerarVendedorFechaJson(VendedorFechaWS vendedorFechaWS)
    {
        String json = "";
        json += "{";
        json += "'_vendedorId':" + vendedorFechaWS.get_vendedorId() +",";
        json += "'_dia':" + vendedorFechaWS.get_dia() +",";
        json += "'_mes':" + vendedorFechaWS.get_mes() +",";
        json += "'_anio':" + vendedorFechaWS.get_anio() +",";
        json += "'_username':'" + vendedorFechaWS.get_username() +"',";
        json += "'_token':'" + vendedorFechaWS.get_token() +"'}";
        return json;
    }

    public String GenerarSingleFechaJson(SingleFechaWS singleFechaWS)
    {
        String json = "";
        json += "{";
        json += "'_dia':" + singleFechaWS.get_dia() +",";
        json += "'_mes':" + singleFechaWS.get_mes() +",";
        json += "'_anio':" + singleFechaWS.get_anio() +",";
        json += "'_username':'" + singleFechaWS.get_username() +"',";
        json += "'_token':'" + singleFechaWS.get_token() +"'}";
        return json;
    }

    public String GenerarVendedorDiaRutaJson(VendedorDiaRutaWS vendedorDiaRutaWS)
    {
        String json = "";
        json += "{";
        json += "'_vendedorId':" + vendedorDiaRutaWS.get_vendedorId() +",";
        json += "'_diaId':" + vendedorDiaRutaWS.get_diaId() +",";
        json += "'_rutaId':" + vendedorDiaRutaWS.get_rutaId() +",";
        json += "'_username':'" + vendedorDiaRutaWS.get_username() +"',";
        json += "'_token':'" + vendedorDiaRutaWS.get_token() +"'}";
        return json;
    }

    public String GenerarSingleIdJson(SingleId singleId)
    {
        String json = "";
        json += "{";
        json += "'_id':" + singleId.get_id() +",";
        json += "'_username':'" + singleId.get_username() +"',";
        json += "'_token':'" + singleId.get_token() +"'}";
        return json;
    }

    public String GenerarDistribuidorAlmacenFechaJson(DistribuidorAlmacenFechaWS distribuidorAlmacenFechaWS)
    {
        String json = "";
        json += "{";
        json += "'_distribuidorId':" + distribuidorAlmacenFechaWS.get_distribuidorId() +",";
        json += "'_almacenId':" + distribuidorAlmacenFechaWS.get_almacenId() +",";
        json += "'_dia':" + distribuidorAlmacenFechaWS.get_dia() +",";
        json += "'_mes':" + distribuidorAlmacenFechaWS.get_mes() +",";
        json += "'_anio':" + distribuidorAlmacenFechaWS.get_anio() +",";
        json += "'_username':'" + distribuidorAlmacenFechaWS.get_username() +"',";
        json += "'_token':'" + distribuidorAlmacenFechaWS.get_token() +"'}";
        return json;
    }

    public String GenerarAplicacionIncentivo(int preventaId, float incentivo, String incentivosId)
    {
        String json = "";
        json += "{";
        json += "'_preventaId':" + preventaId +",";
        json += "'_descuentoIncentivo':" + incentivo +",";
        json += "'_incentivos':'" + incentivosId +"'}";
        return json;
    }
}
