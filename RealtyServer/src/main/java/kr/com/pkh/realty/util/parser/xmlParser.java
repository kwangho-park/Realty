package kr.com.pkh.realty.util.parser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;



public class xmlParser {
	
	protected final Log logger = LogFactory.getLog(getClass());

	public String listXMLParser(List<Object> list) 
	{
		
		Marshaller marshaller = null;
		StringWriter result = new StringWriter();
		try {
			Object obj = list.get(0);
			marshaller = JAXBContext.newInstance(obj.getClass()).createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING,"utf-8" );
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			marshaller.setProperty(marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			result.append("<?xml version=\"1.0\" encoding=\"EUC-KR\"?>\r\n");
			for(int i=0; i<list.size(); i++){
				Object ob = list.get(i);
				marshaller.marshal(ob,result);
				result.append("\n");
			}
		} catch (JAXBException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(result.toString());
		return result.toString();
		
	}
	
}
