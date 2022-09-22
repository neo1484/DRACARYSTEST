package com.detesim.venderunilever;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import BLL.ImpresoraBLL;
import BLL.MyLogBLL;
import Clases.Impresora;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Distribuidortestimpresora extends Activity implements OnClickListener
{
	LinearLayout llTestImpresora;
	Utilidades theUtilidades = new Utilidades();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	EditText etTexto;
	Button btnImprimir;
	
	ArrayList<Impresora> listadoImpresora;
	
	BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
	
	OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
 
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidortestimpresora);
		
		llTestImpresora = (LinearLayout)findViewById(R.id.DistribuidorTestImpresoraLinearLayout);
		etTexto = (EditText)findViewById(R.id.etTestImpresoraTexto);
		btnImprimir = (Button)findViewById(R.id.btnTestImpresoraImprimir);
		btnImprimir.setOnClickListener(this);	
		
		llTestImpresora.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
	    
	    loginEmpleado = null;
	    try
	    {
	    	loginEmpleado = theUtilidades.ObtenerLoginEmpleado();
	    }
	    catch (Exception localException)
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
	    	{
	            myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado distribuidor: vacio");
	    	}
	    	else
	    	{
	    		myLog.InsertarLog("App",this.toString(),1,"Error al obtener loginEmpleado distribuidor: " + localException.getMessage());
	    	} 
	    }
	      
	    if(loginEmpleado == null)
	    {
	    	MostrarControles(false);
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El distribuidor no se logeo correctamente.", 1);
	    }
	    else
	    {
	    	MostrarControles(true);
	    }
	}
	
	private void MostrarControles(boolean estado)
	{
		int visibility;
		if(estado)
		{
			visibility = View.VISIBLE;
		}
		else
		{
			visibility = View.INVISIBLE;
		}
		
		etTexto.setVisibility(visibility);
		btnImprimir.setVisibility(visibility);
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnTestImpresoraImprimir:
			ImprimirPrueba();
			break;
		}
	}
	
	private void ImprimirPrueba()
	{
		if(etTexto.getText().length() > 0)
		{
			if(ObtenerImpresoras())
			{
				if(BuscarImpresora())
				{
					if(ConectarBluetooth())
					{
						EnviarDatos();
						
						if(!CerrarBluetooth())
						{
							theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo cerrar la conexion del bluetooth.", 1);
						}
					}
					else
					{
						theUtilidades.MostrarMensaje(getApplicationContext(), "No pudo conectarse al bluetooth.", 1);
					}
				}
				else
				{
					theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo encontrar una impresora.", 1);
				}
			}
			else
			{
				theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo obtener las impresoras.", 1);
			}
		}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe introducir un texto para imprimir el test.", 1);
		}
	}
	
	private boolean ObtenerImpresoras()
	{
		boolean cargado = false;
		listadoImpresora = null;
		try
		{
			listadoImpresora = new ImpresoraBLL().ObtenerImpresoras();
		}
		catch(Exception localException)
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener las impresoras: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener las impresoras: " + localException.getMessage());
			} 
		}
		
		if(listadoImpresora == null)
		{
			return cargado;
		}
		else
		{
			cargado = true;
		}
		
		return cargado;
	}
	
	private boolean BuscarImpresora() 
	{
		boolean impresoraEncontrada = false;
		try 
		{
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
 
            if (mBluetoothAdapter == null) 
            {
            	theUtilidades.MostrarMensaje(getApplicationContext(), "No existe un adaptador bluetooth disponible.", 1);
            	return false;
            }
 
            if (!mBluetoothAdapter.isEnabled()) 
            {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 0);
            }
 
            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
            if (pairedDevices.size() > 0) 
            {
                for (BluetoothDevice device : pairedDevices) 
                {
                	for(Impresora itemImpresora : listadoImpresora)
                	{
                		if (device.getName().equals(itemImpresora.get_nombre()) && device.getAddress().equals(itemImpresora.get_address())) 
                        {
                            mmDevice = device;
                            break;
                        }
                	}
                }
            }
            
            if(mmDevice!=null)
            {
	            theUtilidades.MostrarMensaje(getApplicationContext(), "Impresora encontrada.", 1);
	            impresoraEncontrada = true;
            }
            else
            {
            	theUtilidades.MostrarMensaje(getApplicationContext(), "Impresora no encontrada.", 1);
            }
        } 
		catch (NullPointerException localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al obtener un dispositivo bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al obtener un dispositivo bluetooth: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al buscar la impresora.", 1);
			return false;
        } 
		catch (Exception localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al obtener un dispositivo bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al obtener un dispositivo bluetooth: " + localException.getMessage());
			} 
			theUtilidades.MostrarMensaje(getApplicationContext(), "Error al buscar la impresora.", 1);
			return false;
        }
		
		return impresoraEncontrada;
    }

	private boolean ConectarBluetooth()
	{
		boolean conectado = false;
        try 
        {
            // Standard SerialPortServiceID UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        	UUID uuid = mmDevice.getUuids()[0].getUuid();
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
 
            EscucharTransmision();
 
            theUtilidades.MostrarMensaje(getApplicationContext(), "Bluetooth conectado.", 1);
            conectado = true;
        } 
        catch (NullPointerException localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al abrir la conexion bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al abrir la conexion bluetooth: " + localException.getMessage());
			} 
			return false;
        } 
		catch (Exception localException) 
		{
			if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al abrir la conexion bluetooth: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al abrir la conexion bluetooth: " + localException.getMessage());
			} 
			return false;
        }
        
        return conectado;
    }
	
	private void EnviarDatos() 
	{
		String cadenaFormateada;
		
		cadenaFormateada = etTexto.getText().toString() + "\n\n\n\n";
		
        try 
        {
            mmOutputStream.write(cadenaFormateada.getBytes());   
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al enviar los datos: " + localException.getMessage());
        	} 
        	
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
        	return;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al enviar los datoss: " + localException.getMessage());
        	} 
        	
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al enviar los datos.", 1);
        	return;
        }	        
        
        // tell the user data were sent
        theUtilidades.MostrarMensaje(getApplicationContext(), "Test OK.", 1);
    }
	
	private void EscucharTransmision() 
	{
		final Handler handler = new Handler();
        try 
        {
            // This is the ASCII code for a newline character
            final byte delimiter = 10;
 
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];
 
            workerThread = new Thread(new Runnable() 
            {
                public void run() 
                {
                    while(!Thread.currentThread().isInterrupted() && !stopWorker) 
                    {
                         try 
                         {
                            int bytesAvailable = mmInputStream.available();
                            
                            if (bytesAvailable > 0) 
                            {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);
                                
                                for (int i = 0; i < bytesAvailable; i++) 
                                {
                                    byte b = packetBytes[i];
                                    if (b == delimiter) 
                                    {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(readBuffer,0,encodedBytes,0,encodedBytes.length);
                                        
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;
 
                                        handler.post(new Runnable() 
                                        {
                                            public void run() 
                                            {
                                                theUtilidades.MostrarMensaje(getApplicationContext(),data,1);
                                            }
                                        });
                                    } 
                                    else 
                                    {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                        	}
                         } 
                         catch (IOException localException) 
                         {
                            stopWorker = true;
                            if(localException.getMessage() == null || localException.getMessage().isEmpty())
                        	{
                                myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: vacio");
                        	}
                        	else
                        	{
                        		myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: " + localException.getMessage());
                        	} 
                            theUtilidades.MostrarMensaje(getApplicationContext(), "Error al leer el buffer de datos.", 1);
                        	return;
                        }
                    }
                }
            });
 
            workerThread.start();
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al leer el buffer de datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al leer el buffer de datos: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al leer el buffer de datos.", 1);
        	return;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al leer el buffer de datos: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al leer el buffer de datos.", 1);
        	return;
        }
    }

	private boolean CerrarBluetooth()
	{
		boolean cerrado = false;
        try 
        {
            stopWorker = true;
            mmOutputStream.close();
            mmInputStream.close();
            mmSocket.close();
            cerrado = true;
            
            theUtilidades.MostrarMensaje(getApplicationContext(), "Conexion Bluetooth cerrada.", 1);
        } 
        catch (NullPointerException localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al cerrar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error NullPointerException al cerrar los datos: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al cerrar la conexion Bluethoot", 1);
        	return false;
        } 
        catch (Exception localException) 
        {
        	if(localException.getMessage() == null || localException.getMessage().isEmpty())
        	{
                myLog.InsertarLog("App",this.toString(),1,"Error al cerrar los datos: vacio");
        	}
        	else
        	{
        		myLog.InsertarLog("App",this.toString(),1,"Error al cerrar los datoss: " + localException.getMessage());
        	} 
        	theUtilidades.MostrarMensaje(getApplicationContext(), "Error al cerrar la conexion Bluethoot", 1);
        	return false;
        }
        
        return cerrado;
    }
}
