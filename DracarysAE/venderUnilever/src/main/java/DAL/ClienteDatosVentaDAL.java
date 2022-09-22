package DAL;

import android.database.Cursor;
import com.detesim.venderunilever.Login;

import java.util.List;

import BLL.MyLogBLL;
import Clases.ClienteDatosVenta;
import Clases.ClienteDatosVentaWS;
import Utilidades.AdministradorDB;

public class ClienteDatosVentaDAL
{
	static AdministradorDB db = new AdministradorDB(Login.getContexto());
    private MyLogBLL myLog = new MyLogBLL();

    public boolean BorrarClienteDatosVentas()throws Exception
    {
        db.OpenDB();
        try
        {
            db.BorrarClienteDatosVentas();
            return true;
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar ClienteDatosVenta: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al borrar ClienteDatosVenta: "+localException.getMessage());
            }
            throw localException;
        }
        finally
        {
            db.CloseDB();
        }
    }

    public Cursor ObtenerClienteDatosVentas() throws Exception
    {
        db.OpenDB();
        try
        {
            return db.ObtenerClienteDatosVentas();
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener ClienteDatosVenta: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener ClienteDatosVenta: "+localException.getMessage());
            }
            throw localException;
        }
        finally
        {
            db.CloseDB();
        }
    }

    public Cursor ObtenerClienteDatosVentasPor(int clienteId) throws Exception
    {
        db.OpenDB();
        try
        {
            return db.ObtenerClienteDatosVentaPor(clienteId);
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener ClienteDatosVenta por clienteId: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al obtener ClienteDatosVenta por clienteId: "+localException.getMessage());
            }
            throw localException;
        }
        finally
        {
            db.CloseDB();
        }
    }

    public long InsertarClienteDatosVenta(List<ClienteDatosVentaWS> clienteDatosVenta) throws Exception
    {
        db.OpenDB();
        try
        {
            return db.InsertarClienteDatosVenta(clienteDatosVenta);
        }
        catch (Exception localException)
        {
            if(localException.getMessage().isEmpty())
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos de venta del cliente: vacio");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al insertar los datos de venta del cliente: "+localException.getMessage());
            }
            throw localException;
        }
        finally
        {
            db.CloseDB();
        }
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
    ) throws Exception
    {
        db.OpenDB();
        try
        {
            return db.ModificarClienteDatosVenta(
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
                myLog.InsertarLog("App",this.toString(),1,"Error al modificar ClienteDatosVenta por rowId: (vacio)");
            }
            else
            {
                myLog.InsertarLog("App",this.toString(),1,"Error al modificar ClienteDatosVenta por rowId: " + localException.getMessage());
            }
            throw localException;
        }
        finally
        {
            db.CloseDB();
        }
    }
}
