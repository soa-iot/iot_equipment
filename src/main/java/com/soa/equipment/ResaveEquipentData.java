
    /**  
    * @Title: ModifyEquipentData.java
    * @Package com.soa.CZ_PIOTMS.server
    * @Description: TODO(用一句话描述该文件做什么)
    * @author yanghua
    * @date 2018年10月28日
    * @version V1.0  
    */
    
package com.soa.equipment;

import java.io.IOException;

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
    * @ClassName: ModifyEquipentData
    * @Description: 根据设备位号，恢复设备信息
    * @author yanghua
    * @date 2018年10月28日
    *
    */

public class ResaveEquipentData extends HttpServlet {	
	    /**
	    * @Fields serialVersionUID : 序列化
	    */
	    
	private static final long serialVersionUID = 16L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding( "utf-8" );
		response.setContentType( "text/html;charset=utf-8" ); 
		
		//获取参数
		//通用
		String WEL_NAME = request.getParameter("WEL_NAME");
		String WEL_UNIT = request.getParameter("WEL_UNIT");
		String EQU_MEMO_ONE = request.getParameter("EQU_MEMO_ONE");
		String EQU_POSITION_NUM = request.getParameter("EQU_POSITION_NUM");
		String EQU_NAME = request.getParameter("EQU_NAME");
		
		//设备大类
		String SECONDCLASS_EQUIPMENT = request.getParameter("SECONDCLASS_EQUIPMENT");

		//压力表
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
		
		//压力差压变送器
		String MEDUIM_TYPE = request.getParameter( "MEDUIM_TYPE" );
		String FREQUENCY = request.getParameter( "FREQUENCY" );
		String	EQU_PRODUC_DATE = request.getParameter("EQU_PRODUC_DATE" );
		String	EQU_COMMISSION_DATE = request.getParameter("EQU_COMMISSION_DATE" );
		String	EQU_WORK_TEMP = request.getParameter("EQU_WORK_TEMP" );
		String	EQU_LASTPERIODIC_DATE = request.getParameter("EQU_LASTPERIODIC_DATE" );
		String	EQU_PERIODIC_CYCLE = request.getParameter("EQU_PERIODIC_CYCLE" );
		String	EQU_PERIODIC_WARNDAYS = request.getParameter("EQU_PERIODIC_WARNDAYS" );
		String	PRESSURE_RANGE = request.getParameter("PRESSURE_RANGE" );
		String	EXPERY_TIME = request.getParameter("EXPERY_TIME" );
		String	INTER_SIZE = request.getParameter("INTER_SIZE" );
		String	DEEP_LENGTH = request.getParameter("DEEP_LENGTH" );
		String	ORDER_NUM = request.getParameter("ORDER_NUM" );
		String	VAVLE_TYPE = request.getParameter("VAVLE_TYPE" );
		String	ACTION_MODLE = request.getParameter("ACTION_MODLE" );
		String	HAVE_NOT = request.getParameter("HAVE_NOT" );
		String	ACTUAL_MODEL = request.getParameter("ACTUAL_MODEL" );
		String	ACTUAL = request.getParameter("ACTUAL" );
		String	ELEC_MODEL = request.getParameter("ELEC_MODEL" );
		String	ELEC = request.getParameter("ELEC" );
		String	MEASURE_PRIN = request.getParameter("MEASURE_PRIN" );
		String	PIPE_OUTER = request.getParameter("PIPE_OUTER" );
		String	FLUX = request.getParameter("FLUX" );
		String	PIPE_THICK = request.getParameter("PIPE_THICK" );
		String	CV = request.getParameter("CV" );
		String	POSITIONER = request.getParameter("POSITIONER" );
		String	GAS_SOURCE = request.getParameter("GAS_SOURCE" );
		String	FLA_SIZE = request.getParameter("FLA_SIZE" );
		String	PROCE_LINK_TYPE = request.getParameter("PROCE_LINK_TYPE" );
		String	UNIT = request.getParameter("UNIT" );
		String	COUNT = request.getParameter("COUNT" );
		String	POWER_RATE = request.getParameter("POWER_RATE" );
		String	ELECTRIC_PRES = request.getParameter("ELECTRIC_PRES" );
		String	ELECTRIC_TENSION = request.getParameter("ELECTRIC_TENSION" );
		String	BRAND = request.getParameter("BRAND" );
		String	CAPCITY = request.getParameter("CAPCITY" );
		String	SPEED_RAT = request.getParameter("SPEED_RAT" );
		String	BEFORE_BEARING1 = request.getParameter("BEFORE_BEARING1" );
		String	BEFORE_BEARING2 = request.getParameter("BEFORE_BEARING2" );
		String	AFTER_BEARING1 = request.getParameter("AFTER_BEARING1" );
		String	GREASE_INTERV = request.getParameter("GREASE_INTERV" );
		String	GREASE_QUAN = request.getParameter("GREASE_QUAN" );
		String	INSULATION_RATE = request.getParameter("INSULATION_RATE" );
		String	PROTECTION_RATE = request.getParameter("PROTECTION_RATE" );
		String	EXPLOSION_RATE = request.getParameter("EXPLOSION_RATE" );
		String	DEVICE_TYPE = request.getParameter("DEVICE_TYPE" );
		String	HEIGHT_ELECTRIC_TENSION = request.getParameter("HEIGHT_ELECTRIC_TENSION" );
		String	PHASE_NUMBER = request.getParameter("PHASE_NUMBER" );
		String	CONNECTION_GROUP = request.getParameter("CONNECTION_GROUP" );
		String	HEIGHT_ELECTRIC_PRES = request.getParameter("HEIGHT_ELECTRIC_PRES" );
		String	WEIGHT = request.getParameter("WEIGHT" );
		String	SNATCH_ELECTRIC_PRES = request.getParameter("SNATCH_ELECTRIC_PRES" );
		String	THUNDERSTRIKE_ELECTRIC_PRES = request.getParameter("THUNDERSTRIKE_ELECTRIC_PRES" );
		String	SNATCH_ELECTRIC_TENSION = request.getParameter("SNATCH_ELECTRIC_TENSION" );
		String	PEAK_TENSION = request.getParameter("PEAK_TENSION" );
		String	CATEGORY = request.getParameter("CATEGORY" );
		String	MATERIAL = request.getParameter("MATERIAL" );
		String	CORROSION_FATIGUE = request.getParameter("CORROSION_FATIGUE" );
		String	EQU_DESIGN_TEMP = request.getParameter("EQU_DESIGN_TEMP" );
		String	DESIGN_PRESSURE_RANGE = request.getParameter("DESIGN_PRESSURE_RANGE" );
		String	SURFACE_HEAT_TRANSFER = request.getParameter("SURFACE_HEAT_TRANSFER" );
		String	DESIGN_SHELL_PRES = request.getParameter("DESIGN_SHELL_PRES" );
		String	DESIGN_SHELL_TEMP = request.getParameter("DESIGN_SHELL_TEMP" );
		String	DESIGN_TUBE_TEMP = request.getParameter("DESIGN_TUBE_TEMP" );
		String	DESIGN_TUBE_PRES = request.getParameter("DESIGN_TUBE_PRES" );
		String	OPTION_SHELL_PRESS = request.getParameter("OPTION_SHELL_PRESS" );
		String	OPTION_SHELL_IN_TEMP = request.getParameter("OPTION_SHELL_IN_TEMP" );
		String	OPTION_SHELL_OUT_TEMP = request.getParameter("OPTION_SHELL_OUT_TEMP" );
		String	OPTION_SHELL_MEDUIM = request.getParameter("OPTION_SHELL_MEDUIM" );
		String	OPTION_TUBE_IN_TEMP = request.getParameter("OPTION_TUBE_IN_TEMP" );
		String	OPTION_TUBE_OUT_TEMP = request.getParameter("OPTION_TUBE_OUT_TEMP" );
		String	OPTION_TUBE_MEDUIM = request.getParameter("OPTION_TUBE_MEDUIM" );
		String	SHELL_MATERIAL = request.getParameter("SHELL_MATERIAL" );
		String	TUBE_MATERIAL = request.getParameter("TUBE_MATERIAL" );
		String	HIGH_LEFT = request.getParameter("HIGH_LEFT" );
		String	DISPLACEMENT = request.getParameter("DISPLACEMENT" );
		String	SPINDLE_SPEED = request.getParameter("SPINDLE_SPEED" );
		String	IMPELLER_MEDUIM = request.getParameter("IMPELLER_MEDUIM" );
		String	SPINDLE_MEDUIM = request.getParameter("SPINDLE_MEDUIM" );
		String	PUMP_MEDUIM = request.getParameter("PUMP_MEDUIM" );
		String	WIND_PRESSURE = request.getParameter("WIND_PRESSURE" );
		String	ENGINE_NUMBER = request.getParameter("ENGINE_NUMBER" );
		String	LICENSE_NUMBER = request.getParameter("LICENSE_NUMBER" );
		String	CHASSIS_NUMBER = request.getParameter("CHASSIS_NUMBER" );
		String	ENERGY_CONSUMPTION = request.getParameter("ENERGY_CONSUMPTION" );
		String	ENERGY_CONSUMPTION_CAT = request.getParameter("ENERGY_CONSUMPTION_CAT" );
		String	EQU_MEMO_TWO = request.getParameter("EQU_MEMO_TWO" );
		String	EQU_MEMO_THREE = request.getParameter("EQU_MEMO_THREE" );
		String	EQU_WHETHER_PERIODIC = request.getParameter("EQU_WHETHER_PERIODIC" );
		String	EQUIPMENT_ATTACH_URL = request.getParameter("EQUIPMENT_ATTACH_URL" );		
		
		String condition = createUpdateCondition( 
				 WEL_NAME, WEL_UNIT, EQU_MEMO_ONE, 
				EQU_POSITION_NUM, EQU_NAME,//通用
				MANAGE_TYPE, EQU_MODEL, MEARING_RANGE, MEASURE_ACC,
				EQU_INSTALL_POSITION, EQU_MANUFACTURER, SERIAL_NUM,
				CHECK_CYCLE, CHECK_TIME, NEXT_CHECK_TIME, REMARK1,//压力表
				MEDUIM_TYPE,//压力差压变送器
				EQU_PRODUC_DATE,
				EQU_COMMISSION_DATE,
				EQU_WORK_TEMP,
				EQU_LASTPERIODIC_DATE,
				EQU_PERIODIC_CYCLE,
				EQU_PERIODIC_WARNDAYS,
				PRESSURE_RANGE,
				EXPERY_TIME,
				INTER_SIZE,
				DEEP_LENGTH,
				ORDER_NUM,
				VAVLE_TYPE,
				ACTION_MODLE,
				HAVE_NOT,
				ACTUAL_MODEL,
				ACTUAL,
				ELEC_MODEL,
				ELEC,
				MEASURE_PRIN,
				PIPE_OUTER,
				FLUX,
				PIPE_THICK,
				CV,
				POSITIONER,
				GAS_SOURCE,
				FLA_SIZE,
				PROCE_LINK_TYPE,
				UNIT,
				COUNT,
				POWER_RATE,
				ELECTRIC_PRES,
				ELECTRIC_TENSION,
				FREQUENCY,
				BRAND,
				CAPCITY,
				SPEED_RAT,
				BEFORE_BEARING1,
				BEFORE_BEARING2,
				AFTER_BEARING1,
				GREASE_INTERV,
				GREASE_QUAN,
				INSULATION_RATE,
				PROTECTION_RATE,
				EXPLOSION_RATE,
				DEVICE_TYPE,
				HEIGHT_ELECTRIC_TENSION,
				PHASE_NUMBER,
				CONNECTION_GROUP,
				HEIGHT_ELECTRIC_PRES,
				WEIGHT,
				SNATCH_ELECTRIC_PRES,
				THUNDERSTRIKE_ELECTRIC_PRES,
				SNATCH_ELECTRIC_TENSION,
				PEAK_TENSION,
				CATEGORY,
				MATERIAL,
				CORROSION_FATIGUE,
				EQU_DESIGN_TEMP,
				DESIGN_PRESSURE_RANGE,
				SURFACE_HEAT_TRANSFER,
				DESIGN_SHELL_PRES,
				DESIGN_SHELL_TEMP,
				DESIGN_TUBE_TEMP,
				DESIGN_TUBE_PRES,
				OPTION_SHELL_PRESS,
				OPTION_SHELL_IN_TEMP,
				OPTION_SHELL_OUT_TEMP,
				OPTION_SHELL_MEDUIM,
				OPTION_TUBE_IN_TEMP,
				OPTION_TUBE_OUT_TEMP,
				OPTION_TUBE_MEDUIM,
				SHELL_MATERIAL,
				TUBE_MATERIAL,
				HIGH_LEFT,
				DISPLACEMENT,
				SPINDLE_SPEED,
				IMPELLER_MEDUIM,
				SPINDLE_MEDUIM,
				PUMP_MEDUIM,
				WIND_PRESSURE,
				ENGINE_NUMBER,
				LICENSE_NUMBER,
				CHASSIS_NUMBER,
				ENERGY_CONSUMPTION,
				ENERGY_CONSUMPTION_CAT,
				EQU_MEMO_TWO,
				EQU_MEMO_THREE,
				EQU_WHETHER_PERIODIC,
				EQUIPMENT_ATTACH_URL
		);
		
		String updateSql = "update cz_equipment_info set " + condition;
		System.out.println("----updateSql:---------" + updateSql );
		int i = DataUtil.doExecuteSql( updateSql );
		System.out.println("----updateSql:---------i:" + i );
		
		//保存操作历史记录信息
		InsertEquipentData insertEquipent = new InsertEquipentData();
		int k = 0;
		try {	
			String whereCondition = 
					condition.substring(condition.indexOf("where"), condition.length());
			System.out.println("------------更新历史记录的where条件-------whereCondition："+ whereCondition);
			k = insertEquipent.updateHisRecode( whereCondition, request , "update" ) ;
			System.out.println("------------更新历史记录的where条件-------k："+ k);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//返回数据
		JSONObject json = new JSONObject();
		if( i > 0 && k > 0 ) {
			try {
				json.put( "state" , 0 );
				json.put( "messge" , "修改成功" );
				json.put( "messge" , "修改成功" );
				json.put( "data" , i );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else if( i > 0 && k < 0 ) {
			try {
				json.put( "state" , 0 );
				json.put( "messge" , "修改成功,备份操作失败" );
				json.put( "data" , i );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else {
			try {
				json.put( "state" , 1 );
				json.put( "messge" , "修改失败" );
				json.put( "data" , i );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}		
		response.getWriter().write( json.toString() );
	}
	
	
	    /**
	    * @Title: createUpdateCondition
	    * @Description: 动态获取sql的条件
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    */
	    
	public String createUpdateCondition(
			String WEL_NAME, String WEL_UNIT, 
			String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME,//通用
			String MANAGE_TYPE, String EQU_MODEL, String MEARING_RANGE, 
			String MEASURE_ACC,String EQU_INSTALL_POSITION, String EQU_MANUFACTURER, 
			String SERIAL_NUM,String CHECK_CYCLE, String CHECK_TIME, 
			String NEXT_CHECK_TIME, String REMARK1,
			String MEDUIM_TYPE,
			String	EQU_PRODUC_DATE,
			String	EQU_COMMISSION_DATE,
			String	EQU_WORK_TEMP,
			String	EQU_LASTPERIODIC_DATE,
			String	EQU_PERIODIC_CYCLE,
			String	EQU_PERIODIC_WARNDAYS,
			String	PRESSURE_RANGE,
			String	EXPERY_TIME,
			String	INTER_SIZE,
			String	DEEP_LENGTH,
			String	ORDER_NUM,
			String	VAVLE_TYPE,
			String	ACTION_MODLE,
			String	HAVE_NOT,
			String	ACTUAL_MODEL,
			String	ACTUAL,
			String	ELEC_MODEL,
			String	ELEC,
			String	MEASURE_PRIN,
			String	PIPE_OUTER,
			String	FLUX,
			String	PIPE_THICK,
			String	CV,
			String	POSITIONER,
			String	GAS_SOURCE,
			String	FLA_SIZE,
			String	PROCE_LINK_TYPE,
			String	UNIT,
			String	COUNT,
			String	POWER_RATE,
			String	ELECTRIC_PRES,
			String	ELECTRIC_TENSION,
			String	FREQUENCY,
			String	BRAND,
			String	CAPCITY,
			String	SPEED_RAT,
			String	BEFORE_BEARING1,
			String	BEFORE_BEARING2,
			String	AFTER_BEARING1,
			String	GREASE_INTERV,
			String	GREASE_QUAN,
			String	INSULATION_RATE,
			String	PROTECTION_RATE,
			String	EXPLOSION_RATE,
			String	DEVICE_TYPE,
			String	HEIGHT_ELECTRIC_TENSION,
			String	PHASE_NUMBER,
			String	CONNECTION_GROUP,
			String	HEIGHT_ELECTRIC_PRES,
			String	WEIGHT,
			String	SNATCH_ELECTRIC_PRES,
			String	THUNDERSTRIKE_ELECTRIC_PRES,
			String	SNATCH_ELECTRIC_TENSION,
			String	PEAK_TENSION,
			String	CATEGORY,
			String	MATERIAL,
			String	CORROSION_FATIGUE,
			String	EQU_DESIGN_TEMP,
			String	DESIGN_PRESSURE_RANGE,
			String	SURFACE_HEAT_TRANSFER,
			String	DESIGN_SHELL_PRES,
			String	DESIGN_SHELL_TEMP,
			String	DESIGN_TUBE_TEMP,
			String	DESIGN_TUBE_PRES,
			String	OPTION_SHELL_PRESS,
			String	OPTION_SHELL_IN_TEMP,
			String	OPTION_SHELL_OUT_TEMP,
			String	OPTION_SHELL_MEDUIM,
			String	OPTION_TUBE_IN_TEMP,
			String	OPTION_TUBE_OUT_TEMP,
			String	OPTION_TUBE_MEDUIM,
			String	SHELL_MATERIAL,
			String	TUBE_MATERIAL,
			String	HIGH_LEFT,
			String	DISPLACEMENT,
			String	SPINDLE_SPEED,
			String	IMPELLER_MEDUIM,
			String	SPINDLE_MEDUIM,
			String	PUMP_MEDUIM,
			String	WIND_PRESSURE,
			String	ENGINE_NUMBER,
			String	LICENSE_NUMBER,
			String	CHASSIS_NUMBER,
			String	ENERGY_CONSUMPTION,
			String	ENERGY_CONSUMPTION_CAT,
			String	EQU_MEMO_TWO,
			String	EQU_MEMO_THREE,
			String	EQU_WHETHER_PERIODIC,
			String	EQUIPMENT_ATTACH_URL	) {
		String condition = "";

		condition = condition + " WEL_NAME='" + WEL_NAME + "',";
		condition = condition + " WEL_UNIT='" + WEL_UNIT + "',";
		condition = condition + " EQU_MEMO_ONE='" + EQU_MEMO_ONE + "',";
		//condition = condition + " EQU_POSITION_NUM=" + EQU_POSITION_NUM + ",";

			condition = condition + " MANAGE_TYPE='" + MANAGE_TYPE + "',";

			condition = condition + " EQU_MODEL='" + EQU_MODEL + "',";

		

			condition = condition + " MEARING_RANGE='" + MEARING_RANGE + "',";


			condition = condition + " MEASURE_ACC='" + MEASURE_ACC + "',";
	
			condition = condition + " EQU_INSTALL_POSITION='" + EQU_INSTALL_POSITION + "',";

			condition = condition + " EQU_MANUFACTURER='" + EQU_MANUFACTURER + "',";

			condition = condition + " SERIAL_NUM='" + SERIAL_NUM + "',";
	
			condition = condition + " CHECK_CYCLE='" + CHECK_CYCLE + "',";

			condition = condition + " CHECK_TIME='" + CHECK_TIME + "',";

	
			condition = condition + " NEXT_CHECK_TIME='" + NEXT_CHECK_TIME + "',";


			condition = condition + " REMARK1='" + REMARK1 + "',";

		condition = condition + " 	EQU_PRODUC_DATE	='"	+	EQU_PRODUC_DATE	+	"',";
		condition = condition + " 	EQU_COMMISSION_DATE	='"	+	EQU_COMMISSION_DATE	+	"',";
		condition = condition + " 	EQU_WORK_TEMP	='"	+	EQU_WORK_TEMP	+	"',";
		condition = condition + " 	EQU_LASTPERIODIC_DATE	='"	+	EQU_LASTPERIODIC_DATE	+	"',";
		condition = condition + " 	EQU_PERIODIC_CYCLE	='"	+	EQU_PERIODIC_CYCLE	+	"',";
		condition = condition + " 	EQU_PERIODIC_WARNDAYS	='"	+	EQU_PERIODIC_WARNDAYS	+	"',";
		condition = condition + " 	PRESSURE_RANGE	='"	+	PRESSURE_RANGE	+	"',";
		condition = condition + " 	EXPERY_TIME	='"	+	EXPERY_TIME	+	"',";
		condition = condition + " 	INTER_SIZE	='"	+	INTER_SIZE	+	"',";
		condition = condition + " 	DEEP_LENGTH	='"	+	DEEP_LENGTH	+	"',";
		condition = condition + " 	ORDER_NUM	='"	+	ORDER_NUM	+	"',";
		condition = condition + " 	VAVLE_TYPE	='"	+	VAVLE_TYPE	+	"',";
		condition = condition + " 	ACTION_MODLE	='"	+	ACTION_MODLE	+	"',";
		condition = condition + " 	HAVE_NOT	='"	+	HAVE_NOT	+	"',";
	condition = condition + " 	ACTUAL_MODEL	='"	+	ACTUAL_MODEL	+	"',";
	condition = condition + " 	ACTUAL	='"	+	ACTUAL	+	"',";
		condition = condition + " 	ELEC_MODEL	='"	+	ELEC_MODEL	+	"',";
		condition = condition + " 	ELEC	='"	+	ELEC	+	"',";
		condition = condition + " 	MEASURE_PRIN	='"	+	MEASURE_PRIN	+	"',";
		condition = condition + " 	PIPE_OUTER	='"	+	PIPE_OUTER	+	"',";
		condition = condition + " 	FLUX	='"	+	FLUX	+	"',";
		condition = condition + " 	PIPE_THICK	='"	+	PIPE_THICK	+	"',";
		condition = condition + " 	CV	='"	+	CV	+	"',";
		condition = condition + " 	POSITIONER	='"	+	POSITIONER	+	"',";
		condition = condition + " 	GAS_SOURCE	='"	+	GAS_SOURCE	+	"',";
		condition = condition + " 	FLA_SIZE	='"	+	FLA_SIZE	+	"',";
		condition = condition + " 	PROCE_LINK_TYPE	='"	+	PROCE_LINK_TYPE	+	"',";
		condition = condition + " 	UNIT	='"	+	UNIT	+	"',";
		condition = condition + " 	COUNT	='"	+	COUNT	+	"',";
		condition = condition + " 	POWER_RATE	='"	+	POWER_RATE	+	"',";
		condition = condition + " 	ELECTRIC_PRES	='"	+	ELECTRIC_PRES	+	"',";
		condition = condition + " 	ELECTRIC_TENSION	='"	+	ELECTRIC_TENSION	+	"',";
		condition = condition + " 	FREQUENCY	='"	+	FREQUENCY	+	"',";
		condition = condition + " 	BRAND	='"	+	BRAND	+	"',";
		condition = condition + " 	CAPCITY	='"	+	CAPCITY	+	"',";
		condition = condition + " 	SPEED_RAT	='"	+	SPEED_RAT	+	"',";
		condition = condition + " 	BEFORE_BEARING1	='"	+	BEFORE_BEARING1	+	"',";
		condition = condition + " 	BEFORE_BEARING2	='"	+	BEFORE_BEARING2	+	"',";
		condition = condition + " 	AFTER_BEARING1	='"	+	AFTER_BEARING1	+	"',";
		condition = condition + " 	GREASE_INTERV	='"	+	GREASE_INTERV	+	"',";
		condition = condition + " 	GREASE_QUAN	='"	+	GREASE_QUAN	+	"',";
		condition = condition + " 	INSULATION_RATE	='"	+	INSULATION_RATE	+	"',";
		condition = condition + " 	PROTECTION_RATE	='"	+	PROTECTION_RATE	+	"',";
		condition = condition + " 	EXPLOSION_RATE	='"	+	EXPLOSION_RATE	+	"',";
		condition = condition + " 	DEVICE_TYPE	='"	+	DEVICE_TYPE	+	"',";
		condition = condition + " 	HEIGHT_ELECTRIC_TENSION	='"	+	HEIGHT_ELECTRIC_TENSION	+	"',";
		condition = condition + " 	PHASE_NUMBER	='"	+	PHASE_NUMBER	+	"',";
		condition = condition + " 	CONNECTION_GROUP	='"	+	CONNECTION_GROUP	+	"',";
		condition = condition + " 	HEIGHT_ELECTRIC_PRES	='"	+	HEIGHT_ELECTRIC_PRES	+	"',";
		condition = condition + " 	WEIGHT	='"	+	WEIGHT	+	"',";
		condition = condition + " 	SNATCH_ELECTRIC_PRES	='"	+	SNATCH_ELECTRIC_PRES	+	"',";
		condition = condition + " 	THUNDERSTRIKE_ELECTRIC_PRES	='"	+	THUNDERSTRIKE_ELECTRIC_PRES	+	"',";
		condition = condition + " 	SNATCH_ELECTRIC_TENSION	='"	+	SNATCH_ELECTRIC_TENSION	+	"',";
		condition = condition + " 	PEAK_TENSION	='"	+	PEAK_TENSION	+	"',";
		condition = condition + " 	CATEGORY	='"	+	CATEGORY	+	"',";
		condition = condition + " 	MATERIAL	='"	+	MATERIAL	+	"',";
		condition = condition + " 	CORROSION_FATIGUE	='"	+	CORROSION_FATIGUE	+	"',";
		condition = condition + " 	EQU_DESIGN_TEMP	='"	+	EQU_DESIGN_TEMP	+	"',";
		condition = condition + " 	DESIGN_PRESSURE_RANGE	='"	+	DESIGN_PRESSURE_RANGE	+	"',";
		condition = condition + " 	SURFACE_HEAT_TRANSFER	='"	+	SURFACE_HEAT_TRANSFER	+	"',";
		condition = condition + " 	DESIGN_SHELL_PRES	='"	+	DESIGN_SHELL_PRES	+	"',";
		condition = condition + " 	DESIGN_SHELL_TEMP	='"	+	DESIGN_SHELL_TEMP	+	"',";
		condition = condition + " 	DESIGN_TUBE_TEMP	='"	+	DESIGN_TUBE_TEMP	+	"',";
		condition = condition + " 	DESIGN_TUBE_PRES	='"	+	DESIGN_TUBE_PRES	+	"',";
		condition = condition + " 	OPTION_SHELL_PRESS	='"	+	OPTION_SHELL_PRESS	+	"',";
		condition = condition + " 	OPTION_SHELL_IN_TEMP	='"	+	OPTION_SHELL_IN_TEMP	+	"',";
		condition = condition + " 	OPTION_SHELL_OUT_TEMP	='"	+	OPTION_SHELL_OUT_TEMP	+	"',";
		condition = condition + " 	OPTION_SHELL_MEDUIM	='"	+	OPTION_SHELL_MEDUIM	+	"',";
		condition = condition + " 	OPTION_TUBE_IN_TEMP	='"	+	OPTION_TUBE_IN_TEMP	+	"',";
		condition = condition + " 	OPTION_TUBE_OUT_TEMP	='"	+	OPTION_TUBE_OUT_TEMP	+	"',";
		condition = condition + " 	OPTION_TUBE_MEDUIM	='"	+	OPTION_TUBE_MEDUIM	+	"',";
		condition = condition + " 	SHELL_MATERIAL	='"	+	SHELL_MATERIAL	+	"',";
		condition = condition + " 	TUBE_MATERIAL	='"	+	TUBE_MATERIAL	+	"',";
		condition = condition + " 	HIGH_LEFT	='"	+	HIGH_LEFT	+	"',";
		condition = condition + " 	DISPLACEMENT	='"	+	DISPLACEMENT	+	"',";
		condition = condition + " 	SPINDLE_SPEED	='"	+	SPINDLE_SPEED	+	"',";
		condition = condition + " 	IMPELLER_MEDUIM	='"	+	IMPELLER_MEDUIM	+	"',";
		condition = condition + " 	SPINDLE_MEDUIM	='"	+	SPINDLE_MEDUIM	+	"',";
		condition = condition + " 	PUMP_MEDUIM	='"	+	PUMP_MEDUIM	+	"',";
		condition = condition + " 	WIND_PRESSURE	='"	+	WIND_PRESSURE	+	"',";
		condition = condition + " 	ENGINE_NUMBER	='"	+	ENGINE_NUMBER	+	"',";
		condition = condition + " 	LICENSE_NUMBER	='"	+	LICENSE_NUMBER	+	"',";
		condition = condition + " 	CHASSIS_NUMBER	='"	+	CHASSIS_NUMBER	+	"',";
		condition = condition + " 	ENERGY_CONSUMPTION	='"	+	ENERGY_CONSUMPTION	+	"',";
			condition = condition + " 	ENERGY_CONSUMPTION_CAT	='"	+	ENERGY_CONSUMPTION_CAT	+	"',";
		condition = condition + " 	EQU_MEMO_TWO	='"	+	EQU_MEMO_TWO	+	"',";
		condition = condition + " 	EQU_MEMO_THREE	='"	+	EQU_MEMO_THREE	+	"',";
		condition = condition + " 	EQU_WHETHER_PERIODIC	='"	+	EQU_WHETHER_PERIODIC	+	"',";
		condition = condition + " 	EQUIPMENT_ATTACH_URL	='"	+	EQUIPMENT_ATTACH_URL	+	"',";
		condition = condition.trim();
		condition = condition.substring( 0, condition.length()-1 );
		condition = condition + " where EQU_POSITION_NUM = '" + EQU_POSITION_NUM + "'";
		return condition;
	}

	

}
