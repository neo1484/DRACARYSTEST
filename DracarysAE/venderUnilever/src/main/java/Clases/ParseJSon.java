package Clases;

public class ParseJSon 
{
	public ParseJSon(){}
	
	public String GenerarPreventaJSString(PreventaJS preventaJS)
    {
        String json = "";
        json += "{";
        json += "'empleadoId':" + preventaJS.get_empleadoId() +",";
        json += "'clienteId':" + preventaJS.get_clienteId() +",";
        json += "'monto':" + preventaJS.get_monto() +",";
        json += "'descuento':" + preventaJS.get_descuento() +",";
        json += "'montoFinal':" + preventaJS.get_montoFinal() +",";
        json += "'tipoPagoId':" + preventaJS.get_tipoPagoId() +",";
        json += "'latitud':" + preventaJS.get_latitud() +",";
        json += "'longitud':" + preventaJS.get_longitud() +",";
        json += "'dia':" + preventaJS.get_dia() +",";
        json += "'mes':" + preventaJS.get_mes() +",";
        json += "'anio':" + preventaJS.get_anio() +",";
        json += "'hora':" + preventaJS.get_hora() +",";
        json += "'minuto':" + preventaJS.get_minuto() +",";
        json += "'nombreFactura':'" + preventaJS.get_nombreFactura() +"',";
        json += "'nit':'" + preventaJS.get_nit() +"',";
        json += "'nitNuevo':" + preventaJS.is_nitNuevo() +",";
        json += "'almacenId':" + preventaJS.get_almacenId() +",";
        json += "'rutaId':" + preventaJS.get_rutaId() +",";
        json += "'diaId':" + preventaJS.get_dia() +",";
        json += "'nroPreVenta':" + preventaJS.get_nroPreVenta() +",";
        json += "'observacion':'" + preventaJS.get_observacion() +"',";
        json += "'aplicaBonificacion':" + preventaJS.is_aplicarBonificacion() +",";
        json += "'diaEntrega':" + preventaJS.get_diaEntrega() +",";
        json += "'mesEntrega':" + preventaJS.get_mesEntrega() +",";
        json += "'anioEntrega':" + preventaJS.get_anioEntrega() +",";
        json += "'dosificacionId':" + preventaJS.get_dosificacionId() +",";
        json += "'tipoNit':'" + preventaJS.get_tipoNit() +"',";
        json += "'ruta':'" + preventaJS.get_ruta() +"',";
        json += "'tipoVisita':'" + preventaJS.get_tipoVisita() +"',";
        json += "'zonaVentaId':" + preventaJS.get_zonaVentaId() +",";
        json += "'prontoPagoId':" + preventaJS.get_prontoPagoId() +",";
        json += "'descuentoCanal':" + preventaJS.get_descuentoCanal() +",";
        json += "'descuentoAjuste':" + preventaJS.get_descuentoAjuste() +",";
        json += "'descuentoProntoPago':" + preventaJS.get_descuentoProntoPago() +",";
        json += "'descuentoObjetivo':" + preventaJS.get_descuentoObjetivo() +",";
        json += "'formaPagoId':" + preventaJS.get_formaPagoId() +",";
        json += "'codTransferencia':'" + preventaJS.get_codTransferencia() +"',";
        json += "'fromApk':" + preventaJS.is_fromApk() +",";
        json += "'preVentaProductos':[";

        int count = 0;
        int countTotal = preventaJS.get_listaPreventaProductoTempJS().size();

        for(PreventaProductoTempJS item : preventaJS.get_listaPreventaProductoTempJS())
        {
            json += "{'productoId':" + item.get_productoId() + ",";
            json += "'promocionId':" + item.get_promocionId() +",";
            json += "'cantidad':" + item.get_cantidad() +",";
            json += "'cantidadPaquete':" + item.get_cantidadPaquete() +",";
            json += "'monto':" + item.get_monto() +",";
            json += "'descuento':" + item.get_descuento() +",";
            json += "'montoFinal':" + item.get_montoFinal() +",";
            json += "'costoId':" + item.get_costoId() +",";
            json += "'precioId':" + item.get_precioId() +",";
            json += "'descuentoCanal':" + item.get_descuentoCanal() +",";
            json += "'descuentoAjuste':" + item.get_descuentoAjuste() +",";
            json += "'canalRutaPrecioId':" + item.get_canalRutaPrecioId() +",";
            json += "'descuentoProntoPago':" + item.get_descuentoProntoPago() +"}";
            
            count++;
            if (count < countTotal)
            {
                json += ",";
            }
        }

        json += "]}";

        return json;
    }
	
	public String GenerarDatosVentaClienteJSString(ClienteDatosVentaJS clienteDatosVentaJS)
    {
        String json = "";
        json += "{";
        json += "'_vendedorId':" + clienteDatosVentaJS.get_vendedorId() +",";
        json += "'_diaId':" + clienteDatosVentaJS.get_diaId() +"}";
        
        return json;
    }
	
}
