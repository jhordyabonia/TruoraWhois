package com.jhordyabonia.webservice;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jhordyabonia.webservice.HttpRequest.HttpRequestException;

import java.util.Map;

public class Client extends AsyncTask<String, Long, String> {
	// Variable con los datos para pasar al web service
	private Map<String, String> datos;
	// Url del servicio web
	private String url = "";

	// Actividad para mostrar el cuadro de progreso
	private Context actividad;

	private boolean progVisible = false;


	// Resultado
	private String json = null;

	// Clase a la cual se le retorna los datos dle ws
	private Asynchtask callback = null;


	public void setCallback(Asynchtask callback) {
		this.callback = callback;
	}

	private ProgressDialog progDailog;



	public Client() {
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (progVisible) {
			if (progDailog == null) {
				progDailog = new ProgressDialog(actividad);
				progDailog.setMessage("Cargando...");
				progDailog.setIndeterminate(false);
				progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				// progDailog.setCancelable(true);
			}
			progDailog.show();
		}
	}
	@Override
	protected String doInBackground(String... params) {
		try {	
			return HttpRequest.post(this.url).form(this.datos).body();
		} catch (HttpRequestException exception) {
			Log.e("doInBackground", exception.getMessage());
			return "Sin conexion a internet";//"Error HttpRequestException";
		} catch (Exception e) {
			Log.e("doInBackground", e.getMessage());
			return "Error Exception";
		}
	}

	@Override
	protected void onPostExecute(String response) {
		super.onPostExecute(response);
		this.json = response;
		if (progVisible)
			progDailog.dismiss();
		// Retorno los datos
		if (callback != null)
			{
				if(LOG.ACTIVE)
					LOG.save("{\"url\":\""+this.url+"\",\"result\":\""+response+"\"},");
				callback.processFinish(this.json);
			}
	}


	public void setDatos(Map<String, String> datos) {
		this.datos = datos;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setActividad(Context actividad) {
		progVisible = actividad != null;
		this.actividad = actividad;
	}

	public String getXml() {
		return json;
	}

	public void setXml(String xml) {
		this.json = xml;
	}
}
