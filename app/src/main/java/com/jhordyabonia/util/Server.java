package com.jhordyabonia.util;

import java.util.HashMap;

import com.jhordyabonia.truora_whois.MainActivity;
import com.jhordyabonia.webservice.Asynchtask;
import com.jhordyabonia.webservice.Client;

import android.app.Activity;
import android.util.Log;

public final class Server {
	public static String URL_SERVER = "http://127.0.0.1:8090";
	private static Client ws;
	private static HashMap<String, String> data_toSend = new HashMap<String, String>();

	public static final void setDataToSend(HashMap<String, String> toSend) {
		data_toSend = toSend;
	}

	public static void send(String url, MainActivity a, Asynchtask t) {
		ws = new Client();
		ws.setUrl(URL_SERVER + url);
		ws.setDatos(data_toSend);
		ws.setActividad(a);
		ws.setCallback(t);
		ws.execute("");
	}
}
