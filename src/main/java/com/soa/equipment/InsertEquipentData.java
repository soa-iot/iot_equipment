
/**  
* @Title: InsertEquipentData.java
* @Package com.soa.CZ_PIOTMS.server
* @Description: TODO(用一句话描述该文件做什么)
* @author yanghua
* @date 2018年10月28日
* @version V1.0  
*/

package com.soa.equipment;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.soa.util.DataUtil;

/**
 * @ClassName: InsertEquipentData
 * @Description: 插入一行设备模块数据
 * @author yanghua
 * @date 2018年10月28日
 *
 */

public class InsertEquipentData extends HttpServlet {
	/**
	 * @Fields serialVersionUID : 序列化
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 通用
		String WEL_NAME = request.getParameter("WEL_NAME");
		String WEL_UNIT = request.getParameter("WEL_UNIT");
		String EQU_MEMO_ONE = request.getParameter("EQU_MEMO_ONE");
		String EQU_POSITION_NUM = request.getParameter("EQU_POSITION_NUM");
		String EQU_NAME = request.getParameter("EQU_NAME");

		// 设备大类
		String SECONDCLASS_EQUIPMENT = request.getParameter("SECONDCLASS_EQUIPMENT");

		// 压力表
		String MANAGE_TYPE = request.getParameter("MANAGE_TYPE");
		String EQU_MODEL = request.getParameter("EQU_MODEL");
		String MEARING_RANGE = request.getParameter("MEARING_RANGE");
		String MEASURE_ACC = request.getParameter("MEASURE_ACC");
		String EQU_INSTALL_POSITION = request.getParameter("EQU_INSTALL_POSITION");
		String EQU_MANUFACTURER = request.getParameter("EQU_MANUFACTURER");
		String SERIAL_NUM = request.getParameter("SERIAL_NUM");
		String CHECK_CYCLE = request.getParameter("CHECK_CYCLE");
		String CHECK_TIME = request.getParameter("CHECK_TIME");
		String NEXT_CHECK_TIME = request.getParameter("NEXT_CHECK_TIME");
		String REMARK1 = request.getParameter("REMARK1");

		// 压力差压变送器
		String MEDUIM_TYPE = request.getParameter("MEDUIM_TYPE");

		String EQU_PRODUC_DATE = request.getParameter("EQU_PRODUC_DATE");
		String EQU_COMMISSION_DATE = request.getParameter("EQU_COMMISSION_DATE");
		String EQU_WORK_TEMP = request.getParameter("EQU_WORK_TEMP");
		String EQU_LASTPERIODIC_DATE = request.getParameter("EQU_LASTPERIODIC_DATE");
		String EQU_PERIODIC_CYCLE = request.getParameter("EQU_PERIODIC_CYCLE");
		String EQU_PERIODIC_WARNDAYS = request.getParameter("EQU_PERIODIC_WARNDAYS");
		String PRESSURE_RANGE = request.getParameter("PRESSURE_RANGE");
		String EXPERY_TIME = request.getParameter("EXPERY_TIME");
		String INTER_SIZE = request.getParameter("INTER_SIZE");
		String DEEP_LENGTH = request.getParameter("DEEP_LENGTH");
		String ORDER_NUM = request.getParameter("ORDER_NUM");
		String VAVLE_TYPE = request.getParameter("VAVLE_TYPE");
		String ACTION_MODLE = request.getParameter("ACTION_MODLE");
		String HAVE_NOT = request.getParameter("HAVE_NOT");
		String ACTUAL_MODEL = request.getParameter("ACTUAL_MODEL");
		String ACTUAL = request.getParameter("ACTUAL");
		String ELEC_MODEL = request.getParameter("ELEC_MODEL");
		String ELEC = request.getParameter("ELEC");
		String MEASURE_PRIN = request.getParameter("MEASURE_PRIN");
		String PIPE_OUTER = request.getParameter("PIPE_OUTER");
		String FLUX = request.getParameter("FLUX");
		String PIPE_THICK = request.getParameter("PIPE_THICK");
		String CV = request.getParameter("CV");
		String POSITIONER = request.getParameter("POSITIONER");
		String GAS_SOURCE = request.getParameter("GAS_SOURCE");
		String FLA_SIZE = request.getParameter("FLA_SIZE");
		String PROCE_LINK_TYPE = request.getParameter("PROCE_LINK_TYPE");
		String UNIT = request.getParameter("UNIT");
		String COUNT = request.getParameter("COUNT");
		String POWER_RATE = request.getParameter("POWER_RATE");
		String ELECTRIC_PRES = request.getParameter("ELECTRIC_PRES");
		String ELECTRIC_TENSION = request.getParameter("ELECTRIC_TENSION");
		String FREQUENCY = request.getParameter("FREQUENCY");
		String BRAND = request.getParameter("BRAND");
		String CAPCITY = request.getParameter("CAPCITY");
		String SPEED_RAT = request.getParameter("SPEED_RAT");
		String BEFORE_BEARING1 = request.getParameter("BEFORE_BEARING1");
		String BEFORE_BEARING2 = request.getParameter("BEFORE_BEARING2");
		String AFTER_BEARING1 = request.getParameter("AFTER_BEARING1");
		String GREASE_INTERV = request.getParameter("GREASE_INTERV");
		String GREASE_QUAN = request.getParameter("GREASE_QUAN");
		String INSULATION_RATE = request.getParameter("INSULATION_RATE");
		String PROTECTION_RATE = request.getParameter("PROTECTION_RATE");
		String EXPLOSION_RATE = request.getParameter("EXPLOSION_RATE");
		String DEVICE_TYPE = request.getParameter("DEVICE_TYPE");
		String HEIGHT_ELECTRIC_TENSION = request.getParameter("HEIGHT_ELECTRIC_TENSION");
		String PHASE_NUMBER = request.getParameter("PHASE_NUMBER");
		String CONNECTION_GROUP = request.getParameter("CONNECTION_GROUP");
		String HEIGHT_ELECTRIC_PRES = request.getParameter("HEIGHT_ELECTRIC_PRES");
		String WEIGHT = request.getParameter("WEIGHT");
		String SNATCH_ELECTRIC_PRES = request.getParameter("SNATCH_ELECTRIC_PRES");
		String THUNDERSTRIKE_ELECTRIC_PRES = request.getParameter("THUNDERSTRIKE_ELECTRIC_PRES");
		String SNATCH_ELECTRIC_TENSION = request.getParameter("SNATCH_ELECTRIC_TENSION");
		String PEAK_TENSION = request.getParameter("PEAK_TENSION");
		String CATEGORY = request.getParameter("CATEGORY");
		String MATERIAL = request.getParameter("MATERIAL");
		String CORROSION_FATIGUE = request.getParameter("CORROSION_FATIGUE");
		String EQU_DESIGN_TEMP = request.getParameter("EQU_DESIGN_TEMP");
		String DESIGN_PRESSURE_RANGE = request.getParameter("DESIGN_PRESSURE_RANGE");
		String SURFACE_HEAT_TRANSFER = request.getParameter("SURFACE_HEAT_TRANSFER");
		String DESIGN_SHELL_PRES = request.getParameter("DESIGN_SHELL_PRES");
		String DESIGN_SHELL_TEMP = request.getParameter("DESIGN_SHELL_TEMP");
		String DESIGN_TUBE_TEMP = request.getParameter("DESIGN_TUBE_TEMP");
		String DESIGN_TUBE_PRES = request.getParameter("DESIGN_TUBE_PRES");
		String OPTION_SHELL_PRESS = request.getParameter("OPTION_SHELL_PRESS");
		String OPTION_SHELL_IN_TEMP = request.getParameter("OPTION_SHELL_IN_TEMP");
		String OPTION_SHELL_OUT_TEMP = request.getParameter("OPTION_SHELL_OUT_TEMP");
		String OPTION_SHELL_MEDUIM = request.getParameter("OPTION_SHELL_MEDUIM");
		String OPTION_TUBE_IN_TEMP = request.getParameter("OPTION_TUBE_IN_TEMP");
		String OPTION_TUBE_OUT_TEMP = request.getParameter("OPTION_TUBE_OUT_TEMP");
		String OPTION_TUBE_MEDUIM = request.getParameter("OPTION_TUBE_MEDUIM");
		String SHELL_MATERIAL = request.getParameter("SHELL_MATERIAL");
		String TUBE_MATERIAL = request.getParameter("TUBE_MATERIAL");
		String HIGH_LEFT = request.getParameter("HIGH_LEFT");
		String DISPLACEMENT = request.getParameter("DISPLACEMENT");
		String SPINDLE_SPEED = request.getParameter("SPINDLE_SPEED");
		String IMPELLER_MEDUIM = request.getParameter("IMPELLER_MEDUIM");
		String SPINDLE_MEDUIM = request.getParameter("SPINDLE_MEDUIM");
		String PUMP_MEDUIM = request.getParameter("PUMP_MEDUIM");
		String WIND_PRESSURE = request.getParameter("WIND_PRESSURE");
		String ENGINE_NUMBER = request.getParameter("ENGINE_NUMBER");
		String LICENSE_NUMBER = request.getParameter("LICENSE_NUMBER");
		String CHASSIS_NUMBER = request.getParameter("CHASSIS_NUMBER");
		String ENERGY_CONSUMPTION = request.getParameter("ENERGY_CONSUMPTION");
		String ENERGY_CONSUMPTION_CAT = request.getParameter("ENERGY_CONSUMPTION_CAT");
		String EQU_MEMO_TWO = request.getParameter("EQU_MEMO_TWO");
		String EQU_MEMO_THREE = request.getParameter("EQU_MEMO_THREE");
		String EQU_WHETHER_PERIODIC = request.getParameter("EQU_WHETHER_PERIODIC");
		String EQUIPMENT_ATTACH_URL = request.getParameter("EQUIPMENT_ATTACH_URL");

		// 检查设备是否存在，存在就更新
		if (EQU_POSITION_NUM != null) {
			String sql = "select count(*) as NUM  from cz_equipment_info where EQU_POSITION_NUM = '" + EQU_POSITION_NUM
					+ "'";
			System.out.println("----更新sql:---------" + sql);
			JSONArray numJsonArr = DataUtil.getData(sql);
			int i;
			try {
				i = numJsonArr.getJSONObject(0).getInt("NUM");
				System.out.println("----更新sql:i---------" + i);
				if (i > 0) {
					// 更新数据
					ModifyEquipentData modify = new ModifyEquipentData();
					String condition = modify.createUpdateCondition(WEL_NAME, WEL_UNIT, EQU_MEMO_ONE, EQU_POSITION_NUM,
							EQU_NAME, // 通用
							MANAGE_TYPE, EQU_MODEL, MEARING_RANGE, MEASURE_ACC, EQU_INSTALL_POSITION, EQU_MANUFACTURER,
							SERIAL_NUM, CHECK_CYCLE, CHECK_TIME, NEXT_CHECK_TIME, REMARK1, // 压力表
							MEDUIM_TYPE, // 压力差压变送器
							EQU_PRODUC_DATE, EQU_COMMISSION_DATE, EQU_WORK_TEMP, EQU_LASTPERIODIC_DATE,
							EQU_PERIODIC_CYCLE, EQU_PERIODIC_WARNDAYS, PRESSURE_RANGE, EXPERY_TIME, INTER_SIZE,
							DEEP_LENGTH, ORDER_NUM, VAVLE_TYPE, ACTION_MODLE, HAVE_NOT, ACTUAL_MODEL, ACTUAL,
							ELEC_MODEL, ELEC, MEASURE_PRIN, PIPE_OUTER, FLUX, PIPE_THICK, CV, POSITIONER, GAS_SOURCE,
							FLA_SIZE, PROCE_LINK_TYPE, UNIT, COUNT, POWER_RATE, ELECTRIC_PRES, ELECTRIC_TENSION,
							FREQUENCY, BRAND, CAPCITY, SPEED_RAT, BEFORE_BEARING1, BEFORE_BEARING2, AFTER_BEARING1,
							GREASE_INTERV, GREASE_QUAN, INSULATION_RATE, PROTECTION_RATE, EXPLOSION_RATE, DEVICE_TYPE,
							HEIGHT_ELECTRIC_TENSION, PHASE_NUMBER, CONNECTION_GROUP, HEIGHT_ELECTRIC_PRES, WEIGHT,
							SNATCH_ELECTRIC_PRES, THUNDERSTRIKE_ELECTRIC_PRES, SNATCH_ELECTRIC_TENSION, PEAK_TENSION,
							CATEGORY, MATERIAL, CORROSION_FATIGUE, EQU_DESIGN_TEMP, DESIGN_PRESSURE_RANGE,
							SURFACE_HEAT_TRANSFER, DESIGN_SHELL_PRES, DESIGN_SHELL_TEMP, DESIGN_TUBE_TEMP,
							DESIGN_TUBE_PRES, OPTION_SHELL_PRESS, OPTION_SHELL_IN_TEMP, OPTION_SHELL_OUT_TEMP,
							OPTION_SHELL_MEDUIM, OPTION_TUBE_IN_TEMP, OPTION_TUBE_OUT_TEMP, OPTION_TUBE_MEDUIM,
							SHELL_MATERIAL, TUBE_MATERIAL, HIGH_LEFT, DISPLACEMENT, SPINDLE_SPEED, IMPELLER_MEDUIM,
							SPINDLE_MEDUIM, PUMP_MEDUIM, WIND_PRESSURE, ENGINE_NUMBER, LICENSE_NUMBER, CHASSIS_NUMBER,
							ENERGY_CONSUMPTION, ENERGY_CONSUMPTION_CAT, EQU_MEMO_TWO, EQU_MEMO_THREE,
							EQU_WHETHER_PERIODIC, EQUIPMENT_ATTACH_URL);

					condition = condition.substring(0, condition.lastIndexOf(","))
							+ condition.substring(condition.lastIndexOf(",") + 1);
					// 检查历史操作表中是否存在，不存在就插入起始数据
					String whereCondition = condition.substring(condition.indexOf("where"), condition.length());
					String initsql = " select count(*) as NUM from  iot_equipment_operate_recode where EQU_POSITION_NUM='"
							+ EQU_POSITION_NUM + "'";
					try {
						JSONArray array = DataUtil.getData(initsql);
						int initnum = ((JSONObject) array.get(0)).getInt("NUM");
						int l = 0;
						if (initnum <= 0) {
							l = updateHisRecode(whereCondition, request, "insert");
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("----检查历史操作表中是否存在现设备的位号--------失败-");
					}

					String updateSql = "update cz_equipment_info set " + condition;
					System.out.println("----insert - updateSql:---------" + updateSql);
					int j = DataUtil.doExecuteSql(updateSql);
					System.out.println("----insert - updateSql:---------j:" + j);

					// 保存设备的增加信息备份

					System.out.println("------------更新历史记录的where条件-------whereCondition：" + whereCondition);
					int k = updateHisRecode(whereCondition, request, "update");

					JSONObject json = new JSONObject();
					if (j > 0) {
						try {
							json.put("state", 0);
							json.put("messge", "插入成功");
							json.put("data", i);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else {
						try {
							json.put("state", 1);
							json.put("messge", "插入失败");
							json.put("data", i);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					response.getWriter().write(json.toString());
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			JSONObject json = new JSONObject();
			try {
				json.put("state", 1);
				json.put("messge", "设备位号为空");
				json.put("data", 0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().write(json.toString());
		}

		String EQU_ID = UUID.randomUUID().toString();

		String condition = createInsertCondition(EQU_ID, SECONDCLASS_EQUIPMENT, WEL_NAME, WEL_UNIT, EQU_MEMO_ONE,
				EQU_POSITION_NUM, EQU_NAME, // 通用
				MANAGE_TYPE, EQU_MODEL, MEARING_RANGE, MEASURE_ACC, EQU_INSTALL_POSITION, EQU_MANUFACTURER, SERIAL_NUM,
				CHECK_CYCLE, CHECK_TIME, NEXT_CHECK_TIME, REMARK1, // 压力表
				MEDUIM_TYPE, // 压力差压变送器
				EQU_PRODUC_DATE, EQU_COMMISSION_DATE, EQU_WORK_TEMP, EQU_LASTPERIODIC_DATE, EQU_PERIODIC_CYCLE,
				EQU_PERIODIC_WARNDAYS, PRESSURE_RANGE, EXPERY_TIME, INTER_SIZE, DEEP_LENGTH, ORDER_NUM, VAVLE_TYPE,
				ACTION_MODLE, HAVE_NOT, ACTUAL_MODEL, ACTUAL, ELEC_MODEL, ELEC, MEASURE_PRIN, PIPE_OUTER, FLUX,
				PIPE_THICK, CV, POSITIONER, GAS_SOURCE, FLA_SIZE, PROCE_LINK_TYPE, UNIT, COUNT, POWER_RATE,
				ELECTRIC_PRES, ELECTRIC_TENSION, FREQUENCY, BRAND, CAPCITY, SPEED_RAT, BEFORE_BEARING1, BEFORE_BEARING2,
				AFTER_BEARING1, GREASE_INTERV, GREASE_QUAN, INSULATION_RATE, PROTECTION_RATE, EXPLOSION_RATE,
				DEVICE_TYPE, HEIGHT_ELECTRIC_TENSION, PHASE_NUMBER, CONNECTION_GROUP, HEIGHT_ELECTRIC_PRES, WEIGHT,
				SNATCH_ELECTRIC_PRES, THUNDERSTRIKE_ELECTRIC_PRES, SNATCH_ELECTRIC_TENSION, PEAK_TENSION, CATEGORY,
				MATERIAL, CORROSION_FATIGUE, EQU_DESIGN_TEMP, DESIGN_PRESSURE_RANGE, SURFACE_HEAT_TRANSFER,
				DESIGN_SHELL_PRES, DESIGN_SHELL_TEMP, DESIGN_TUBE_TEMP, DESIGN_TUBE_PRES, OPTION_SHELL_PRESS,
				OPTION_SHELL_IN_TEMP, OPTION_SHELL_OUT_TEMP, OPTION_SHELL_MEDUIM, OPTION_TUBE_IN_TEMP,
				OPTION_TUBE_OUT_TEMP, OPTION_TUBE_MEDUIM, SHELL_MATERIAL, TUBE_MATERIAL, HIGH_LEFT, DISPLACEMENT,
				SPINDLE_SPEED, IMPELLER_MEDUIM, SPINDLE_MEDUIM, PUMP_MEDUIM, WIND_PRESSURE, ENGINE_NUMBER,
				LICENSE_NUMBER, CHASSIS_NUMBER, ENERGY_CONSUMPTION, ENERGY_CONSUMPTION_CAT, EQU_MEMO_TWO,
				EQU_MEMO_THREE, EQU_WHETHER_PERIODIC, EQUIPMENT_ATTACH_URL);

		String insertSql = "insert into cz_equipment_info " + condition;
		System.out.println("----insertSql:---------" + insertSql);
		int i = DataUtil.saveData(insertSql);
		System.out.println("----insertSql:---------i:" + i);

		// 保存设备的增加增加信息备份
		int j = historyRecord(condition, request);

		// 返回数据
		JSONObject json = new JSONObject();
		if (i > 0 && j > 0) {
			try {
				json.put("state", 0);
				json.put("messge", "插入成功");
				json.put("data", i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (i > 0 && j <= 0) {
			try {
				json.put("state", 0);
				json.put("messge", "设备插入成功,备份数据操作失败");
				json.put("data", i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			try {
				json.put("state", 1);
				json.put("messge", "插入失败");
				json.put("data", i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		response.getWriter().write(json.toString());
	}

	/**
	 * @Title: createInsertCondition @Description: 生成插入条件 @param @param
	 *         SECONDCLASS_EQUIPMENT @param @param WEL_NAME @param @param
	 *         WEL_UNIT @param @param EQU_MEMO_ONE @param @param
	 *         EQU_POSITION_NUM @param @param EQU_NAME @param @param
	 *         MANAGE_TYPE @param @param EQU_MODEL @param @param
	 *         MEARING_RANGE @param @param MEASURE_ACC @param @param
	 *         EQU_INSTALL_POSITION @param @param EQU_MANUFACTURER @param @param
	 *         SERIAL_NUM @param @param CHECK_CYCLE @param @param
	 *         CHECK_TIME @param @param NEXT_CHECK_TIME @param @param
	 *         REMARK1 @param @return 参数 @return String 返回类型 @throws
	 */

	private String createInsertCondition(String EQU_ID, String SECONDCLASS_EQUIPMENT, String WEL_NAME, String WEL_UNIT,
			String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME, // 通用
			String MANAGE_TYPE, String EQU_MODEL, String MEARING_RANGE, String MEASURE_ACC, String EQU_INSTALL_POSITION,
			String EQU_MANUFACTURER, String SERIAL_NUM, String CHECK_CYCLE, String CHECK_TIME, String NEXT_CHECK_TIME,
			String REMARK1, String MEDUIM_TYPE, String EQU_PRODUC_DATE, String EQU_COMMISSION_DATE,
			String EQU_WORK_TEMP, String EQU_LASTPERIODIC_DATE, String EQU_PERIODIC_CYCLE, String EQU_PERIODIC_WARNDAYS,
			String PRESSURE_RANGE, String EXPERY_TIME, String INTER_SIZE, String DEEP_LENGTH, String ORDER_NUM,
			String VAVLE_TYPE, String ACTION_MODLE, String HAVE_NOT, String ACTUAL_MODEL, String ACTUAL,
			String ELEC_MODEL, String ELEC, String MEASURE_PRIN, String PIPE_OUTER, String FLUX, String PIPE_THICK,
			String CV, String POSITIONER, String GAS_SOURCE, String FLA_SIZE, String PROCE_LINK_TYPE, String UNIT,
			String COUNT, String POWER_RATE, String ELECTRIC_PRES, String ELECTRIC_TENSION, String FREQUENCY,
			String BRAND, String CAPCITY, String SPEED_RAT, String BEFORE_BEARING1, String BEFORE_BEARING2,
			String AFTER_BEARING1, String GREASE_INTERV, String GREASE_QUAN, String INSULATION_RATE,
			String PROTECTION_RATE, String EXPLOSION_RATE, String DEVICE_TYPE, String HEIGHT_ELECTRIC_TENSION,
			String PHASE_NUMBER, String CONNECTION_GROUP, String HEIGHT_ELECTRIC_PRES, String WEIGHT,
			String SNATCH_ELECTRIC_PRES, String THUNDERSTRIKE_ELECTRIC_PRES, String SNATCH_ELECTRIC_TENSION,
			String PEAK_TENSION, String CATEGORY, String MATERIAL, String CORROSION_FATIGUE, String EQU_DESIGN_TEMP,
			String DESIGN_PRESSURE_RANGE, String SURFACE_HEAT_TRANSFER, String DESIGN_SHELL_PRES,
			String DESIGN_SHELL_TEMP, String DESIGN_TUBE_TEMP, String DESIGN_TUBE_PRES, String OPTION_SHELL_PRESS,
			String OPTION_SHELL_IN_TEMP, String OPTION_SHELL_OUT_TEMP, String OPTION_SHELL_MEDUIM,
			String OPTION_TUBE_IN_TEMP, String OPTION_TUBE_OUT_TEMP, String OPTION_TUBE_MEDUIM, String SHELL_MATERIAL,
			String TUBE_MATERIAL, String HIGH_LEFT, String DISPLACEMENT, String SPINDLE_SPEED, String IMPELLER_MEDUIM,
			String SPINDLE_MEDUIM, String PUMP_MEDUIM, String WIND_PRESSURE, String ENGINE_NUMBER,
			String LICENSE_NUMBER, String CHASSIS_NUMBER, String ENERGY_CONSUMPTION, String ENERGY_CONSUMPTION_CAT,
			String EQU_MEMO_TWO, String EQU_MEMO_THREE, String EQU_WHETHER_PERIODIC, String EQUIPMENT_ATTACH_URL) {
		String condition = "( EQU_ID, " + "SECONDCLASS_EQUIPMENT, " + "WEL_NAME, WEL_UNIT, " + "EQU_MEMO_ONE, "
				+ "EQU_POSITION_NUM, " + "EQU_NAME," + "MANAGE_TYPE, " + "EQU_MODEL, " + "MEARING_RANGE, "
				+ "MEASURE_ACC," + "EQU_INSTALL_POSITION, " + "EQU_MANUFACTURER, " + "SERIAL_NUM ," + "CHECK_CYCLE, "
				+ "CHECK_TIME, " + "NEXT_CHECK_TIME, " + "REMARK1," + "MEDUIM_TYPE," + "	EQU_PRODUC_DATE	,"
				+ "	EQU_COMMISSION_DATE	," + "	EQU_WORK_TEMP	," + "	EQU_LASTPERIODIC_DATE	,"
				+ "	EQU_PERIODIC_CYCLE	," + "	EQU_PERIODIC_WARNDAYS	," + "	PRESSURE_RANGE	," + "	EXPERY_TIME	,"
				+ "	INTER_SIZE	," + "	DEEP_LENGTH	," + "	ORDER_NUM	," + "	VAVLE_TYPE	," + "	ACTION_MODLE	,"
				+ "	HAVE_NOT	," + "	ACTUAL_MODEL	," + "	ACTUAL	," + "	ELEC_MODEL	," + "	ELEC	,"
				+ "	MEASURE_PRIN	," + "	PIPE_OUTER	," + "	FLUX	," + "	PIPE_THICK	," + "	CV	,"
				+ "	POSITIONER	," + "	GAS_SOURCE	," + "	FLA_SIZE	," + "	PROCE_LINK_TYPE	," + "	UNIT	,"
				+ "	COUNT	," + "	POWER_RATE	," + "	ELECTRIC_PRES	," + "	ELECTRIC_TENSION	,"
				+ "	FREQUENCY	," + "	BRAND	," + "	CAPCITY	," + "	SPEED_RAT	," + "	BEFORE_BEARING1	,"
				+ "	BEFORE_BEARING2	," + "	AFTER_BEARING1	," + "	GREASE_INTERV	," + "	GREASE_QUAN	,"
				+ "	INSULATION_RATE	," + "	PROTECTION_RATE	," + "	EXPLOSION_RATE	," + "	DEVICE_TYPE	,"
				+ "	HEIGHT_ELECTRIC_TENSION	," + "	PHASE_NUMBER	," + "	CONNECTION_GROUP	,"
				+ "	HEIGHT_ELECTRIC_PRES	," + "	WEIGHT	," + "	SNATCH_ELECTRIC_PRES	,"
				+ "	THUNDERSTRIKE_ELECTRIC_PRES	," + "	SNATCH_ELECTRIC_TENSION	," + "	PEAK_TENSION	,"
				+ "	CATEGORY	," + "	MATERIAL	," + "	CORROSION_FATIGUE	," + "	EQU_DESIGN_TEMP	,"
				+ "	DESIGN_PRESSURE_RANGE	," + "	SURFACE_HEAT_TRANSFER	," + "	DESIGN_SHELL_PRES	,"
				+ "	DESIGN_SHELL_TEMP	," + "	DESIGN_TUBE_TEMP	," + "	DESIGN_TUBE_PRES	,"
				+ "	OPTION_SHELL_PRESS	," + "	OPTION_SHELL_IN_TEMP	," + "	OPTION_SHELL_OUT_TEMP	,"
				+ "	OPTION_SHELL_MEDUIM	," + "	OPTION_TUBE_IN_TEMP	," + "	OPTION_TUBE_OUT_TEMP	,"
				+ "	OPTION_TUBE_MEDUIM	," + "	SHELL_MATERIAL	," + "	TUBE_MATERIAL	," + "	HIGH_LEFT	,"
				+ "	DISPLACEMENT	," + "	SPINDLE_SPEED	," + "	IMPELLER_MEDUIM	," + "	SPINDLE_MEDUIM	,"
				+ "	PUMP_MEDUIM	," + "	WIND_PRESSURE	," + "	ENGINE_NUMBER	," + "	LICENSE_NUMBER	,"
				+ "	CHASSIS_NUMBER	," + "	ENERGY_CONSUMPTION	," + "	ENERGY_CONSUMPTION_CAT	,"
				+ "	EQU_MEMO_TWO	," + "	EQU_MEMO_THREE	," + "	EQU_WHETHER_PERIODIC	,"
				+ "	EQUIPMENT_ATTACH_URL )";

		condition = condition + "values (";
		condition = condition + "'" + EQU_ID + "','" + SECONDCLASS_EQUIPMENT + "','" + WEL_NAME + "','" + WEL_UNIT
				+ "','" + EQU_MEMO_ONE + "','" + EQU_POSITION_NUM + "','" + EQU_NAME + "','" + MANAGE_TYPE + "','"
				+ EQU_MODEL + "','" + MEARING_RANGE + "','" + MEASURE_ACC + "','" + EQU_INSTALL_POSITION + "','"
				+ EQU_MANUFACTURER + "','" + SERIAL_NUM + "','" + CHECK_CYCLE + "','" + CHECK_TIME + "','"
				+ NEXT_CHECK_TIME + "','" + REMARK1 + "','" + MEDUIM_TYPE + "','" + EQU_PRODUC_DATE + "','"
				+ EQU_COMMISSION_DATE + "','" + EQU_WORK_TEMP + "','" + EQU_LASTPERIODIC_DATE + "','"
				+ EQU_PERIODIC_CYCLE + "','" + EQU_PERIODIC_WARNDAYS + "','" + PRESSURE_RANGE + "','" + EXPERY_TIME
				+ "','" + INTER_SIZE + "','" + DEEP_LENGTH + "','" + ORDER_NUM + "','" + VAVLE_TYPE + "','"
				+ ACTION_MODLE + "','" + HAVE_NOT + "','" + ACTUAL_MODEL + "','" + ACTUAL + "','" + ELEC_MODEL + "','"
				+ ELEC + "','" + MEASURE_PRIN + "','" + PIPE_OUTER + "','" + FLUX + "','" + PIPE_THICK + "','" + CV
				+ "','" + POSITIONER + "','" + GAS_SOURCE + "','" + FLA_SIZE + "','" + PROCE_LINK_TYPE + "','" + UNIT
				+ "','" + COUNT + "','" + POWER_RATE + "','" + ELECTRIC_PRES + "','" + ELECTRIC_TENSION + "','"
				+ FREQUENCY + "','" + BRAND + "','" + CAPCITY + "','" + SPEED_RAT + "','" + BEFORE_BEARING1 + "','"
				+ BEFORE_BEARING2 + "','" + AFTER_BEARING1 + "','" + GREASE_INTERV + "','" + GREASE_QUAN + "','"
				+ INSULATION_RATE + "','" + PROTECTION_RATE + "','" + EXPLOSION_RATE + "','" + DEVICE_TYPE + "','"
				+ HEIGHT_ELECTRIC_TENSION + "','" + PHASE_NUMBER + "','" + CONNECTION_GROUP + "','"
				+ HEIGHT_ELECTRIC_PRES + "','" + WEIGHT + "','" + SNATCH_ELECTRIC_PRES + "','"
				+ THUNDERSTRIKE_ELECTRIC_PRES + "','" + SNATCH_ELECTRIC_TENSION + "','" + PEAK_TENSION + "','"
				+ CATEGORY + "','" + MATERIAL + "','" + CORROSION_FATIGUE + "','" + EQU_DESIGN_TEMP + "','"
				+ DESIGN_PRESSURE_RANGE + "','" + SURFACE_HEAT_TRANSFER + "','" + DESIGN_SHELL_PRES + "','"
				+ DESIGN_SHELL_TEMP + "','" + DESIGN_TUBE_TEMP + "','" + DESIGN_TUBE_PRES + "','" + OPTION_SHELL_PRESS
				+ "','" + OPTION_SHELL_IN_TEMP + "','" + OPTION_SHELL_OUT_TEMP + "','" + OPTION_SHELL_MEDUIM + "','"
				+ OPTION_TUBE_IN_TEMP + "','" + OPTION_TUBE_OUT_TEMP + "','" + OPTION_TUBE_MEDUIM + "','"
				+ SHELL_MATERIAL + "','" + TUBE_MATERIAL + "','" + HIGH_LEFT + "','" + DISPLACEMENT + "','"
				+ SPINDLE_SPEED + "','" + IMPELLER_MEDUIM + "','" + SPINDLE_MEDUIM + "','" + PUMP_MEDUIM + "','"
				+ WIND_PRESSURE + "','" + ENGINE_NUMBER + "','" + LICENSE_NUMBER + "','" + CHASSIS_NUMBER + "','"
				+ ENERGY_CONSUMPTION + "','" + ENERGY_CONSUMPTION_CAT + "','" + EQU_MEMO_TWO + "','" + EQU_MEMO_THREE
				+ "','" + EQU_WHETHER_PERIODIC + "','" + EQUIPMENT_ATTACH_URL + "')";

		return condition;
	}

	/**
	 * @Title: isEmpty @Description: 判断字符串是否为空部分加以封装 @param @param
	 *         name @param @return 参数 @return Boolean 返回类型 @throws
	 */
	private Boolean isEmpty(String name) {
		if (!(name == null || "".equals(name))) {
			return true;
		}
		return false;
	}

	public String getName(HttpServletRequest request) {
		String name = "";
		try {
			Cookie[] cookies = request.getCookies();
			for (Cookie c : cookies) {
				String cookieName = c.getName();
				if (!StringUtils.isBlank(cookieName) && "userID".equals(cookieName)) {
					if (!StringUtils.isBlank(name)) {
						name = name + "," + c.getValue();
					} else {
						name = c.getValue();
					}
				}
			}
			System.out.println("----获取操作用户姓名:---------name:" + name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return name;
	}

	public int historyRecord(String condition, HttpServletRequest request) {
		int j = 0;
		try {
			// 获取新增字段值
			String name = getName(request);

			// 插入信息
			String replacement = ",name,type) ";
			condition = condition.replaceFirst("\\)", replacement);
			condition = condition.substring(0, condition.length() - 1) + ",'" + name + "','insert')";
			System.out.println("----增加设备备份数据的sql:---------sql:" + condition);
			String insertSql2 = "insert into iot_equipment_operate_recode " + condition;
			j = DataUtil.saveData(insertSql2);
			System.out.println("----insertSql2:---------j:" + j);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(">>>>>>>>>>>>>>>>>>>保存设备增加信息的备份失败……");
			return -1;
		}
		return j;
	}

	public int updateHisRecode(String condition, HttpServletRequest request, String type) {
		// 获取新增字段值
		String name = getName(request);

		String recode = "EQU_ID, " + "SECONDCLASS_EQUIPMENT, " + "WEL_NAME, WEL_UNIT, " + "EQU_MEMO_ONE, "
				+ "EQU_POSITION_NUM, " + "EQU_NAME," + "MANAGE_TYPE, " + "EQU_MODEL, " + "MEARING_RANGE, "
				+ "MEASURE_ACC," + "EQU_INSTALL_POSITION, " + "EQU_MANUFACTURER, " + "SERIAL_NUM ," + "CHECK_CYCLE, "
				+ "CHECK_TIME, " + "NEXT_CHECK_TIME, " + "REMARK1," + "	EQU_PRODUC_DATE	," + "	EQU_COMMISSION_DATE	,"
				+ "	EQU_WORK_TEMP	," + "	EQU_LASTPERIODIC_DATE	," + "	EQU_PERIODIC_CYCLE	,"
				+ "	EQU_PERIODIC_WARNDAYS	," + "	PRESSURE_RANGE	," + "	EXPERY_TIME	," + "	INTER_SIZE	,"
				+ "	DEEP_LENGTH	," + "	ORDER_NUM	," + "	VAVLE_TYPE	," + "	ACTION_MODLE	," + "	HAVE_NOT	,"
				+ "	ACTUAL_MODEL	," + "	ACTUAL	," + "	ELEC_MODEL	," + "	ELEC	," + "	MEASURE_PRIN	,"
				+ "	PIPE_OUTER	," + "	FLUX	," + "	PIPE_THICK	," + "	CV	," + "	POSITIONER	,"
				+ "	GAS_SOURCE	," + "	FLA_SIZE	," + "	PROCE_LINK_TYPE	," + "	UNIT	," + "	COUNT	,"
				+ "	POWER_RATE	," + "	ELECTRIC_PRES	," + "	ELECTRIC_TENSION	," + "	FREQUENCY	,"
				+ "	BRAND	," + "	CAPCITY	," + "	SPEED_RAT	," + "	BEFORE_BEARING1	," + "	BEFORE_BEARING2	,"
				+ "	AFTER_BEARING1	," + "	GREASE_INTERV	," + "	GREASE_QUAN	," + "	INSULATION_RATE	,"
				+ "	PROTECTION_RATE	," + "	EXPLOSION_RATE	," + "	DEVICE_TYPE	," + "	HEIGHT_ELECTRIC_TENSION	,"
				+ "	PHASE_NUMBER	," + "	CONNECTION_GROUP	," + "	HEIGHT_ELECTRIC_PRES	," + "	WEIGHT	,"
				+ "	SNATCH_ELECTRIC_PRES	," + "	THUNDERSTRIKE_ELECTRIC_PRES	," + "	SNATCH_ELECTRIC_TENSION	,"
				+ "	PEAK_TENSION	," + "	CATEGORY	," + "	MATERIAL	," + "	CORROSION_FATIGUE	,"
				+ "	EQU_DESIGN_TEMP	," + "	DESIGN_PRESSURE_RANGE	," + "	SURFACE_HEAT_TRANSFER	,"
				+ "	DESIGN_SHELL_PRES	," + "	DESIGN_SHELL_TEMP	," + "	DESIGN_TUBE_TEMP	,"
				+ "	DESIGN_TUBE_PRES	," + "	OPTION_SHELL_PRESS	," + "	OPTION_SHELL_IN_TEMP	,"
				+ "	OPTION_SHELL_OUT_TEMP	," + "	OPTION_SHELL_MEDUIM	," + "	OPTION_TUBE_IN_TEMP	,"
				+ "	OPTION_TUBE_OUT_TEMP	," + "	OPTION_TUBE_MEDUIM	," + "	SHELL_MATERIAL	,"
				+ "	TUBE_MATERIAL	," + "	HIGH_LEFT	," + "	DISPLACEMENT	," + "	SPINDLE_SPEED	,"
				+ "	IMPELLER_MEDUIM	," + "	SPINDLE_MEDUIM	," + "	PUMP_MEDUIM	," + "	WIND_PRESSURE	,"
				+ "	ENGINE_NUMBER	," + "	LICENSE_NUMBER	," + "	CHASSIS_NUMBER	," + "	ENERGY_CONSUMPTION	,"
				+ "	ENERGY_CONSUMPTION_CAT	," + "	EQU_MEMO_TWO	," + "	EQU_MEMO_THREE	,"
				+ "	EQU_WHETHER_PERIODIC	," + "	EQUIPMENT_ATTACH_URL ";
		int i = 0;
		try {
			String sql = "insert into iot_equipment_operate_recode (name,type," + recode + ") select '" + name + "','"
					+ type + "'," + recode + " from cz_equipment_info " + condition;
			i = DataUtil.doExecuteSql(sql);
			System.out.println("-------更新增加历史操作记录数据-------i:" + i);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return i;
	}

}
