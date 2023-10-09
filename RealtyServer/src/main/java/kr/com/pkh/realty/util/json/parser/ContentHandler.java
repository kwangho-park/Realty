package kr.com.pkh.realty.util.json.parser;

import java.io.IOException;

public interface ContentHandler {

	public abstract void startJSON() throws ParseException, IOException;

	public abstract void endJSON() throws ParseException, IOException;

	public abstract boolean startObject() throws ParseException, IOException;

	public abstract boolean endObject() throws ParseException, IOException;

	public abstract boolean startObjectEntry(String s) throws ParseException, IOException;

	public abstract boolean endObjectEntry() throws ParseException, IOException;

	public abstract boolean startArray() throws ParseException, IOException;

	public abstract boolean endArray() throws ParseException, IOException;

	public abstract boolean primitive(Object obj) throws ParseException, IOException;
}