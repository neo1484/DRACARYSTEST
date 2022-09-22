package com.detesim.venderunilever;

import java.util.ArrayList;
import java.util.Locale;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import BLL.ClienteBLL;
import BLL.ClientePreventaBLL;
import BLL.MyLogBLL;
import Clases.Cliente;
import Clases.ClientePreventa;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Vendedoractualizacioncliente extends Activity implements OnClickListener
{
	LinearLayout llVendedorActualizacionCliente;
	Utilidades theUtilidades = new Utilidades();
	String NAMESPACE = theUtilidades.get_NAMESPACE();
	String URL = theUtilidades.get_URL();
	MyLogBLL myLog = new MyLogBLL();
	LoginEmpleado loginEmpleado;
	
	int clienteId;
	String nombres;
	String paterno;
	String materno;
	String casada;
	String direccion;
	String referencia;
	ClientePreventa clientePreventa;
	
	EditText etNombres;
	EditText etPaterno;
	EditText etMaterno;
	EditText etCasada;
	EditText etDireccion;
	EditText etReferencia;
	Button btnActualizarCliente;
	ProgressDialog pdEsperarActualizarcliente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vendedoractualizacioncliente);
		
		llVendedorActualizacionCliente = (LinearLayout)findViewById(R.id.VendedorActualizacionClienteLinearLayout);
		etNombres = (EditText)findViewById(R.id.etActualizacionClienteNombres);
		etPaterno = (EditText)findViewById(R.id.etActualizacionClientePaterno);
		etMaterno = (EditText)findViewById(R.id.etActualizacionClienteMaterno);
		etCasada = (EditText)findViewById(R.id.etActualizacionClienteCasada);
		etDireccion = (EditText)findViewById(R.id.etActualizacionClienteDireccion);
		etReferencia = (EditText)findViewById(R.id.etActualizacionClienteReferencia);
		btnActualizarCliente = (Button)findViewById(R.id.btnActualizacionClienteActualizar);
		btnActualizarCliente.setOnClickListener(this);
		
		llVendedorActualizacionCliente.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
		clienteId = 0;
	    Bundle localBundle = getIntent().getExtras();
	    clienteId = localBundle.getInt("ClienteId");
	    
	    if(clienteId == 0)
	    {
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el id del cliente.", 1);
	    	return;
	    }
		
		loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	        if (localException.getMessage().isEmpty()) 
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al obtener loginEmpleado vendedor: vacio");
          	} 
	        else 
	        {
	            myLog.InsertarLog("App", toString(), 1, "Error al obtener loginEmpleado vendedor: " + localException.getMessage());
	        }
	    }
	        
	    if (loginEmpleado == null)
        {
	    	theUtilidades.MostrarMensaje(getApplicationContext(),"El usuario no se logeo correctamente.", 1);
	    	return;
        }
	    else
	    {
	    	CargarDatosClientePreventa();
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnActualizacionClienteActualizar:
			if(ValidarIngresoDatos())
			{
				ActualizarCliente();
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(),"El nombre, paterno, direccion y referencia son requeridos.", 1);
				return;				
			}
			break;
		}
	}
	
	private void CargarDatosClientePreventa()
	{
		clientePreventa = null;
		try
		{
			clientePreventa = new ClientePreventaBLL().ObtenerClientePreventaPor(clienteId);
		}
		catch(Exception localException)
		{
			if (localException.getMessage().isEmpty()) 
	        {
	        	myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del clientePreventa: vacio");
          	} 
	        else 
	        {
	            myLog.InsertarLog("App", toString(), 1, "Error al obtener los detalles del clientePreventa: " + localException.getMessage());
	        }
		}
		
		if(clientePreventa == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener los detalles del cliente.", 1);
			return;
		}
		else
		{
			etNombres.setText(clientePreventa.get_nombres().equals("anyType{}")?"Sin nombres":clientePreventa.get_nombres());
			etPaterno.setText(clientePreventa.get_paterno().equals("anyType{}")?"Sin paterno":clientePreventa.get_paterno());
			etMaterno.setText(clientePreventa.get_materno().equals("anyType{}")?"":clientePreventa.get_materno());
			etCasada.setText(clientePreventa.get_apellidoCasada().equals("anyType{}")?"":clientePreventa.get_apellidoCasada());
			etDireccion.setText(clientePreventa.get_direccion().equals("anyType{}")?"":clientePreventa.get_direccion());
			etReferencia.setText(clientePreventa.get_referencia().equals("anyType{}")?"":clientePreventa.get_referencia());
		}
	}

	private boolean ValidarIngresoDatos()
	{
		boolean validado = false;
		if(etNombres.getText() != null && etNombres.getText().toString().toLowerCase(Locale.US).equals("sin nombres"))
		{
			validado = false;
		}
		else if(etPaterno.getText() != null && etPaterno.getText().toString().toLowerCase(Locale.US).equals("sin paterno"))
		{
			validado = false;
		}
		else if(etDireccion.getText() == null || etDireccion.getText().toString().isEmpty())
		{
			validado = false;
		}
		else if(etReferencia.getText() == null || etReferencia.getText().toString().isEmpty())
		{
			validado = false;
		}
		else
		{
			validado = true;
		}
		return validado;
	}
	
	private boolean ActualizarClienteBD()
	{
		boolean modificado = false;
		
		nombres = "sin nombres";
	    if(etNombres.getText().toString().length() > 0)
	    {
	    	nombres = etNombres.getText().toString();	    	
	    }
	    
	    paterno = "sin paterno";
	    if(etPaterno.getText().toString().length() > 0)
	    {
	    	paterno = etPaterno.getText().toString();
	    }
	    
	    materno = "";
	    if(etMaterno.getText().toString().length() > 0)
	    {
	    	materno = etMaterno.getText().toString();
	    }
	    
	    casada = "";
	    if(etCasada.getText().toString().length() > 0)
	    {
	    	casada = etCasada.getText().toString();
	    }
	    
	    direccion = "";
	    if(etDireccion.getText().toString().length() > 0)
	    {
	    	direccion = etDireccion.getText().toString();
	    }
	    
	    referencia = "";
	    if(etReferencia.getText().toString().length() > 0)
	    {
	    	referencia = etReferencia.getText().toString();
	    }
	    
	    //Verificamos que el menu Censista haya sincronizado su informacion y descargado sus clientes
		if(ExistenClientesMenuCensista())
		{
		    //Modificamos el clientePreventa
			long modificarClientePreventa = 0;
			try
			{
				modificarClientePreventa = new ClientePreventaBLL().ModificarClientePreventaNombre(clienteId,nombres,paterno, 
																									materno,casada,direccion,referencia);
			}
			catch(Exception localException)
			{
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del clientePreventa: " + localException.getMessage());
				}
			}
			
			if(modificarClientePreventa <= 0)
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el nombre del cliente bajo preventa.", 1);
				return false;
			}
			else
			{
				//Derivamos la actualizacion del cliente en funcion a su sincronizacion del punteo
				//Si esta sincronizado por clienteId, si no por Id
				if(clientePreventa.is_clientePunteoSincronizado())
				{
					long modificarCliente = 0;
					try
					{
						modificarCliente = new ClienteBLL().ModificarClienteNombrePorClienteId(clienteId,nombres,paterno, 
																								materno,casada,direccion,referencia);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por clienteId: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por clienteId: " + localException.getMessage());
						}
					}
					
					if(modificarCliente <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el nombre del cliente por clienteId.", 1);
						return false;
					}
					else
					{
						modificado = true;
					}
				}
				else
				{
					long modificarCliente = 0;
					try
					{
						modificarCliente = new ClienteBLL().ModificarClienteNombrePorId(clientePreventa.get_clienteId()*(-1), 
																						nombres, paterno, materno, casada,direccion,referencia);
					}
					catch(Exception localException)
					{
						if(localException.getMessage() == null || localException.getMessage().isEmpty())
						{
					        myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: vacio");
						}
						else
						{
							myLog.InsertarLog("App",this.toString(),1,"Error al modificar el nombre del cliente por Id: " + localException.getMessage());
						}
					}
					
					if(modificarCliente <= 0)
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el nombre del cliente.", 1);
						return false;
					}
					else
					{
						modificado = true;
					}
				}	
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Para actualizar los nombres del cliente, debe sincronizar los datos del Menu Censista.", 1);
			return false;
		}	
		
		return modificado;
	}
	
	private boolean ExistenClientesMenuCensista()
	{
		boolean existen = false;
		ArrayList<Cliente> listadoClientes = null;
		
		try
		{
			listadoClientes = new ClienteBLL().ObtenerClientes();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de clientes: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener el listado de clientes: " + localException.getMessage());
			}
		}
		
		if(listadoClientes == null)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener el listado de clientes.", 1);
			return existen;
		}
		else
		{
			existen = true;
		}
		
		return existen;
	}
	
	private void ActualizarCliente()
	{
		if(ActualizarClienteBD())
		{
			if(clientePreventa.is_clientePunteoSincronizado())
			{
				ActualizarClienteServer();
			}
			else
			{
				MostrarPantallaPreventaNits();
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(),"No se pudo actualizar el nombre del cliente.",1);
			return;
		}
	}
	
	private void ActualizarClienteServer()
	{
		pdEsperarActualizarcliente = new ProgressDialog(this);
		pdEsperarActualizarcliente.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdEsperarActualizarcliente.setMessage("Actualizando cliente ...");
		pdEsperarActualizarcliente.setCancelable(false);
		pdEsperarActualizarcliente.setCanceledOnTouchOutside(false);

	    WSUpdateClienteNombre wsUpdateClienteNombre = new WSUpdateClienteNombre();
	    
	    try
	    {
	    	wsUpdateClienteNombre.execute();
	    }
	    catch(Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
        		myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSUpdateClienteNombre: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar WSUpdateClienteNombre: " + localException.getMessage());
			}
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo ejecutar el webservice WSUpdateClienteNombre.", 1);
	    }		
	}
	
	private class WSUpdateClienteNombre extends AsyncTask<Void, Integer, Boolean>
	{
		String UPDATECLIENTE_METHOD_NAME = "UpdateClienteNombre";
		String UPDATECLIENTE_SOAP_ACTION = NAMESPACE + UPDATECLIENTE_METHOD_NAME;
		
		boolean WSUpdateCliente = false;
		int intResultado;
		String stringResultado;
		SoapObject soapObjects;

	    protected void onPreExecute()
	    {
	    	pdEsperarActualizarcliente.show();
	    }
    
	    protected Boolean doInBackground(Void... paramVarArgs)
	    {
	    	WSUpdateCliente = false;
			
			SoapObject localSoapObject = new SoapObject(NAMESPACE,UPDATECLIENTE_METHOD_NAME);
			
			localSoapObject.addProperty("clienteId",clienteId);
			localSoapObject.addProperty("nombres",nombres);
			localSoapObject.addProperty("paterno",paterno);
			localSoapObject.addProperty("materno",materno);
			localSoapObject.addProperty("apellidoCasada",casada);
			localSoapObject.addProperty("direccion",direccion);
			localSoapObject.addProperty("referencia",referencia);
			
			SoapSerializationEnvelope localSoapSerializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
			localSoapSerializationEnvelope.dotNet = true;
			localSoapSerializationEnvelope.setOutputSoapObject(localSoapObject);
			HttpTransportSE localHttpTransportSE = new HttpTransportSE(URL);
			try
			{
				localHttpTransportSE.call(UPDATECLIENTE_SOAP_ACTION, localSoapSerializationEnvelope);
				
				soapObjects = (SoapObject)localSoapSerializationEnvelope.getResponse();
				
				if(soapObjects != null)
				{
					intResultado = Integer.parseInt(soapObjects.getPropertyAsString("Id"));
					stringResultado = soapObjects.getPropertyAsString("Resultado");					
				}
				
				if(stringResultado.equals("OK") && (intResultado > 0)) 
				{
					WSUpdateCliente = true;
				}
				return true;
			}
			catch (Exception localException)
			{
				WSUpdateCliente = false;
				if(localException.getMessage() == null || localException.getMessage().isEmpty())
				{
			        myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteNombre: vacio");
				}
				else
				{
					myLog.InsertarLog("App",this.toString(),1,"Error al ejecutar el WSUpdateClienteNombre: " + localException.getMessage());
				}
				return true;
			}		
	    }
    
	    protected void onPostExecute(Boolean ejecutado)
	    {
	    	if(pdEsperarActualizarcliente.isShowing())
			{
	    		pdEsperarActualizarcliente.dismiss();
			}
			
			if (ejecutado)
			{
				if(WSUpdateCliente)
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "Nombre del cliente modificado en el servidor.", 1);
					MostrarPantallaPreventaNits();
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar el cliente en el servidor.", 1);
					
					ModificarClientePreventaNombreSincronizacion();					
					MostrarPantallaPreventaNits();
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "Error al ejecutar el WSUpdateClienteNombre.", 1);
			}
		}
	}
	
	private void ModificarClientePreventaNombreSincronizacion()
	{
		long m=0;
		try
		{
			m = new ClientePreventaBLL().ModificarClientePreventaNombreSincronizacion(clientePreventa.get_clienteId(),false);
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del nombre del cliente: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al modificar la sincronizacion del nombre del cliente: " + localException.getMessage());
			}
		}
		
		if(m==0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo modificar la sincronizacion del nombre del cliente.", 1);
		}
	}
	
	private void MostrarPantallaPreventaNits()
	{
	    Intent localIntent = null;
	    
	    localIntent = new Intent(this, Vendedorpreventanits.class);
	    localIntent.putExtra("clienteId", clienteId);
	    localIntent.putExtra("origenSolicitud", "Preventa");
	    startActivity(localIntent);
	}
}
