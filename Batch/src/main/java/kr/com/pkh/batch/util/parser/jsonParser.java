package kr.com.pkh.batch.util.parser;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.com.pkh.batch.util.json.JSONArray;
import kr.com.pkh.batch.util.json.JSONObject;



public class jsonParser
{
	public String listJsonParser(List<Object> list) throws DataAccessException
	{
		String jsonStr = null;
		try {
			ObjectMapper om = new ObjectMapper();
			jsonStr = om.writeValueAsString(list);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}

	public String ajaxPaser(List<Object> value) throws DataAccessException
	{
		JSONObject cel = new JSONObject();
		Object clas = value.get(0);
		Field[] fields = clas.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				cel.put(fields[i].getName(), fields[i].get(clas));
			} catch (IllegalArgumentException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cel.toString();
	}

	public String ajaxPaserArray(List<Object> value) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONObject cel = new JSONObject();
		JSONArray cell = new JSONArray();

		for (int j = 0; j < value.size(); j++) {
			Object clas = value.get(j);
			Field[] fields = clas.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					cel.put(fields[i].getName(), fields[i].get(clas));

				} catch (IllegalArgumentException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
			}
			cell.add(cel);
		}
		return cell.toString();
	}

	public static String ajaxPaserArray(List<Object> value, List<String> code) throws DataAccessException
	{
		JSONObject cel = new JSONObject();
		JSONArray cell = new JSONArray();
		JSONObject codeCell;
		JSONArray objCell;
		JSONObject idobj;
		JSONArray idArr;

		for (int j = 0; j < value.size(); j++) {
			codeCell = new JSONObject();
			objCell = new JSONArray();
			idobj = new JSONObject();
			idArr = new JSONArray();
			List clas = (List) value.get(j);
			for (int k = 0; k < clas.size(); k++) {
				Object cla = clas.get(k);
				cel.put((String) "id", cla);
				objCell.add(cel);
			}
			codeCell.put((String) "code", objCell);
			idobj.put((String) "codeid", code.get(j));
			idArr.add(codeCell);
			idArr.add(idobj);
			cell.add(idArr);
		}

		return cell.toString();
	}

	public static String StateTreeJson(List<Object> node) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONObject cel = new JSONObject();
		JSONObject celsub = null;
		JSONArray cell = new JSONArray();

		for (int j = 0; j < node.size(); j++) {
			Object clas = node.get(j);
			Field[] fields = clas.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);

				try {
					celsub = new JSONObject();

					if ("data".equals(fields[i].getName())) {
						cel.put(fields[i].getName(), fields[i].get(clas));
						continue;
					}
					if ("metadata".equals(fields[i].getName())) {
						cel.put(fields[i].getName(), (String) "{  \"id\" : " + "\" " + fields[i].get(clas) + " \" }");

						// cel.put(fields[i].getName(), celsub.put("id",fields[i].get(clas)));
					}
				} catch (IllegalArgumentException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}

			}

			cell.add(cel);
		}

		return cell.toString();
	}

	public static String TreeJson(List<Object> node) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONObject cel = new JSONObject();
		JSONObject metaObj = null;
		JSONArray cell = new JSONArray();
		JSONArray meta = new JSONArray();
		for (int j = 0; j < node.size(); j++) {
			Object clas = node.get(j);
			Field[] fields = clas.getClass().getDeclaredFields();
			metaObj = new JSONObject();
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				try {
					if ("metadata".equals(fields[i].getName())) {
						metaObj.put((Object) "id", fields[i].get(clas));
						continue;
					}
					if ("data".equals(fields[i].getName())) {
						cel.put(fields[i].getName(), fields[i].get(clas));
						continue;
					}
					if ("metadata2".equals(fields[i].getName())) {
						metaObj.put((Object) "id2", fields[i].get(clas));
						continue;
					}
					if ("metadata3".equals(fields[i].getName())) {
						metaObj.put((Object) "id3", fields[i].get(clas));
						continue;
					}
					if ("metadata4".equals(fields[i].getName())) {
						metaObj.put((Object) "id4", fields[i].get(clas));
						continue;
					}
					if ("metadata5".equals(fields[i].getName())) {
						metaObj.put((Object) "id5", fields[i].get(clas));
						continue;
					}					
					if ("metadata6".equals(fields[i].getName())) {
						metaObj.put((Object) "id6", fields[i].get(clas));
						continue;
					}					
					
					if ("state".equals(fields[i].getName())) {
						cel.put(fields[i].getName(), fields[i].get(clas));
					}
					cel.put((Object) "metadata", metaObj);
					// cel.put(fields[i].getName(), celsub.put("id",fields[i].get(clas)));
				} catch (IllegalArgumentException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}

			}
			cell.add(cel);
		}

		return cell.toString();

	}

	public String jqgridAjaxPaser(List<String> key, List<Object> objList, String Count) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONArray cell = new JSONArray();
		JSONObject obj = new JSONObject();
		for (int j = 0; j < objList.size(); j++) {
			for (int i = 0; i < key.size(); i++) {
				Object clas = objList.get(j);
				Field[] fields = objList.get(j).getClass().getDeclaredFields();
				for (int f = 0; f < fields.length; f++) {
					fields[f].setAccessible(true);
					try {
						if (key.get(i).equals(fields[f].getName())) {
							obj.put(key.get(i), fields[f].get(clas));
						}
					} catch (IllegalArgumentException e) {
						// FIXME Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// FIXME Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			cell.add(obj);
		}

		jobj.put("total", Count);
		jobj.put("rows", cell);

		return jobj.toString();
	}

	public static String jqgridPaser(List<String> key, List<Object> objList) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONArray cell = new JSONArray();
		JSONObject obj = new JSONObject();

		for (int j = 0; j < objList.size(); j++) {
			for (int i = 0; i < key.size(); i++) {
				Object clas = objList.get(j);
				Field[] fields = objList.get(j).getClass().getDeclaredFields();
				if (clas instanceof Map) {
					Map ObjMap = (Map) clas;
					for (int f = 0; f < objList.size(); f++) {
						try {
							String emptyChk = (String) ObjMap.get((String) key.get(i));
							if ((emptyChk != null) && !(emptyChk.equals(""))) {
								obj.put(key.get(i), ObjMap.get((String) key.get(i)));
							}
						} catch (IllegalArgumentException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					for (int f = 0; f < fields.length; f++) {
						fields[f].setAccessible(true);
						try {
							if (key.get(i).equals(fields[f].getName())) {
								obj.put(key.get(i), fields[f].get(clas));
							}
						} catch (IllegalArgumentException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

			}
			cell.add(obj);
		}

		jobj.put("total", objList.size());
		jobj.put("records", objList.size());
		jobj.put("rows", cell);

		return jobj.toString();
	}

	public static String apiPaser(List<String> key, List<Object> objList, int status) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONArray cell = new JSONArray();
		JSONObject obj = new JSONObject();

		for (int j = 0; j < objList.size(); j++) {
			for (int i = 0; i < key.size(); i++) {
				Object clas = objList.get(j);
				Field[] fields = objList.get(j).getClass().getDeclaredFields();
				if (clas instanceof Map) {
					Map ObjMap = (Map) clas;
					for (int f = 0; f < objList.size(); f++) {
						try {
							String emptyChk = (String) ObjMap.get((String) key.get(i));
							if ((emptyChk != null) && !(emptyChk.equals(""))) {
								obj.put(key.get(i), ObjMap.get((String) key.get(i)));
							}
						} catch (IllegalArgumentException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else {
					for (int f = 0; f < fields.length; f++) {
						fields[f].setAccessible(true);
						try {
							if (key.get(i).equals(fields[f].getName())) {
								obj.put(key.get(i), fields[f].get(clas));
							}
						} catch (IllegalArgumentException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						}

					}
				}

			}
			cell.add(obj);
		}

		jobj.put("status", status);
		jobj.put("data", cell);

		return jobj.toString();
	}

	public String jqgridDatePaser(List<String> key, List<Object> objList, int total) throws DataAccessException
	{
		JSONObject jobj = new JSONObject();
		JSONArray cell = new JSONArray();
		JSONObject obj = new JSONObject();
		for (int j = 0; j < objList.size(); j++) {
			for (int i = 0; i < key.size(); i++) {
				Object clas = objList.get(j);
				Field[] fields = objList.get(j).getClass().getDeclaredFields();
				for (int f = 0; f < fields.length; f++) {
					fields[f].setAccessible(true);
					try {
						if (key.get(i).equals(fields[f].getName())) {
							obj.put(key.get(i), fields[f].get(clas));
						}
					} catch (IllegalArgumentException e) {
						// FIXME Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// FIXME Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			cell.add(obj);
		}

		jobj.put("total", objList.size());
		jobj.put("records", objList.size());
		jobj.put("rows", cell);
		jobj.put("rept30date", total);

		return jobj.toString();
	}
}
