package services.impl;

import org.w3c.dom.Document;
import play.libs.WS;
import services.RandomService;

import org.apache.commons.lang.StringEscapeUtils;

public class RandomServiceImpl implements RandomService {
	
	public final static String URL = "http://api.viedemerde.fr/1.0/view/";
	
	public final static String RANDOM_URL = URL + "random";
	
	public String getRandomVDM() {
		return StringEscapeUtils.unescapeHtml(WS.url(RANDOM_URL).get().getString());
	}
	
	public String getRandomVDMs(int nbr) {
		return StringEscapeUtils.unescapeHtml(WS.url(RANDOM_URL + "/" + nbr).get().getString());
	}
}

