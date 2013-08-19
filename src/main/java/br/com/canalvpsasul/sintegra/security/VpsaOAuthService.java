package br.com.canalvpsasul.sintegra.security;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.canalvpsasul.sintegra.business.UserBusiness;
import br.com.canalvpsasul.sintegra.vpsa.VpsaApi;
import br.com.canalvpsasul.sintegra.vpsa.VpsaApiImpl;

@Component
public class VpsaOAuthService {

	@Value("#{appProperties.client_id}")
	private String client_id;
	
	@Value("#{appProperties.client_secret}")
	private String client_secret;
	
	@Value("#{appProperties.redirect_uri}")
	private String redirect_uri;
	
	@Autowired
	UserBusiness userBusiness;

	public String getAuthorizationUrl() {
		return String.format("https://www.vpsa.com.br/apps/oauth/authorization?response_type=code&client_id=%s&redirect_uri=%s", client_id, redirect_uri);		
	}
	
	public VpsaOAuthToken getAccessToken(String authCode) throws Exception {
			
		if(StringUtils.isEmpty(authCode))
			throw new Exception("Não foi possível obter o código de autorização do serviço de autenticação da VPSA.");
		
		VpsaOAuthToken vpsaTokenResponse = null;
		
		String result = "";
		
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("client_id", client_id));
        nameValuePairs.add(new BasicNameValuePair("client_secret", client_secret));
        nameValuePairs.add(new BasicNameValuePair("redirect_uri", redirect_uri));
        nameValuePairs.add(new BasicNameValuePair("code", authCode));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "authorization_code"));
        
        InputStream is = null;
        try
        {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("https://www.vpsa.com.br/apps/oauth/token");
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            
        } catch (Exception e) {
        	throw new Exception("Não foi possível conectar ao serviço de obtenção do token de autenticação da VPSA.");
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
            
            ObjectMapper mapper = new ObjectMapper();
            vpsaTokenResponse = mapper.readValue(result, VpsaOAuthToken.class);
            
        }catch (Exception e) {
        	throw new Exception("A resposta do servico de obtenção do token de autenticação da VPSA não é válida.");
        }
        finally {
        	is.close();
        }
        
		return vpsaTokenResponse;	
	}
	
	public VpsaApi getVpsaApi(String accessToken) {
		VpsaApi vpsaApi = new VpsaApiImpl(accessToken);
		return vpsaApi;
	}
	
	public VpsaApi getVpsaApi() {
		VpsaApi vpsaApi = new VpsaApiImpl(userBusiness.getCurrent().getToken());
		return vpsaApi;
	}
	
}
