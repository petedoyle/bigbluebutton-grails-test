package login

import groovyx.net.http.URIBuilder;
import java.security.MessageDigest;

class ApiService {

	static transactional = true
	private static final API_BASE_URL = "http://conf.cotni.org/bigbluebutton/api/"
	private static final String SALT = "26f31c52-6d4f-4562-b3c7-fd7857d191ac"
	
	def messageDigest = MessageDigest.getInstance( "SHA1" )
	
	def getSignedApiUrl(String command, Map params) {
		API_BASE_URL + command + buildQueryString( command, params ) + "&checksum=" + calculateChecksumValue( command, params )
	}
	
	/**
	 * Returns the query string without the &checksum= parameter 
	 */
	private String buildQueryString(String command, Map params) {
		"?" + params.collect { it.key + "=" + it.value }.join("&")
	}
	
	/**
	 * Returns the checksum value for a given API call 
	 */
	private String calculateChecksumValue(String command, Map params) {
		String checksumString = buildChecksumString( command, params )
		
		new BigInteger( messageDigest.digest( checksumString.getBytes() ) ).toString(16)
	}
	
	/**
	 * Builds the checksum string in the form of {command}+{query string}+{salt}.
	 * example: joinfullName=Les&meetingID=71000&password=888826f31c52-6d4f-4562-b3c7-fd7857d191ac
	 */
	private String buildChecksumString(String command, Map params) {
		command + params.collect { it.key + "=" + it.value }.join("&") + SALT
	}
}
