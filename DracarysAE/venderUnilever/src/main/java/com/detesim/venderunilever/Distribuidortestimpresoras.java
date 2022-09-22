package com.detesim.venderunilever;

import java.util.ArrayList;
import java.util.Set;
import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver; 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import BLL.ImpresoraBLL;
import BLL.MyLogBLL;
import Clases.Impresora;
import Clases.LoginEmpleado;
import Utilidades.Utilidades;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Distribuidortestimpresoras extends Activity implements OnClickListener
{
	LinearLayout llTestImpresoras;
	Utilidades theUtilidades = new Utilidades();
	LoginEmpleado loginEmpleado;
	MyLogBLL myLog = new MyLogBLL();
	
	private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothPrintDriver mChatService = null;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
	
    Button btnBluetooth;
	TextView tvBluetooth;
	Button btnImpresora;
	TextView tvImpresora;
	EditText etTexto;
	Button btnImprimir;
	Button btnQr;
	ImageView imgvQr;
	ArrayList<Impresora> listadoImpresora;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_distribuidortestimpresoras);
		
		llTestImpresoras = (LinearLayout)findViewById(R.id.llDistribuidorTestImpresorasLinearLayout);
		btnBluetooth = (Button)findViewById(R.id.btnDistribuidorTestImpresorasBluetooth);
		btnBluetooth.setOnClickListener(this);
		tvBluetooth = (TextView)findViewById(R.id.tvDistribuidorTestImpresorasBluetooth);
		btnImpresora = (Button)findViewById(R.id.btnDistribuidorTestImpresorasImpresora);
		btnImpresora.setOnClickListener(this);
		tvImpresora = (TextView)findViewById(R.id.tvDistribuidorTestImpresorasImpresora);
		etTexto = (EditText)findViewById(R.id.etDistribuidorTestImpresorasTexto);
		btnImprimir = (Button)findViewById(R.id.btnDistribuidorTestImpresorasImprimir);
		btnImprimir.setOnClickListener(this);
		imgvQr = (ImageView)findViewById(R.id.imgvDistribuidorTestImpresorasQr);
		btnQr = (Button)findViewById(R.id.btnDistribuidorTestImpresorasGenerarQr);
		btnQr.setOnClickListener(this);
		
		llTestImpresoras.setBackground(getResources().getDrawable(theUtilidades.get_fondo()));
		
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
	    	theUtilidades.MostrarMensaje(getApplicationContext(), "El distribuidor no se logeo correctamente.", 1);
	    }
	    else
	    {
	    	MostrarControlesImpresion(false);
	    	btnImpresora.setVisibility(View.INVISIBLE);
	    	if(ObtenerImpresoras())
	    	{
	    		btnBluetooth.setVisibility(View.VISIBLE);
	    	}
	    	else
	    	{
	    		theUtilidades.MostrarMensaje(getApplicationContext(), "No existen impresoras asignadas a este distribuidor.", 1);
	    	}
	    }
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.btnDistribuidorTestImpresorasBluetooth:
			VerificarBluetooth();
			break;
		case R.id.btnDistribuidorTestImpresorasImpresora:
			VincularImpresora();
			break;
		case R.id.btnDistribuidorTestImpresorasImprimir:
			ImprimirTexto();
			break;
		case R.id.btnDistribuidorTestImpresorasGenerarQr:
			GenerarQR();
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
			return true;
		}
	}
	
	private void VerificarBluetooth()
	{
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) 
        {
        	tvBluetooth.setText("El celular no soporta Bluetooth.");
        }
        else
        {
        	if(mBluetoothAdapter.isEnabled())
        	{
        		tvBluetooth.setText("Activado");
        		btnImpresora.setVisibility(View.VISIBLE);
        		
        		mChatService = new BluetoothPrintDriver(this, handler);
        		
        		if (mChatService != null) 
        		{
                    // Only if the state is STATE_NONE, do we know that we haven't started already
                    if (mChatService.getState() == BluetoothPrintDriver.STATE_NONE) 
                    {
                    	mChatService.start();
                    }
                }
        	}
        	else
        	{
        		tvBluetooth.setText("Deshabilitado");
        		theUtilidades.MostrarMensaje(getApplicationContext(), "El Bluetooth se encuentra deshabilitado.", 1);
        	}
        }
	}
	
	@Override
    public void onDestroy() 
	{
        super.onDestroy();
        if (mChatService != null) 
        {
        	mChatService.stop();
        }
    }
	
	private final Handler handler = new Handler() 
	{
        @Override
        public void handleMessage(Message msg) 
        {
            switch (msg.what) 
            {
            case MESSAGE_STATE_CHANGE:
                switch (msg.arg1) 
                {
                case BluetoothPrintDriver.STATE_NONE:
                case BluetoothPrintDriver.STATE_LISTEN:
                	break;
                case BluetoothPrintDriver.STATE_CONNECTING:
                    break;
                case BluetoothPrintDriver.STATE_CONNECTED:
                    break;
                }
                break;
            case MESSAGE_WRITE:
                break;
            case MESSAGE_READ:
            	String ErrorMsg = null;
            	byte[] readBuf = (byte[]) msg.obj;
                float Voltage = 0;
                if(readBuf[2]==0)
                	ErrorMsg = "NO ERROR!";
                else
                {
	                if((readBuf[2] & 0x02) != 0)
	                	ErrorMsg = "ERROR: La impresora no esta conectada!";
	                if((readBuf[2] & 0x04) != 0)
	                	ErrorMsg = "ERROR: La impresora no tiene papel!";
	                if((readBuf[2] & 0x08) != 0)
	                	ErrorMsg = "ERROR: El voltaje de la impresora es muy bajo!";
	                if((readBuf[2] & 0x40) != 0)
	                	ErrorMsg = "ERROR: La impresora esta muy caliente!";
                }
                Voltage = (float) ((readBuf[0]*256 + readBuf[1])/10.0);
                DisplayToast(ErrorMsg+" voltaje de la bateria "+Voltage+" voltios");
                break;
            case MESSAGE_DEVICE_NAME:
                break;
            case MESSAGE_TOAST:
            	Toast.makeText(getApplicationContext(), msg.getData().getString("toast"),Toast.LENGTH_SHORT).show();
                break;
            }
        }
    };
    
    private void DisplayToast(String mensaje)
    {
    	theUtilidades.MostrarMensaje(getApplicationContext(), mensaje, 1);
    }
    
    private void VincularImpresora()
    {
    	Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		
		if (pairedDevices.size() > 0) 
		{
            for (BluetoothDevice device : pairedDevices)
            {
            	for(Impresora impresora : listadoImpresora)
            	{
            		if(device.getName().equals(impresora.get_nombre()) 
            				&& device.getAddress().equals(impresora.get_address()))
                    {
            			mChatService.connect(mBluetoothAdapter.getRemoteDevice(device.getAddress()));
                    }
            	}
            }
            
            if(mChatService == null)
            {
            	theUtilidades.MostrarMensaje(getApplicationContext(), "No existe ninguna impresora vinculada por Bluetooth.", 1);
            }
            else
            {
            	tvImpresora.setText("Si");
            	MostrarControlesImpresion(true);
            }
        } 
		else 
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No existen dispositivos vinculados al Bluetooth.", 1);
        }
    }
	
	private void MostrarControlesImpresion(boolean estado)
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

	private void ImprimirTexto()
	{		
		if(etTexto.getText().length()<=0)
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "Debe escribir un texto para poder imprimirlo", 1);
			return;
		}
		
		BluetoothPrintDriver.Begin();
		BluetoothPrintDriver.BT_Write(etTexto.getText().toString());
		BluetoothPrintDriver.BT_Write("\r");
		
		//BluetoothPrintDriver.printImage();
		
	}
	
	private void GenerarQR()
	{
		Bitmap qr = GenerarQR(etTexto.getText().toString(),200);
		if(qr!=null)
		{
			imgvQr.setImageBitmap(qr);
			
			/*
			BluetoothPrintDriver.Begin();
			
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		    qr.compress(Bitmap.CompressFormat.PNG, 0, localByteArrayOutputStream);
		    
			BluetoothPrintDriver.printByteData(localByteArrayOutputStream.toByteArray());
			
			
			InputStream in = null;
			try 
			{
				in = getResources().getAssets().open("Rongta.jpg");
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}*/
			
			BluetoothPrintDriver.printImage();
			}
		else
		{
			theUtilidades.MostrarMensaje(getApplicationContext(), "No se pudo generar el codigo QR.", 1);
		}
	}

	private Bitmap GenerarQR(String texto,int tamanioPx)
	{
		QRCodeWriter writer = new QRCodeWriter();
	    try 
	    {
	        BitMatrix bitMatrix = writer.encode(texto, BarcodeFormat.QR_CODE, tamanioPx, tamanioPx);
	        int width = bitMatrix.getWidth();
	        int height = bitMatrix.getHeight();
	        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
	        for (int x = 0; x < width; x++) 
	        {
	            for (int y = 0; y < height; y++) 
	            {
	                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
	            }
	        }
	        
	        return bmp;
	    } 
	    catch (WriterException localException) 
	    {
	    	if(localException.getMessage() == null || localException.getMessage().isEmpty())
			{
		        myLog.InsertarLog("App",this.toString(),1,"Error al generar el codigo Qr: vacio");
			}
			else
			{
				myLog.InsertarLog("App",this.toString(),1,"Error al generar el codigo Qr: " + localException.getMessage());
			} 
	    	return null;
	    }
	}
}
