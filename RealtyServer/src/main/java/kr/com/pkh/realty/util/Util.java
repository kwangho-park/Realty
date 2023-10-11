package kr.com.pkh.realty.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import jakarta.servlet.http.HttpServletRequest;

public class Util
{

	public static void zeroize(StringBuilder str)
	{
		if (str != null) {
			for (int i = 0; i < str.length(); i++)
				str.setCharAt(i, '0');
		}

		return;
	}

	public static void zeroize(StringBuffer str)
	{
		if (str != null) {
			for (int i = 0; i < str.length(); i++)
				str.setCharAt(i, '0');
		}

		return;
	}

	public static byte[] zeroize(byte[] input)
	{
		if (input == null)
			return input;

		for (int i = 0; i < input.length; i++)
			input[i] = 0x00;

		input = null;

		return input;
	}

	public static int bytesToInt(byte[] src, int srcOff)
	{
		int word = 0;

		for (int i = 0; i < 4; i++)
			word = (word << 8) + (src[i + srcOff] & 0xff);

		return word;
	}

	public static byte[] intToBytes(int value, int destOff)
	{
		byte[] bytes = new byte[4];

		for (int i = 0; i < 4; i++)
			bytes[i + destOff] = (byte) (value >> ((3 - i) * 8));

		return bytes;
	}

	public static int bytes2SaltInt(byte[] src, int srcOff, byte[] salt)
	{
		int word = 0;

		for (int j = 0; j < 4; j++)
			src[j] ^= ~salt[j];

		for (int i = 0; i < 4; i++)
			word = (word << 8) + (src[i + srcOff] & 0xff);

		return word;
	}

	public static byte[] int2SaltBytes(int value, int destOff, byte[] salt)
	{
		byte[] bytes = new byte[4];

		for (int i = 0; i < 4; i++)
			bytes[i + destOff] = (byte) (value >> ((3 - i) * 8));

		for (int j = 0; j < 4; j++)
			bytes[j] ^= ~salt[j];

		return bytes;
	}

	public static boolean isEmpty(String str)
	{
		return str == null || str.equals("");
	}

	public static byte[] concatBytes(byte[] firstBytes, byte[] nextBytes)
	{
		byte[] bytes = new byte[firstBytes.length + nextBytes.length];

		System.arraycopy(firstBytes, 0, bytes, 0, firstBytes.length);
		System.arraycopy(nextBytes, 0, bytes, firstBytes.length, nextBytes.length);
		return bytes;
	}

	public static void splitBytes(byte[] source, byte[] firstBytes, byte[] nextBytes)
	{
		System.arraycopy(source, 0, firstBytes, 0, firstBytes.length);
		System.arraycopy(source, firstBytes.length, nextBytes, 0, nextBytes.length);
	}

	public static boolean compareBytes(byte[] source, byte[] dest)
	{
		if (source.length != dest.length)
			return false;

		for (int i = 0; i < source.length; i++)
			if (source[i] != dest[i])
				return false;

		return true;
	}

	public static String getURL(HttpServletRequest request, String path)
	{
		URL returnURL = null;

		try {
			returnURL = new URL(path);
		}
		catch (MalformedURLException e) {
			int sport = request.getServerPort();

			try {
				if (sport == 80)
					returnURL = new URL(request.getScheme() + "://" + request.getServerName() + path);
				else
					returnURL = new URL(request.getScheme() + "://" + request.getServerName() + ":" + sport + path);
			}
			catch (Exception ex) {
				if (sport == 80)
					return request.getScheme() + "://" + request.getServerName() + "/" + path;
				else
					return request.getScheme() + "://" + request.getServerName() + ":" + sport + "/" + path;
			}
		}

		return returnURL.toString();
	}

	public static String generateUUID()
	{
		UUID uudi = UUID.randomUUID();
		return "IDP-" + uudi.toString();
	}

	public static long getTime()
	{
		return System.currentTimeMillis();
	}

	public static String getDateFormat(String format)
	{
		DateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String addDate(String baseDate, String pattern, int field, int amount)
	{
		String result = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTime(dateFormat.parse(baseDate));
			calendar.add(field, amount);
			result = dateFormat.format(calendar.getTime());
		}
		catch (ParseException e) {
			result = baseDate;
		}

		return result;
	}


	public static String getBaseURL(HttpServletRequest request)
	{
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	public static String domToStr(Document doc, boolean indent)
	{
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();

			String encoding = new OutputStreamWriter(System.out).getEncoding();

			if (!"UTF8".equals(encoding)) {
				transformer.setOutputProperty(OutputKeys.ENCODING, "EUC-KR");
			}

			if (indent) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			}

			transformer.transform(domSource, result);
			return writer.toString();
		}
		catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String domToStr(Document doc, boolean indent, String encoding)
	{
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();

			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);

			if (indent) {
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			}

			transformer.transform(domSource, result);
			return writer.toString();
		}
		catch (TransformerException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Document createDomDoc(String xmlString)
	{
		DocumentBuilderFactory objDocBuilderFactory = DocumentBuilderFactory.newInstance();
		objDocBuilderFactory.setNamespaceAware(true);

		try {
			DocumentBuilder objDocBuilder = objDocBuilderFactory.newDocumentBuilder();
			return objDocBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Document createDomDoc(byte[] xmlByte)
	{
		DocumentBuilderFactory objDocBuilderFactory = DocumentBuilderFactory.newInstance();
		objDocBuilderFactory.setNamespaceAware(true);

		try {
			DocumentBuilder objDocBuilder = objDocBuilderFactory.newDocumentBuilder();
			return objDocBuilder.parse(new ByteArrayInputStream(xmlByte));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static X509Certificate getCert(String certFilepath)
	{
		try {
			InputStream pubKey = new FileInputStream(certFilepath);
			CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");

			return (X509Certificate) certificateFactory.generateCertificate(pubKey);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getAttribute(HttpServletRequest request, String key)
	{
		String result = request.getAttribute(key) == null ? "" : (String) request.getAttribute(key);

		if (isEmpty(result)) {
			result = request.getParameter(key) == null ? "" : request.getParameter(key);
		}

		return result;
	}

	
	// 사용자 PW 유효성검사 
	// 8자리 이상 , 영문 대문자/소문자/특수문자 최소 1개 이상 포함
	public static boolean isPWvalidation(String pw) {
		
		
		String pattern="^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
		
		boolean regex = Pattern.matches(pattern, pw);
		
		return regex;
	}

	
	// VPN subnet -> route value  타입변경 
	// ex 10.10.10.177/24 -> 10.10.10.177 255.255.255.0
//	public static String convertToRouteType(String IPsubnet) {
//	
//		
//		SubnetUtils subnetUtils = new SubnetUtils(IPsubnet);    
//		subnetUtils.setInclusiveHostCount(true);				// subnet mask 포함 설정  (ex 10.10.10.177/24)
//
//		String ip = subnetUtils.getInfo().getAddress();
//		String netMask = subnetUtils.getInfo().getNetmask();
//
//
//		String RouteTypeValue = ip + " " + netMask;
//
//		return RouteTypeValue;
//		
//	}
	
	
}