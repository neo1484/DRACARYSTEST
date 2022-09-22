package BLL;

import android.database.Cursor;

import Clases.ClienteDatosVenta;
import Clases.ClienteDatosVentaWS;
import DAL.ClienteDatosVentaDAL;
import java.util.ArrayList;
import java.util.List;

public class ClienteDatosVentaBLL
{
    private MyLogBLL myLog = new MyLogBLL();

    public boolean BorrarClienteDatosVentas() throws Exception
    {
        try
        {
            return new ClienteDatosVentaDAL().BorrarClienteDatosVentas();
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al borrar ClienteDatosVenta: (vacio)");
            }
            else
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al borrar ClienteDatosVenta: " + localException.getMessage());
            }
            throw localException;
        }
    }

    public ArrayList<ClienteDatosVenta> ObtenerClienteDatosVentas() throws Exception
    {
        Cursor localCursor;
        ArrayList<ClienteDatosVenta> listadoClienteDatosVenta = null;
        try
        {
            localCursor = new ClienteDatosVentaDAL().ObtenerClienteDatosVentas();
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al obtener ClienteDatosVenta: (vacio)");
            }
            else
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al obtener ClienteDatosVenta: " + localException.getMessage());
            }
            throw localException;
        }
        if(localCursor != null && localCursor.getCount() > 0)
        {
            listadoClienteDatosVenta = new ArrayList<ClienteDatosVenta>();

            do{
                ClienteDatosVenta theClienteDatosVenta = new ClienteDatosVenta(
                        localCursor.getInt(0),
                        localCursor.getInt(1),
                        localCursor.getFloat(2),
                        localCursor.getInt(3),
                        localCursor.getString(4),
                        localCursor.getFloat(5),
                        localCursor.getString(6),
                        localCursor.getString(7),
                        localCursor.getString(8),
                        localCursor.getString(9)
                );

                listadoClienteDatosVenta.add(theClienteDatosVenta);

            }while(localCursor.moveToNext());
        }
        return listadoClienteDatosVenta;
    }

    public ClienteDatosVenta ObtenerClienteDatosVentaPor(int clienteId) throws Exception
    {
        Cursor localCursor;
        ClienteDatosVenta clienteDatosVenta = null;

        try
        {
            localCursor = new ClienteDatosVentaDAL().ObtenerClienteDatosVentasPor(clienteId);
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al obtener el ClienteDatosVenta por clienteId: (vacio)");
            }
            else
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al obtener el ClienteDatosVenta por clienteId:" + localException.getMessage());
            }
            throw localException;
        }

        if(localCursor != null && localCursor.getCount() > 0)
        {
            clienteDatosVenta = new ClienteDatosVenta(
                    localCursor.getInt(0),
                    localCursor.getInt(1),
                    localCursor.getFloat(2),
                    localCursor.getInt(3),
                    localCursor.getString(4),
                    localCursor.getFloat(5),
                    localCursor.getString(6),
                    localCursor.getString(7),
                    localCursor.getString(8),
                    localCursor.getString(9)
            );
        }
        return clienteDatosVenta;
    }

    public long InsertarClienteDatosVenta(List<ClienteDatosVentaWS> clienteDatosVenta)
    {
        try
        {
            return new ClienteDatosVentaDAL().InsertarClienteDatosVenta(clienteDatosVenta);
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al insertar los datos de venta del cliente: (vacio)");
            }
            else
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al insertar los datos de venta del cliente: " + localException.getMessage());
            }
        }
        return 0;
    }

    public long ModificarClienteDatosVenta(
            int id,
            int clienteId,
            float promedioSemanal,
            int intensidadCompra,
            String ultimaCompraFecha,
            float ultimaCompraMonto,
            String escadoPreventaFecha,
            String escadoPreventa,
            String escadoVentaFecha,
            String escadoVenta
    )
    {
        try
        {
            return new ClienteDatosVentaDAL().ModificarClienteDatosVenta(
                    id,
                    clienteId,
                    promedioSemanal,
                    intensidadCompra,
                    ultimaCompraFecha,
                    ultimaCompraMonto,
                    escadoPreventaFecha,
                    escadoPreventa,
                    escadoVentaFecha,
                    escadoVenta
            );
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al modificar ClienteDatosVenta por rowId: (vacio)");
            }
            else
            {
                myLog.InsertarLog("App", this.toString(),1,"Error al modificar ClienteDatosVenta por rowId: " + localException.getMessage());
            }
        }
        return 0;
    }

}
