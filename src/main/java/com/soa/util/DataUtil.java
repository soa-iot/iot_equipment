package com.soa.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.soa.cloudplatform.DataObjectAccess.dataobj.RDBDataObject;
import com.soa.cloudplatform.DataObjectAccess.util.DataObjectUtil;
import com.soa.cloudplatform.DataObjectAccess.util.SystemData;
import com.soa.cloudplatform.DataObjectAccess.util.bean.DOFieldBean;
import com.soa.cloudplatform.dataaccess.common.datacondition.AbstractCondition;
import com.soa.cloudplatform.dataaccess.common.datacondition.BaseCondition;
import com.soa.cloudplatform.dataaccess.common.datacondition.CompareOperatorType;
import com.soa.cloudplatform.dataaccess.common.datameta.FetchField;
import com.soa.cloudplatform.rdb.base.AbstractRDBAccess;
import com.soa.cloudplatform.rdb.base.SoaSystemRDBFactory;
import com.soa.cloudplatform.rdb.base.exception.SOAException;

public class DataUtil {
	/**
	 * 根据传入sql以JSONArray格式返回结果
	 * 
	 * @param sql
	 * @return
	 */
	public static JSONArray getData(String sql) {
		JSONArray data = new JSONArray();
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		AbstractRDBAccess access;
		try {
			access = factory.getAbstractRDBAccess();
			data = access.doSelectSqlAsJSON(sql);
		} catch (SOAException e1) {
			e1.printStackTrace();
		}
		return data;
	}

	/**
	 * 执行SQL
	 * @param sql
	 * @return
	 */
	public static int doExecuteSql(String sql) {
		int cols = 0;
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		AbstractRDBAccess access;
		try {
			access = factory.getAbstractRDBAccess();
			cols = access.doExecuteSql(sql);
		} catch (SOAException e1) {
			cols = -1;
		}
		return cols;
	}

	/**
	 * 批处理Sql
	 * @param sqls
	 * @return
	 */
	public static int doExecuteSqlBatch(List<String> sqls) {
		int cols = 0;
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		AbstractRDBAccess access;
		try {
			access = factory.getAbstractRDBAccess();
			cols = access.doExecuteBatchSql(sqls);
		} catch (SOAException e1) {
			cols = -1;
		}
		return cols;
	}

	/**
	 * 执行数据库查询，并转换为对象List<Map<String, Object>>
	 * 
	 * 
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getDataListMap(String sql) {
		// 转存的maps
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		JSONArray datas = getData(sql);
		if (datas != null && datas.length() > 0) {
			try {
				int dataLen = datas.length();
				for (int i = 0; i < dataLen; i++) {
					Map<String, Object> map = new HashMap<String, Object>();

					JSONObject obj = (JSONObject) datas.get(i);
					Iterator<String> sIterator = obj.keys();
					while (sIterator.hasNext()) {
						// 获得key
						String key = sIterator.next();
						// 根据key获得value,
						// value也可以是JSONObject,JSONArray,使用对应的参数接收即可
						Object value = obj.get(key);
						map.put(key, value);
					}
					maps.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maps;
	}

	public static int saveData(String sql) {
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		AbstractRDBAccess access;
		int count = 0;
		try {
			access = factory.getAbstractRDBAccess();
			count = access.doExecuteSql(sql);
		} catch (SOAException e1) {
			count = 0;
		}
		return count;
	}

	/**
	 * 得到查询总条数
	 * 
	 * @param sql
	 *            sql句
	 * @param keyName
	 *            sql句中count别名 如：select count(*) Total from tab; 则传入TOTAL
	 * @return
	 */
	public static int getTotal(String sql, String keyName) {
		int total = 0;
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRDBAccess access;
		try {
			access = factory.getAbstractRDBAccess();
			map = access.doSelectOneRowSql(sql);
			total = Integer.parseInt(map.get(keyName).toString());
		} catch (SOAException e1) {
			e1.printStackTrace();
		} catch (NumberFormatException e) {
			total = 0;
		}
		return total;
	}

	/**
	 * 得到查询一个结果
	 * 
	 * @param sql
	 *            sql句
	 * @param keyName
	 *            sql句中的别名 如：select field_val oneVal from tab; 则传入ONEVAL
	 * @return
	 */
	public static String getOneString(String sql, String keyName) {
		String total = "";
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		Map<String, Object> map = new HashMap<String, Object>();
		AbstractRDBAccess access;
		try {
			access = factory.getAbstractRDBAccess();
			map = access.doSelectOneRowSql(sql);
			total = map.get(keyName).toString();
		} catch (SOAException e1) {
			e1.printStackTrace();
		}
		return total;
	}

	/**
	 * sql条件拼凑，目前只支持like和=
	 * 
	 * @param request
	 *            请求
	 * @param keyMap
	 *            字段名和操作符，如：<id,equals>,<name,like>
	 * @return
	 */
	public static String getCondition(HttpServletRequest request,
			Map<String, String> keyMap) {
		StringBuffer sqlCondition = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		for (String key : keyMap.keySet()) {
			map.put(key, request.getParameter(key));
		}
		for (String key : map.keySet()) {
			String value = map.get(key);
			String operator = keyMap.get(key);
			if (value != null && !"".equals(value.trim()) && operator != null) {
				switch (OperatorType.valueOf(operator)) {
				case equals:
					sqlCondition.append("AND " + key + " = '" + value + "' ");
					break;
				case like:
					sqlCondition.append("AND " + key + " like '%" + value
							+ "%' ");
					break;
				}
			}
		}
		return sqlCondition.toString();
	}

	/**
	 * 分页时获取查询条数
	 * 
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示条数
	 * @return
	 */
	public static int[] getLimitRows(String page, String rows) {
		int[] rowsNum = { 1, 20 };
		int currentPage = 1; // 当前页
		int currentRows = 20; // 当前显示数
		if (page != null && rows != null) {
			try {
				currentPage = Integer.parseInt(page);
				currentRows = Integer.parseInt(rows);
			} catch (NumberFormatException e) {
				currentPage = 1;
				currentRows = 20;
			}
		}
		rowsNum[0] = (currentPage - 1) * currentRows + 1;
		rowsNum[1] = currentPage * currentRows;
		return rowsNum;
	}

	/**
	 * 获取附件信息
	 * 
	 * @param tableName
	 *            附件表名
	 * @param keyName
	 *            主键名
	 * @param keyValue
	 *            主键值
	 * @param typeName
	 *            类型字段
	 * @return
	 * @throws JSONException
	 */
	
	public static JSONObject getAttachments(String tableName, String keyName,
			String keyValue, String typeName) throws JSONException {
		String sql = "SELECT * FROM " + tableName + " WHERE " + keyName
				+ " = '" + keyValue + "' ";
		JSONArray data = DataUtil.getData(sql);
		JSONArray imgsData = new JSONArray();
		JSONArray vediosData = new JSONArray();
		JSONObject returnJson = new JSONObject();
		JSONObject json = null;
		int type = 0;
		for (int i = 0, len = data.length(); i < len; i++) {
			json = data.getJSONObject(i);
			try {
				type = json.getInt(typeName);
			} catch (Exception e) {
				type = 0;
			}
			switch (type) {
			case 0:
				imgsData.put(json);
				break;
			case 1:
				vediosData.put(json);
			default:
				break;
			}
		}
		returnJson.put("imgsData", imgsData);
		returnJson.put("vediosData", vediosData);
		return returnJson;
	}

	/**
	 * 通用的修改方法，弥补了RDB中update方法不能控制事务的问题
	 * 
	 * @param userID
	 * @param dataObjectID
	 * @param data
	 * @return
	 */
	public static boolean update(String userID, String dataObjectID,
			JSONArray data) {
		boolean flag = true;
		SoaSystemRDBFactory factory = new SoaSystemRDBFactory();
		AbstractRDBAccess access = null;
		try {
			access = factory.getAbstractRDBAccess();
			access.absConnection.openConnection();
			access.absConnection.getConnection().setAutoCommit(false);
			RDBDataObject dataObject = new RDBDataObject();

			String tableName = dataObject
					.getTableNameByDataObjectID(dataObjectID);
			FetchField fields = dataObject
					.getFetchFieldsByDataObjectID(dataObjectID);
			DOFieldBean[] beans = SystemData.DoFieldMap.get(dataObjectID);
			String pkField = DataObjectUtil.getPKFieldName(beans);
			int count = 0;
			for (int i = 0, j = data.length(); i < j; i++) {
				JSONObject json = data.getJSONObject(i);
				AbstractCondition baseCondition = new BaseCondition(pkField,
						CompareOperatorType.equals, json.getString(pkField));
				count = access.rdb_update(tableName, fields,
						new JSONArray().put(json), baseCondition);
				if (count < 1) {
					flag = false;
					break;
				}
			}

		} catch (SOAException e) {
			rollBack(access);
			flag = false;
			e.printStackTrace();
		} catch (SQLException e) {
			rollBack(access);
			flag = false;
			e.printStackTrace();
		} catch (JSONException e) {
			rollBack(access);
			flag = false;
			e.printStackTrace();
		}
		if (flag) {
			flag = commit(access);
		}
		closeConnection(access);
		return flag;

	}

	private static void rollBack(AbstractRDBAccess access) {
		if (access != null) {
			try {
				access.absConnection.getConnection().rollback();
			} catch (SOAException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean commit(AbstractRDBAccess access) {
		boolean flag = true;
		if (access != null) {
			try {
				access.absConnection.getConnection().commit();
			} catch (SOAException e) {
				flag = false;
				e.printStackTrace();
			} catch (SQLException e) {
				flag = false;
				e.printStackTrace();
			}
		}
		return flag;
	}

	private static void closeConnection(AbstractRDBAccess access) {
		try {
			access.absConnection.closeConnection();
		} catch (SOAException e) {
			e.printStackTrace();
		}
	}
}
