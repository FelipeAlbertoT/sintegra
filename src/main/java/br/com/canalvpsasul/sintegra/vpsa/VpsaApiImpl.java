package br.com.canalvpsasul.sintegra.vpsa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

import br.com.canalvpsasul.sintegra.entities.Terceiro;

public class VpsaApiImpl implements VpsaApi {

	private String vpsaOAuthToken;
	
	public VpsaApiImpl(String vpsaToken) {	
		vpsaOAuthToken = vpsaToken;		
	}
	
	@Override
	public Terceiro getTerceiroById(Long id) throws Exception {
		
		String JSONTerceiro = callGetApi("terceiros/" + id.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		Terceiro terceiro = mapper.readValue(JSONTerceiro, Terceiro.class);
		
		return terceiro; 
	}
	
	private String callGetApi(String apiPath, String... apiAdditionalParameters) throws Exception {
		
		String result = "";
		
		String apiUrl = String.format("https://www.vpsa.com.br/apps/api/%s?token=%s", apiPath, vpsaOAuthToken);
		if(!StringUtils.isEmpty(apiAdditionalParameters)) 
			apiUrl += "&" + apiAdditionalParameters;
			
		InputStream is = null;
        try {
        	
            HttpClient httpclient = new DefaultHttpClient();
            
            HttpGet httpget = new HttpGet(apiUrl);
            HttpResponse response = httpclient.execute(httpget);
            
            if(response != null){
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
            }
            
        } catch (Exception e) {
        	throw new Exception("Não foi possível conectar ao serviço da VPSA.");
        }
        
        StringBuilder sb = new StringBuilder();
        String line = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            
        }catch (Exception e) {
        	throw new Exception("A resposta do servico da VPSA não é válida.");
        }
        finally {
        	is.close();
        }
        
        return result;		
	}
	
	@SuppressWarnings("unused")
	private String callPostApi(String JSONObject, String apiPath, String apiAdditionalParameters) throws Exception {
		
		String result = "";
		
		String apiUrl = String.format("https://www.vpsa.com.br/apps/api/%s?token=%s", apiPath, vpsaOAuthToken);
		if(!StringUtils.isEmpty(apiAdditionalParameters)) 
			apiUrl += "&" + apiAdditionalParameters;
			
		InputStream is = null;
        try {
        	
        	StringEntity se = new StringEntity(JSONObject);  
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        	
            HttpClient httpclient = new DefaultHttpClient();
            
            HttpPost httppost = new HttpPost(apiUrl);
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);
            
            if(response != null){
	            HttpEntity entity = response.getEntity();
	            is = entity.getContent();
            }
            
        } catch (Exception e) {
        	throw new Exception("Não foi possível conectar ao serviço da VPSA.");
        }
        
        StringBuilder sb = new StringBuilder();
        String line = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),8);
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();
            
        }catch (Exception e) {
        	throw new Exception("A resposta do servico da VPSA não é válida.");
        }
        finally {
        	is.close();
        }
        
        return result;		
	}
}
