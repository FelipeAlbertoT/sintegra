package br.com.canalvpsasul.sintegra.helper;

public class AjaxHelper {

	public static boolean isAjaxRequest(String requestedWith) {
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;	
	}
}
