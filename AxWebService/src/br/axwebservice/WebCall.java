package br.axwebservice;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.os.Handler;

public class WebCall {
	
	public final String url = "http://www.webservicex.com/CurrencyConvertor.asmx";
	public final String namespace = "http://www.webserviceX.NET/";
	public final String method = "ConversionRate";
	public final String action = "http://www.webserviceX.NET/ConversionRate";
	
	public Thread currencyThread;
	
	public String serviceResponse = "Dummy";
	public Handler handler = new Handler();
	
	public String fromCurrencyStr;
	public String toCurrencyStr;
	
	public void serviceCall()
	{
		currencyThread = new Thread()
		{
			public void run()
			{
				SoapObject request = new SoapObject(namespace,method);
				PropertyInfo fromCurrency = new PropertyInfo();
				PropertyInfo toCurrency = new PropertyInfo();
				
				fromCurrency.setName("FromCurrency");
				fromCurrency.setValue(fromCurrencyStr);
				fromCurrency.setType(PropertyInfo.STRING_CLASS);
				
				toCurrency.setName("ToCurrency");
				toCurrency.setValue(toCurrencyStr);
				toCurrency.setType(PropertyInfo.STRING_CLASS);
				
				request.addProperty(fromCurrency);
				request.addProperty(toCurrency);
				
				SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				
				HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
				
				try
				{
					androidHttpTransport.call(action, envelope);
					
					SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
					serviceResponse = response.toString();
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					serviceResponse = ex.toString() + " - " + ex.getMessage();
				}
				handler.post(saveResponse);
			}
		};
		
		currencyThread.start();
	}
	
	final Runnable saveResponse = new Runnable()
	{
		public void run()
		{
			MainActivity.tv.setText(serviceResponse);
		}
	};
	
	public void setFrom(String _fromCurrencyStr)
	{
		fromCurrencyStr = _fromCurrencyStr;
	}
	
	public void setTo(String _toCurrencyStr)
	{
		toCurrencyStr = _toCurrencyStr;
	}
}
