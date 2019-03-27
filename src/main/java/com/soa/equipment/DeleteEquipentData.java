
    /**  
    * @Title: DeleteEquipentData.java
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
import org.json.JSONException;
import org.json.JSONObject;

import com.soa.util.DataUtil;

/**
    * @ClassName: DeleteEquipentData
    * @Description: 根据设备位号，删除设备信息
    * @author yanghua
    * @date 2018年10月28日
    *
    */

public class DeleteEquipentData extends HttpServlet  {
	/**
	    * @Fields serialVersionUID : 序列化
	    */
	    
	private static final long serialVersionUID = 16L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding( "utf-8" );
		response.setContentType( "text/html;charset=utf-8" ); 
		//返回值
		JSONObject json = new JSONObject();
		
		//获取参数
		String EQU_POSITION_NUM = request.getParameter("EQU_POSITION_NUM");
		if( EQU_POSITION_NUM == null || "".equals( EQU_POSITION_NUM.trim() )) {
			try {
				json.put( "state" , 1 );
				json.put( "messge" , "删除失败,设备位号不存在" );
				json.put( "data" , 0 );
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.getWriter().write( json.toString() );
		}
		
		//保存操作历史记录信息
		int k = 0;
		try {	
			String whereCondition = " where EQU_POSITION_NUM='" + EQU_POSITION_NUM + "'";
			System.out.println("------------删除更新历史记录的where条件-------whereCondition："+ whereCondition);
			k = updateHisRecode( whereCondition, request , "delete" ) ;
			System.out.println("------------删除更新历史记录的where条件-------k："+ k);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String deleteSql = " delete from cz_equipment_info "
				+ " where EQU_POSITION_NUM = '" + EQU_POSITION_NUM + "'";
		System.out.println("----deleteSql:---------" + deleteSql );
		
		int i = 0;
		try {
			i = DataUtil.doExecuteSql( deleteSql );
		} catch (Exception e) {
			e.printStackTrace();
			i = -1;
			String sql = " delete from iot_equipment_operate_recode "
					+ " where EQU_POSITION_NUM = '" + EQU_POSITION_NUM + "'";
			DataUtil.doExecuteSql( deleteSql );
		}		
		System.out.println("----deleteSql:---------i:" + i );
		
		//返回值
		if( i > 0) {
			try {
				json.put( "state" , 0 );
				json.put( "messge" , "删除成功" );
				json.put( "data" , i );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else {
			try {
				json.put( "state" , 1 );
				json.put( "messge" , "删除失败" );
				json.put( "data" , i );
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}		
		response.getWriter().write( json.toString() );
	}
	
	public String getName( HttpServletRequest request ) {
		String name = "";
		try {		
			Cookie[] cookies = request.getCookies();
			for( Cookie c : cookies) {
				String cookieName = c.getName();
				if( !StringUtils.isBlank( cookieName ) &&  "userID".equals(cookieName)) {
					if( !StringUtils.isBlank( name ) ) {
						name = name +"," + c.getValue();
					}else {
						name = c.getValue();
					}					
				}
			}
			System.out.println("----获取操作用户姓名:---------name:" + name );
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return name;
	}
	
	public int updateHisRecode( String condition, HttpServletRequest request, String type  ) {
		//获取新增字段值
		String name = getName( request );
		
		String recode =
				"EQU_ID, " +
						"SECONDCLASS_EQUIPMENT, " +
						"WEL_NAME, WEL_UNIT, " +
						"EQU_MEMO_ONE, " + 
						"EQU_POSITION_NUM, " +
						"EQU_NAME," + 
						"MANAGE_TYPE, " +
						"EQU_MODEL, " +
						"MEARING_RANGE, " +
						"MEASURE_ACC," + 
						"EQU_INSTALL_POSITION, " +
						"EQU_MANUFACTURER, " +
						"SERIAL_NUM ," +
						"CHECK_CYCLE, " +
						"CHECK_TIME, " +
						"NEXT_CHECK_TIME, " +
						"REMARK1," +	
					"	EQU_PRODUC_DATE	," + 
					"	EQU_COMMISSION_DATE	," + 
					"	EQU_WORK_TEMP	," + 
					"	EQU_LASTPERIODIC_DATE	," + 
					"	EQU_PERIODIC_CYCLE	," + 
					"	EQU_PERIODIC_WARNDAYS	," + 
					"	PRESSURE_RANGE	," + 
					"	EXPERY_TIME	," + 
					"	INTER_SIZE	," + 
					"	DEEP_LENGTH	," + 
					"	ORDER_NUM	," + 
					"	VAVLE_TYPE	," + 
					"	ACTION_MODLE	," + 
					"	HAVE_NOT	," + 
					"	ACTUAL_MODEL	," + 
					"	ACTUAL	," + 
					"	ELEC_MODEL	," + 
					"	ELEC	," + 
					"	MEASURE_PRIN	," + 
					"	PIPE_OUTER	," + 
					"	FLUX	," + 
					"	PIPE_THICK	," + 
					"	CV	," + 
					"	POSITIONER	," + 
					"	GAS_SOURCE	," + 
					"	FLA_SIZE	," + 
					"	PROCE_LINK_TYPE	," + 
					"	UNIT	," + 
					"	COUNT	," + 
					"	POWER_RATE	," + 
					"	ELECTRIC_PRES	," + 
					"	ELECTRIC_TENSION	," + 
					"	FREQUENCY	," + 
					"	BRAND	," + 
					"	CAPCITY	," + 
					"	SPEED_RAT	," + 
					"	BEFORE_BEARING1	," + 
					"	BEFORE_BEARING2	," + 
					"	AFTER_BEARING1	," + 
					"	GREASE_INTERV	," + 
					"	GREASE_QUAN	," + 
					"	INSULATION_RATE	," + 
					"	PROTECTION_RATE	," + 
					"	EXPLOSION_RATE	," + 
					"	DEVICE_TYPE	," + 
					"	HEIGHT_ELECTRIC_TENSION	," + 
					"	PHASE_NUMBER	," + 
					"	CONNECTION_GROUP	," + 
					"	HEIGHT_ELECTRIC_PRES	," + 
					"	WEIGHT	," + 
					"	SNATCH_ELECTRIC_PRES	," + 
					"	THUNDERSTRIKE_ELECTRIC_PRES	," + 
					"	SNATCH_ELECTRIC_TENSION	," + 
					"	PEAK_TENSION	," + 
					"	CATEGORY	," + 
					"	MATERIAL	," + 
					"	CORROSION_FATIGUE	," + 
					"	EQU_DESIGN_TEMP	," + 
					"	DESIGN_PRESSURE_RANGE	," + 
					"	SURFACE_HEAT_TRANSFER	," + 
					"	DESIGN_SHELL_PRES	," + 
					"	DESIGN_SHELL_TEMP	," + 
					"	DESIGN_TUBE_TEMP	," + 
					"	DESIGN_TUBE_PRES	," + 
					"	OPTION_SHELL_PRESS	," + 
					"	OPTION_SHELL_IN_TEMP	," + 
					"	OPTION_SHELL_OUT_TEMP	," + 
					"	OPTION_SHELL_MEDUIM	," + 
					"	OPTION_TUBE_IN_TEMP	," + 
					"	OPTION_TUBE_OUT_TEMP	," + 
					"	OPTION_TUBE_MEDUIM	," + 
					"	SHELL_MATERIAL	," + 
					"	TUBE_MATERIAL	," + 
					"	HIGH_LEFT	," + 
					"	DISPLACEMENT	," + 
					"	SPINDLE_SPEED	," + 
					"	IMPELLER_MEDUIM	," + 
					"	SPINDLE_MEDUIM	," + 
					"	PUMP_MEDUIM	," + 
					"	WIND_PRESSURE	," + 
					"	ENGINE_NUMBER	," + 
					"	LICENSE_NUMBER	," + 
					"	CHASSIS_NUMBER	," + 
					"	ENERGY_CONSUMPTION	," + 
					"	ENERGY_CONSUMPTION_CAT	," + 
					"	EQU_MEMO_TWO	," + 
					"	EQU_MEMO_THREE	," + 
					"	EQU_WHETHER_PERIODIC	," + 
					"	EQUIPMENT_ATTACH_URL ";
		int i = 0;
		try {
			String sql = "insert into iot_equipment_operate_recode (name,type," + recode + 
					") select '" + name + "','" + type + "'," + recode + " from cz_equipment_info " + 
					condition; 
			i = DataUtil.doExecuteSql(sql);
			System.out.println("-------更新增加历史操作记录数据-------i:" + i);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return i;
	}

	
}
