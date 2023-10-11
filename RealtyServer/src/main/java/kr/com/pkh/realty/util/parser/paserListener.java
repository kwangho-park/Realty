package kr.com.pkh.realty.util.parser;

import java.util.List;

public interface paserListener  {
	public String listXmlParser(List<Object> list);
	public String listJsonParser(List<Object> list);
}
