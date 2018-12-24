import static java.time.temporal.ChronoUnit.MINUTES;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class HttpUrlConnectionExample {

  private List<String> cookies;
  private HttpsURLConnection conn;

  private final String USER_AGENT = "Mozilla/5.0";

  public static void main(String[] args) throws Exception {

	String url = "https://rodas.ayesa.com/rodasnet/ACCESO/LOGINRODAS.ASPX";
	String gmail = "https://rodas.ayesa.com/rodasnet/usuario/Bienvenida.aspx";

	HttpUrlConnectionExample http = new HttpUrlConnectionExample();

	// make sure cookies is turn on
	CookieHandler.setDefault(new CookieManager());

	// 1. Send a "GET" request, so that you can extract the form's data.
	String page = http.GetPageContent(url);
	String postParams = http.getFormParams(page, "lcaballero", "12Entrabajo12");

	// 2. Construct above post's content and then send a POST request for
	// authentication
	http.sendPost(url, postParams);

	// 3. success then go to gmail.
	String result = http.GetPageContent(gmail);
	System.out.println(result);
	
	getData(result);
	
  }

  private void sendPost(String url, String postParams) throws Exception {

	URL obj = new URL(url);
	conn = (HttpsURLConnection) obj.openConnection();

	// Acts like a browser
	conn.setUseCaches(false);
	conn.setRequestMethod("POST");
//	conn.setRequestProperty("Host", "accounts.google.com");
//	conn.setRequestProperty("User-Agent", USER_AGENT);
//	conn.setRequestProperty("Accept",
//		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
//	for (String cookie : this.cookies) {
//		conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
//	}
//	conn.setRequestProperty("Connection", "keep-alive");
//	conn.setRequestProperty("Referer", "https://accounts.google.com/ServiceLoginAuth");
//	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//	conn.setRequestProperty("Content-Length", Integer.toString(postParams.length()));
//
//	conn.setDoOutput(true);
//	conn.setDoInput(true);
	
//	conn.setRequestProperty("__EVENTTARGET", "Button_Acceder");
//	conn.setRequestProperty("__EVENTARGUMENT", "");
//	conn.setRequestProperty("__VIEWSTATE", "wEPBfwCSDRzSUFBQUFBQUFFQUMyT3pVckRRQkNBeVRhYldsdFlUem1tU3crZUpFbWxwZmhUUWF4SURvVWUxS093eld5YmxlMU8yZDBHOFcxOEJKK2hENllKZWh1K0diNXZmZ0pHVHkvejZYUTJ1WnFNWnl3bVNiUkM1NHNGRGNkNVBvNURRbHZZVThaSVcvbWRwc01WV3I0Uk5kb0xyb3kzQ0lmUFVuQ05qb1B3NklCMEdjUURFakFXaDBuNExEODhqUXBRdUJOSjkxVTV0ZGF5QWlBZGR0YVloNTFtam9NTi9IWDZleTFLV2FFR2FlbkppenNJcS9DNlZUYW5JUjA4WUZNVVRoNi9SVU43ck1XRXZ0MFd5eWZ1YkRrZnBXbW1kbUlyWFNhdFJadSs3N2NqTHJUYW1ybFl1NlVDMEpKbmQrZG03ZlkzLzM1dWtOZkhMNjBBdVRSOG9hd3NQYmFMKzlLckdwdG4rMjBvb05Gam03ZndDeW1QWU8wM0FRQUFkhJUYEDsir6kRbt8bn9");
//	conn.setRequestProperty("__VIEWSTATEGENERATOR", "2A8C50B2");
//	conn.setRequestProperty("__EVENTVALIDATION", "/wEdAAQBtDzO4DQTRl4uFxjuiMRlKhoCyVdJtLIis5AgYZ/RYe4sciJO3Hoc68xTFtZGQEh8DQjdovpcJSWoPeHPvBcEH83/2wBCeDB30myRL8z7CwVDw0nzPz2yJXs78hsq8fs=");

	// Send post request
	conn.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
	wr.writeBytes(postParams);
	wr.flush();
	wr.close();

	int responseCode = conn.getResponseCode();
	System.out.println("\nSending 'POST' request to URL : " + url);
	System.out.println("Post parameters : " + postParams);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = 
             new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	// System.out.println(response.toString());

  }

  private String GetPageContent(String url) throws Exception {

	URL obj = new URL(url);
	conn = (HttpsURLConnection) obj.openConnection();

	// default is GET
	conn.setRequestMethod("GET");

	conn.setUseCaches(false);

	// act like a browser
	conn.setRequestProperty("User-Agent", USER_AGENT);
	conn.setRequestProperty("Accept",
		"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
	conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	if (cookies != null) {
		for (String cookie : this.cookies) {
			conn.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
		}
	}
	int responseCode = conn.getResponseCode();
	System.out.println("\nSending 'GET' request to URL : " + url);
	System.out.println("Response Code : " + responseCode);

	BufferedReader in = 
            new BufferedReader(new InputStreamReader(conn.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();

	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();

	// Get the response cookies
	setCookies(conn.getHeaderFields().get("Set-Cookie"));

	return response.toString();

  }

  
  public static Map<String, String> getData(String html){
	  Map<String, String> mapData= new HashMap<String, String>();
	  
	  Document doc = Jsoup.parse(html);
	  Element presenciaObligatoria = doc.getElementById("Label_SaldoCentral");
	  Element saldoSemanal = doc.getElementById("Label_1");
	  Element diasIncumplidosSemana = doc.getElementById("Label_2");
	  Element saldoDia = doc.getElementById("Label_Saldo");
	  Element usuarioConectado = doc.getElementById("lblUsuarioConectado");
	  Element diaHoy = doc.getElementById("Hyperlink_Hoy");
	    
	  mapData.put("presenciaObligatoria", presenciaObligatoria.text());
	  mapData.put("saldoSemanal", saldoSemanal.text());
	  mapData.put("diasIncumplidosSemana", diasIncumplidosSemana.text());
	  mapData.put("saldoDia", saldoDia.text());
	  mapData.put("usuarioConectado", usuarioConectado.text());
	  mapData.put("diaHoy", diaHoy.text());
	  mapData.put("restanteHoy", calcularRestante(saldoDia.text()));
	
	  Ventana.crearVentana(mapData);
	    
	  return mapData;
  }
  
  public static String calcularRestante(String saldoHoy){
	  
      String jornada= "08:15";
      LocalTime saldoHoyLocalTime= LocalTime.parse(saldoHoy,DateTimeFormatter.ISO_TIME);
      LocalTime jornadaLocalTime= LocalTime.parse(jornada,DateTimeFormatter.ISO_TIME);
      
      Long minutosDiferencia= MINUTES.between(saldoHoyLocalTime, jornadaLocalTime);
      String horasRestantes= "";
      String minutosRestantes= "";
      if(minutosDiferencia<60){
      	horasRestantes= "0";
      	minutosRestantes= minutosDiferencia.toString();
      }
      else{
      	Long a = minutosDiferencia/60L;
      	Long b =minutosDiferencia%60L;
      	horasRestantes= a.toString();
      	minutosRestantes= b.toString();
      }
	  
      String result= "Te queda "+horasRestantes+" horas y "+minutosRestantes+" minutos";
	  return result;
	  
  }
  
  public String getFormParams(String html, String username, String password)
		throws UnsupportedEncodingException {

	System.out.println("Extracting form's data...");

	Document doc = Jsoup.parse(html);

	// Google form id
	Element loginform = doc.getElementById("_ctl2");
	Elements inputElements = loginform.getElementsByTag("input");
	List<String> paramList = new ArrayList<String>();
	for (Element inputElement : inputElements) {
		String key = inputElement.attr("name");
		String value = inputElement.attr("value");

		if (key.equals("username"))
			value = username;
		else if (key.equals("password"))
			value = password;
		else if(key.equals("__EVENTTARGET"))
			value = "Button_Acceder";
		else if(key.equals("__EVENTARGUMENT"))
			value = "";
		else if(key.equals("__VIEWSTATE"))
			value = "/wEPBfwCSDRzSUFBQUFBQUFFQUMyT3pVckRRQkNBeVRhYldsdFlUem1tU3crZUpFbWxwZmhUUWF4SURvVWUxS093eld5YmxlMU8yZDBHOFcxOEJKK2hENllKZWh1K0diNXZmZ0pHVHkvejZYUTJ1WnFNWnl3bVNiUkM1NHNGRGNkNVBvNURRbHZZVThaSVcvbWRwc01WV3I0Uk5kb0xyb3kzQ0lmUFVuQ05qb1B3NklCMEdjUURFakFXaDBuNExEODhqUXBRdUJOSjkxVTV0ZGF5QWlBZGR0YVloNTFtam9NTi9IWDZleTFLV2FFR2FlbkppenNJcS9DNlZUYW5JUjA4WUZNVVRoNi9SVU43ck1XRXZ0MFd5eWZ1YkRrZnBXbW1kbUlyWFNhdFJadSs3N2NqTHJUYW1ybFl1NlVDMEpKbmQrZG03ZlkzLzM1dWtOZkhMNjBBdVRSOG9hd3NQYmFMKzlLckdwdG4rMjBvb05Gam03ZndDeW1QWU8wM0FRQUFkhJUYEDsir6kRbt8bn9/i+kfOQma6ayE05fpOe7PARSY=";
		else if(key.equals("__VIEWSTATEGENERATOR"))
			value = "2A8C50B2";
		else if(key.equals("__EVENTVALIDATION"))
			value = "/wEdAAQBtDzO4DQTRl4uFxjuiMRlKhoCyVdJtLIis5AgYZ/RYe4sciJO3Hoc68xTFtZGQEh8DQjdovpcJSWoPeHPvBcEH83/2wBCeDB30myRL8z7CwVDw0nzPz2yJXs78hsq8fs=";
		paramList.add(key + "=" + URLEncoder.encode(value, "UTF-8"));
	}

	// build parameters list
	StringBuilder result = new StringBuilder();
	for (String param : paramList) {
		if (result.length() == 0) {
			result.append(param);
		} else {
			result.append("&" + param);
		}
	}
	return result.toString();
  }

  public List<String> getCookies() {
	return cookies;
  }

  public void setCookies(List<String> cookies) {
	this.cookies = cookies;
  }

}