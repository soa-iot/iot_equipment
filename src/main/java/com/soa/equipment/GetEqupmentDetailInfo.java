/*     */ package com.soa.equipment;
/*     */ import java.io.IOException;

/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.ServletRequest;
/*     */ import javax.servlet.ServletResponse;
/*     */ import javax.servlet.http.HttpServlet;

/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;

/*     */ 
/*     */ import com.soa.util.DataUtil;
/*     */ 
/*     */ public class GetEqupmentDetailInfo extends HttpServlet{

    
private static final long serialVersionUID = 1L;
public static JSONArray excelJson = new JSONArray();
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		res.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		
		//分页参数
		int  pageSize = Integer.parseInt( req.getParameter("rows") );
		int  pageNumber = Integer.parseInt( req.getParameter("page") );
		
		//通用
		String WEL_NAME = req.getParameter("WEL_NAME");
		String WEL_UNIT = req.getParameter("WEL_UNIT");
		String EQU_MEMO_ONE = req.getParameter("EQU_MEMO_ONE");
		String EQU_POSITION_NUM = req.getParameter("EQU_POSITION_NUM");
		String EQU_NAME = req.getParameter("EQU_NAME");
		
		//设备大类
		String SECONDCLASS_EQUIPMENT = req.getParameter("SECONDCLASS_EQUIPMENT");

		//压力表
		String MANAGE_TYPE = req.getParameter("MANAGE_TYPE");
		String EQU_MODEL = req.getParameter("EQU_MODEL");
		String MEARING_RANGE = req.getParameter("MEARING_RANGE");
		String MEASURE_ACC = req.getParameter("MEASURE_ACC");
		String EQU_INSTALL_POSITION = req.getParameter("EQU_INSTALL_POSITION");
		String EQU_MANUFACTURER = req.getParameter("EQU_MANUFACTURER");
		String SERIAL_NUM = req.getParameter("SERIAL_NUM");
		String CHECK_CYCLE = req.getParameter("CHECK_CYCLE");
		String CHECK_TIME = req.getParameter("CHECK_TIME");
		String NEXT_CHECK_TIME = req.getParameter("NEXT_CHECK_TIME");
		String REMARK1 = req.getParameter("REMARK1");
		
		//压力差压变送器
		String MEDUIM_TYPE = req.getParameter( "MEDUIM_TYPE" );
		
		String	EQU_PRODUC_DATE = req.getParameter("EQU_PRODUC_DATE" );
		String	EQU_COMMISSION_DATE = req.getParameter("EQU_COMMISSION_DATE" );
		String	EQU_WORK_TEMP = req.getParameter("EQU_WORK_TEMP" );
		String	EQU_LASTPERIODIC_DATE = req.getParameter("EQU_LASTPERIODIC_DATE" );
		String	EQU_PERIODIC_CYCLE = req.getParameter("EQU_PERIODIC_CYCLE" );
		String	EQU_PERIODIC_WARNDAYS = req.getParameter("EQU_PERIODIC_WARNDAYS" );
		String	PRESSURE_RANGE = req.getParameter("PRESSURE_RANGE" );
		String	EXPERY_TIME = req.getParameter("EXPERY_TIME" );
		String	INTER_SIZE = req.getParameter("INTER_SIZE" );
		String	DEEP_LENGTH = req.getParameter("DEEP_LENGTH" );
		String	ORDER_NUM = req.getParameter("ORDER_NUM" );
		String	VAVLE_TYPE = req.getParameter("VAVLE_TYPE" );
		String	ACTION_MODLE = req.getParameter("ACTION_MODLE" );
		String	HAVE_NOT = req.getParameter("HAVE_NOT" );
		String	ACTUAL_MODEL = req.getParameter("ACTUAL_MODEL" );
		String	ACTUAL = req.getParameter("ACTUAL" );
		String	ELEC_MODEL = req.getParameter("ELEC_MODEL" );
		String	ELEC = req.getParameter("ELEC" );
		String	MEASURE_PRIN = req.getParameter("MEASURE_PRIN" );
		String	PIPE_OUTER = req.getParameter("PIPE_OUTER" );
		String	FLUX = req.getParameter("FLUX" );
		String	PIPE_THICK = req.getParameter("PIPE_THICK" );
		String	CV = req.getParameter("CV" );
		String	POSITIONER = req.getParameter("POSITIONER" );
		String	GAS_SOURCE = req.getParameter("GAS_SOURCE" );
		String	FLA_SIZE = req.getParameter("FLA_SIZE" );
		String	PROCE_LINK_TYPE = req.getParameter("PROCE_LINK_TYPE" );
		String	UNIT = req.getParameter("UNIT" );
		String	COUNT = req.getParameter("COUNT" );
		String	POWER_RATE = req.getParameter("POWER_RATE" );
		String	ELECTRIC_PRES = req.getParameter("ELECTRIC_PRES" );
		String	ELECTRIC_TENSION = req.getParameter("ELECTRIC_TENSION" );
		String	FREQUENCY = req.getParameter("FREQUENCY" );
		String	BRAND = req.getParameter("BRAND" );
		String	CAPCITY = req.getParameter("CAPCITY" );
		String	SPEED_RAT = req.getParameter("SPEED_RAT" );
		String	BEFORE_BEARING1 = req.getParameter("BEFORE_BEARING1" );
		String	BEFORE_BEARING2 = req.getParameter("BEFORE_BEARING2" );
		String	AFTER_BEARING1 = req.getParameter("AFTER_BEARING1" );
		String	GREASE_INTERV = req.getParameter("GREASE_INTERV" );
		String	GREASE_QUAN = req.getParameter("GREASE_QUAN" );
		String	INSULATION_RATE = req.getParameter("INSULATION_RATE" );
		String	PROTECTION_RATE = req.getParameter("PROTECTION_RATE" );
		String	EXPLOSION_RATE = req.getParameter("EXPLOSION_RATE" );
		String	DEVICE_TYPE = req.getParameter("DEVICE_TYPE" );
		String	HEIGHT_ELECTRIC_TENSION = req.getParameter("HEIGHT_ELECTRIC_TENSION" );
		String	PHASE_NUMBER = req.getParameter("PHASE_NUMBER" );
		String	CONNECTION_GROUP = req.getParameter("CONNECTION_GROUP" );
		String	HEIGHT_ELECTRIC_PRES = req.getParameter("HEIGHT_ELECTRIC_PRES" );
		String	WEIGHT = req.getParameter("WEIGHT" );
		String	SNATCH_ELECTRIC_PRES = req.getParameter("SNATCH_ELECTRIC_PRES" );
		String	THUNDERSTRIKE_ELECTRIC_PRES = req.getParameter("THUNDERSTRIKE_ELECTRIC_PRES" );
		String	SNATCH_ELECTRIC_TENSION = req.getParameter("SNATCH_ELECTRIC_TENSION" );
		String	PEAK_TENSION = req.getParameter("PEAK_TENSION" );
		String	CATEGORY = req.getParameter("CATEGORY" );
		String	MATERIAL = req.getParameter("MATERIAL" );
		String	CORROSION_FATIGUE = req.getParameter("CORROSION_FATIGUE" );
		String	EQU_DESIGN_TEMP = req.getParameter("EQU_DESIGN_TEMP" );
		String	DESIGN_PRESSURE_RANGE = req.getParameter("DESIGN_PRESSURE_RANGE" );
		String	SURFACE_HEAT_TRANSFER = req.getParameter("SURFACE_HEAT_TRANSFER" );
		String	DESIGN_SHELL_PRES = req.getParameter("DESIGN_SHELL_PRES" );
		String	DESIGN_SHELL_TEMP = req.getParameter("DESIGN_SHELL_TEMP" );
		String	DESIGN_TUBE_TEMP = req.getParameter("DESIGN_TUBE_TEMP" );
		String	DESIGN_TUBE_PRES = req.getParameter("DESIGN_TUBE_PRES" );
		String	OPTION_SHELL_PRESS = req.getParameter("OPTION_SHELL_PRESS" );
		String	OPTION_SHELL_IN_TEMP = req.getParameter("OPTION_SHELL_IN_TEMP" );
		String	OPTION_SHELL_OUT_TEMP = req.getParameter("OPTION_SHELL_OUT_TEMP" );
		String	OPTION_SHELL_MEDUIM = req.getParameter("OPTION_SHELL_MEDUIM" );
		String	OPTION_TUBE_IN_TEMP = req.getParameter("OPTION_TUBE_IN_TEMP" );
		String	OPTION_TUBE_OUT_TEMP = req.getParameter("OPTION_TUBE_OUT_TEMP" );
		String	OPTION_TUBE_MEDUIM = req.getParameter("OPTION_TUBE_MEDUIM" );
		String	SHELL_MATERIAL = req.getParameter("SHELL_MATERIAL" );
		String	TUBE_MATERIAL = req.getParameter("TUBE_MATERIAL" );
		String	HIGH_LEFT = req.getParameter("HIGH_LEFT" );
		String	DISPLACEMENT = req.getParameter("DISPLACEMENT" );
		String	SPINDLE_SPEED = req.getParameter("SPINDLE_SPEED" );
		String	IMPELLER_MEDUIM = req.getParameter("IMPELLER_MEDUIM" );
		String	SPINDLE_MEDUIM = req.getParameter("SPINDLE_MEDUIM" );
		String	PUMP_MEDUIM = req.getParameter("PUMP_MEDUIM" );
		String	WIND_PRESSURE = req.getParameter("WIND_PRESSURE" );
		String	ENGINE_NUMBER = req.getParameter("ENGINE_NUMBER" );
		String	LICENSE_NUMBER = req.getParameter("LICENSE_NUMBER" );
		String	CHASSIS_NUMBER = req.getParameter("CHASSIS_NUMBER" );
		String	ENERGY_CONSUMPTION = req.getParameter("ENERGY_CONSUMPTION" );
		String	ENERGY_CONSUMPTION_CAT = req.getParameter("ENERGY_CONSUMPTION_CAT" );
		String	EQU_MEMO_TWO = req.getParameter("EQU_MEMO_TWO" );
		String	EQU_MEMO_THREE = req.getParameter("EQU_MEMO_THREE" );
		String	EQU_WHETHER_PERIODIC = req.getParameter("EQU_WHETHER_PERIODIC" );
		String	EQUIPMENT_ATTACH_URL = req.getParameter("EQUIPMENT_ATTACH_URL" );


		//判断查询方式
		String condition = "";
		if( SECONDCLASS_EQUIPMENT != null && !"".equals( SECONDCLASS_EQUIPMENT.trim() )) {
			if( "全部设备".equals( SECONDCLASS_EQUIPMENT.trim() )) {
				condition = createTotalCondition( WEL_NAME, WEL_UNIT, EQU_MEMO_ONE, 
						EQU_POSITION_NUM, EQU_NAME );
			}else {
				condition = createSecondEquipmentCondition( SECONDCLASS_EQUIPMENT,
						WEL_NAME, WEL_UNIT, EQU_MEMO_ONE, EQU_POSITION_NUM,
						EQU_NAME );
			}				
		}else{
			condition = createThirdCondition( WEL_NAME, WEL_UNIT, EQU_MEMO_ONE, 
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
		}
		System.out.println("lxf----------------condition:"+condition);
		json = getEquList( pageNumber, pageSize, condition);			
		res.getWriter().write( json.toString() );
	}
	
	    /**
	    * @Title: getEquList
	    * @Description: 获取数据
	    * @param @param pageNumber
	    * @param @param pageSize
	    * @param @param condition
	    * @param @return    参数
	    * @return JSONObject    返回类型
	    * @throws
	    */		    
	private JSONObject getEquList( int pageNumber, int pageSize, String condition) {
		// TODO Auto-generated method stub
		JSONObject json = new JSONObject();
		JSONArray data = new JSONArray();		
		String  countSql= "select count(*) as num from cz_equipment_info where 1=1 " + condition;
		String  dataSql= "	select  * from   ( "
				+ "				select info.*, rownum  rn  "
				+ "				from cz_equipment_info info "
				+ "				where rownum < " + (pageNumber * pageSize + 1 ) +  condition + " ) d "
				+ "				where d.rn > " + ( pageNumber - 1 ) * pageSize ;
		System.out.println("lxf----------------"+countSql);
		System.out.println("lxf----------------"+ dataSql);
		excelJson = DataUtil.getData( dataSql );
		JSONArray numJsonArr = DataUtil.getData( countSql );
		try {
			json.put( "total" , numJsonArr.getJSONObject(0).get("NUM"));
			json.put( "rows" , excelJson);
		} catch (JSONException e) {
			
		}
		return json;
	}
	
	
	    /**
	    * @Title: createTotalCondition
	    * @Description: 查询所有设备
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    */	    
	private String createTotalCondition( String WEL_NAME, String WEL_UNIT, 
			String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME ) {	
		String condition = "";
		if( isEmpty( WEL_NAME ) ) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		if( isEmpty( WEL_UNIT ) ) {
			condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
		}
		if( isEmpty( EQU_MEMO_ONE ) ) {
			condition = condition + "and EQU_MEMO_ONE like '%" + EQU_MEMO_ONE + "%'";
		}
		if( isEmpty( EQU_POSITION_NUM ) ) {
			condition = 
					condition + "and EQU_POSITION_NUM like '%" + EQU_POSITION_NUM + "%'";
		}
		if( isEmpty( EQU_NAME ) ) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		return condition;
	}
	
	
	    /**
	    * @Title: createSecondEquipmentCondition
	    * @Description: 按照二级分类查询
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    */
	    
	private String createSecondEquipmentCondition(  String SECONDCLASS_EQUIPMENT,
			String WEL_NAME,  String WEL_UNIT,  String EQU_MEMO_ONE, 
			String EQU_POSITION_NUM, String EQU_NAME ) {
		String condition = "";
		if( isEmpty( WEL_NAME ) ) {
			condition = condition + "and WEL_NAME like '%" + WEL_NAME + "%'";
		}
		if( isEmpty( WEL_UNIT ) ) {
			condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
		}
		if( isEmpty( EQU_MEMO_ONE ) ) {
			condition = condition + "and EQU_MEMO_ONE like '%" + EQU_MEMO_ONE + "%'";
		}
		if( isEmpty( EQU_POSITION_NUM ) ) {
			condition = 
					condition + "and EQU_POSITION_NUM like '%" + EQU_POSITION_NUM + "%'";
		}
		if( isEmpty( EQU_NAME ) ) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		if( isEmpty( SECONDCLASS_EQUIPMENT ) ) {
			condition = condition + 
					"and SECONDCLASS_EQUIPMENT like '%" + SECONDCLASS_EQUIPMENT + "%'";
		}
		return condition;
	}
	
	
	    /**
	    * @Title: createThirdCondition
	    * @Description: 按照三级分类查询
	    * @param @param WEL_NAME
	    * @param @param WEL_UNIT
	    * @param @param EQU_MEMO_ONE
	    * @param @param EQU_POSITION_NUM
	    * @param @param EQU_NAME
	    * @param @param MANAGE_TYPE
	    * @param @param EQU_MODEL
	    * @param @param MEARING_RANGE
	    * @param @param MEASURE_ACC
	    * @param @param EQU_INSTALL_POSITION
	    * @param @param EQU_MANUFACTURER
	    * @param @param SERIAL_NUM
	    * @param @param CHECK_CYCLE
	    * @param @param CHECK_TIME
	    * @param @param NEXT_CHECK_TIME
	    * @param @param REMARK1
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    */
	    
	private String createThirdCondition( String WEL_NAME, String WEL_UNIT, 
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
			String	EQUIPMENT_ATTACH_URL	
			){
		String condition = "";
		if( isEmpty( WEL_NAME ) ) {
			condition = condition + "and WEL_NAME like '%" + WEL_NAME + "%'";
		}
		if( isEmpty( WEL_UNIT ) ) {
			condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
		}
		if( isEmpty( EQU_MEMO_ONE ) ) {
			condition = condition + "and EQU_MEMO_ONE like '%" + EQU_MEMO_ONE + "%'";
		}
		if( isEmpty( EQU_POSITION_NUM ) ) {
			condition = 
					condition + "and EQU_POSITION_NUM like '%" + EQU_POSITION_NUM + "%'";
		}
		if( isEmpty( EQU_NAME ) ) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		if( isEmpty( MANAGE_TYPE ) ) {
			condition = condition + "and MANAGE_TYPE like '%" + MANAGE_TYPE + "%'";
		}
		if( isEmpty( EQU_MODEL ) ) {
			condition = condition + "and EQU_MODEL like '%" + EQU_MODEL + "%'";
		}
		if( isEmpty( MEARING_RANGE ) ) {
			condition = condition + "and MEARING_RANGE like '%" + MEARING_RANGE + "%'";
		}
		if( isEmpty( MEASURE_ACC ) ) {
			condition = condition + "and MEASURE_ACC like '%" + MEASURE_ACC + "%'";
		}
		if( isEmpty( EQU_INSTALL_POSITION ) ) {
			condition = 
					condition + "and EQU_INSTALL_POSITION like '%" + 
					EQU_INSTALL_POSITION + "%'";
		}
		if( isEmpty( EQU_MANUFACTURER ) ) {
			condition = condition + "and EQU_MANUFACTURER like '%" + EQU_MANUFACTURER + "%'";
		}
		if( isEmpty( SERIAL_NUM ) ) {
			condition = condition + "and SERIAL_NUM like '%" + SERIAL_NUM + "%'";
		}
		if( isEmpty( CHECK_CYCLE ) ) {
			condition = condition + "and CHECK_CYCLE like '%" + CHECK_CYCLE + "%'";
		}
		if( isEmpty( CHECK_TIME ) ) {
			condition = condition + "and CHECK_TIME like '%" + CHECK_TIME + "%'";
		}
		if( isEmpty( NEXT_CHECK_TIME ) ) {
			condition = condition + "and NEXT_CHECK_TIME like '%" + NEXT_CHECK_TIME + "%'";
		}
		if( isEmpty( REMARK1 ) ) {
			condition = condition + "and REMARK1 like '%" + REMARK1 + "%'";
		}
		if( isEmpty( MEDUIM_TYPE ) ) {
			condition = condition + "and MEDUIM_TYPE like '%" + MEDUIM_TYPE + "%'";
		}
		
		if(	isEmpty(	EQU_PRODUC_DATE	)){	condition = condition + "and  	EQU_PRODUC_DATE	like '%" +	EQU_PRODUC_DATE	+ "%'";}
		if(	isEmpty(	EQU_COMMISSION_DATE	)){	condition = condition + "and  	EQU_COMMISSION_DATE	like '%" +	EQU_COMMISSION_DATE	+ "%'";}
		if(	isEmpty(	EQU_WORK_TEMP	)){	condition = condition + "and  	EQU_WORK_TEMP	like '%" +	EQU_WORK_TEMP	+ "%'";}
		if(	isEmpty(	EQU_LASTPERIODIC_DATE	)){	condition = condition + "and  	EQU_LASTPERIODIC_DATE	like '%" +	EQU_LASTPERIODIC_DATE	+ "%'";}
		if(	isEmpty(	EQU_PERIODIC_CYCLE	)){	condition = condition + "and  	EQU_PERIODIC_CYCLE	like '%" +	EQU_PERIODIC_CYCLE	+ "%'";}
		if(	isEmpty(	EQU_PERIODIC_WARNDAYS	)){	condition = condition + "and  	EQU_PERIODIC_WARNDAYS	like '%" +	EQU_PERIODIC_WARNDAYS	+ "%'";}
		if(	isEmpty(	PRESSURE_RANGE	)){	condition = condition + "and  	PRESSURE_RANGE	like '%" +	PRESSURE_RANGE	+ "%'";}
		if(	isEmpty(	EXPERY_TIME	)){	condition = condition + "and  	EXPERY_TIME	like '%" +	EXPERY_TIME	+ "%'";}
		if(	isEmpty(	INTER_SIZE	)){	condition = condition + "and  	INTER_SIZE	like '%" +	INTER_SIZE	+ "%'";}
		if(	isEmpty(	DEEP_LENGTH	)){	condition = condition + "and  	DEEP_LENGTH	like '%" +	DEEP_LENGTH	+ "%'";}
		if(	isEmpty(	ORDER_NUM	)){	condition = condition + "and  	ORDER_NUM	like '%" +	ORDER_NUM	+ "%'";}
		if(	isEmpty(	VAVLE_TYPE	)){	condition = condition + "and  	VAVLE_TYPE	like '%" +	VAVLE_TYPE	+ "%'";}
		if(	isEmpty(	ACTION_MODLE	)){	condition = condition + "and  	ACTION_MODLE	like '%" +	ACTION_MODLE	+ "%'";}
		if(	isEmpty(	HAVE_NOT	)){	condition = condition + "and  	HAVE_NOT	like '%" +	HAVE_NOT	+ "%'";}
		if(	isEmpty(	ACTUAL_MODEL	)){	condition = condition + "and  	ACTUAL_MODEL	like '%" +	ACTUAL_MODEL	+ "%'";}
		if(	isEmpty(	ACTUAL	)){	condition = condition + "and  	ACTUAL	like '%" +	ACTUAL	+ "%'";}
		if(	isEmpty(	ELEC_MODEL	)){	condition = condition + "and  	ELEC_MODEL	like '%" +	ELEC_MODEL	+ "%'";}
		if(	isEmpty(	ELEC	)){	condition = condition + "and  	ELEC	like '%" +	ELEC	+ "%'";}
		if(	isEmpty(	MEASURE_PRIN	)){	condition = condition + "and  	MEASURE_PRIN	like '%" +	MEASURE_PRIN	+ "%'";}
		if(	isEmpty(	PIPE_OUTER	)){	condition = condition + "and  	PIPE_OUTER	like '%" +	PIPE_OUTER	+ "%'";}
		if(	isEmpty(	FLUX	)){	condition = condition + "and  	FLUX	like '%" +	FLUX	+ "%'";}
		if(	isEmpty(	PIPE_THICK	)){	condition = condition + "and  	PIPE_THICK	like '%" +	PIPE_THICK	+ "%'";}
		if(	isEmpty(	CV	)){	condition = condition + "and  	CV	like '%" +	CV	+ "%'";}
		if(	isEmpty(	POSITIONER	)){	condition = condition + "and  	POSITIONER	like '%" +	POSITIONER	+ "%'";}
		if(	isEmpty(	GAS_SOURCE	)){	condition = condition + "and  	GAS_SOURCE	like '%" +	GAS_SOURCE	+ "%'";}
		if(	isEmpty(	FLA_SIZE	)){	condition = condition + "and  	FLA_SIZE	like '%" +	FLA_SIZE	+ "%'";}
		if(	isEmpty(	PROCE_LINK_TYPE	)){	condition = condition + "and  	PROCE_LINK_TYPE	like '%" +	PROCE_LINK_TYPE	+ "%'";}
		if(	isEmpty(	UNIT	)){	condition = condition + "and  	UNIT	like '%" +	UNIT	+ "%'";}
		if(	isEmpty(	COUNT	)){	condition = condition + "and  	COUNT	like '%" +	COUNT	+ "%'";}
		if(	isEmpty(	POWER_RATE	)){	condition = condition + "and  	POWER_RATE	like '%" +	POWER_RATE	+ "%'";}
		if(	isEmpty(	ELECTRIC_PRES	)){	condition = condition + "and  	ELECTRIC_PRES	like '%" +	ELECTRIC_PRES	+ "%'";}
		if(	isEmpty(	ELECTRIC_TENSION	)){	condition = condition + "and  	ELECTRIC_TENSION	like '%" +	ELECTRIC_TENSION	+ "%'";}
		if(	isEmpty(	FREQUENCY	)){	condition = condition + "and  	FREQUENCY	like '%" +	FREQUENCY	+ "%'";}
		if(	isEmpty(	BRAND	)){	condition = condition + "and  	BRAND	like '%" +	BRAND	+ "%'";}
		if(	isEmpty(	CAPCITY	)){	condition = condition + "and  	CAPCITY	like '%" +	CAPCITY	+ "%'";}
		if(	isEmpty(	SPEED_RAT	)){	condition = condition + "and  	SPEED_RAT	like '%" +	SPEED_RAT	+ "%'";}
		if(	isEmpty(	BEFORE_BEARING1	)){	condition = condition + "and  	BEFORE_BEARING1	like '%" +	BEFORE_BEARING1	+ "%'";}
		if(	isEmpty(	BEFORE_BEARING2	)){	condition = condition + "and  	BEFORE_BEARING2	like '%" +	BEFORE_BEARING2	+ "%'";}
		if(	isEmpty(	AFTER_BEARING1	)){	condition = condition + "and  	AFTER_BEARING1	like '%" +	AFTER_BEARING1	+ "%'";}
		if(	isEmpty(	GREASE_INTERV	)){	condition = condition + "and  	GREASE_INTERV	like '%" +	GREASE_INTERV	+ "%'";}
		if(	isEmpty(	GREASE_QUAN	)){	condition = condition + "and  	GREASE_QUAN	like '%" +	GREASE_QUAN	+ "%'";}
		if(	isEmpty(	INSULATION_RATE	)){	condition = condition + "and  	INSULATION_RATE	like '%" +	INSULATION_RATE	+ "%'";}
		if(	isEmpty(	PROTECTION_RATE	)){	condition = condition + "and  	PROTECTION_RATE	like '%" +	PROTECTION_RATE	+ "%'";}
		if(	isEmpty(	EXPLOSION_RATE	)){	condition = condition + "and  	EXPLOSION_RATE	like '%" +	EXPLOSION_RATE	+ "%'";}
		if(	isEmpty(	DEVICE_TYPE	)){	condition = condition + "and  	DEVICE_TYPE	like '%" +	DEVICE_TYPE	+ "%'";}
		if(	isEmpty(	HEIGHT_ELECTRIC_TENSION	)){	condition = condition + "and  	HEIGHT_ELECTRIC_TENSION	like '%" +	HEIGHT_ELECTRIC_TENSION	+ "%'";}
		if(	isEmpty(	PHASE_NUMBER	)){	condition = condition + "and  	PHASE_NUMBER	like '%" +	PHASE_NUMBER	+ "%'";}
		if(	isEmpty(	CONNECTION_GROUP	)){	condition = condition + "and  	CONNECTION_GROUP	like '%" +	CONNECTION_GROUP	+ "%'";}
		if(	isEmpty(	HEIGHT_ELECTRIC_PRES	)){	condition = condition + "and  	HEIGHT_ELECTRIC_PRES	like '%" +	HEIGHT_ELECTRIC_PRES	+ "%'";}
		if(	isEmpty(	WEIGHT	)){	condition = condition + "and  	WEIGHT	like '%" +	WEIGHT	+ "%'";}
		if(	isEmpty(	SNATCH_ELECTRIC_PRES	)){	condition = condition + "and  	SNATCH_ELECTRIC_PRES	like '%" +	SNATCH_ELECTRIC_PRES	+ "%'";}
		if(	isEmpty(	THUNDERSTRIKE_ELECTRIC_PRES	)){	condition = condition + "and  	THUNDERSTRIKE_ELECTRIC_PRES	like '%" +	THUNDERSTRIKE_ELECTRIC_PRES	+ "%'";}
		if(	isEmpty(	SNATCH_ELECTRIC_TENSION	)){	condition = condition + "and  	SNATCH_ELECTRIC_TENSION	like '%" +	SNATCH_ELECTRIC_TENSION	+ "%'";}
		if(	isEmpty(	PEAK_TENSION	)){	condition = condition + "and  	PEAK_TENSION	like '%" +	PEAK_TENSION	+ "%'";}
		if(	isEmpty(	CATEGORY	)){	condition = condition + "and  	CATEGORY	like '%" +	CATEGORY	+ "%'";}
		if(	isEmpty(	MATERIAL	)){	condition = condition + "and  	MATERIAL	like '%" +	MATERIAL	+ "%'";}
		if(	isEmpty(	CORROSION_FATIGUE	)){	condition = condition + "and  	CORROSION_FATIGUE	like '%" +	CORROSION_FATIGUE	+ "%'";}
		if(	isEmpty(	EQU_DESIGN_TEMP	)){	condition = condition + "and  	EQU_DESIGN_TEMP	like '%" +	EQU_DESIGN_TEMP	+ "%'";}
		if(	isEmpty(	DESIGN_PRESSURE_RANGE	)){	condition = condition + "and  	DESIGN_PRESSURE_RANGE	like '%" +	DESIGN_PRESSURE_RANGE	+ "%'";}
		if(	isEmpty(	SURFACE_HEAT_TRANSFER	)){	condition = condition + "and  	SURFACE_HEAT_TRANSFER	like '%" +	SURFACE_HEAT_TRANSFER	+ "%'";}
		if(	isEmpty(	DESIGN_SHELL_PRES	)){	condition = condition + "and  	DESIGN_SHELL_PRES	like '%" +	DESIGN_SHELL_PRES	+ "%'";}
		if(	isEmpty(	DESIGN_SHELL_TEMP	)){	condition = condition + "and  	DESIGN_SHELL_TEMP	like '%" +	DESIGN_SHELL_TEMP	+ "%'";}
		if(	isEmpty(	DESIGN_TUBE_TEMP	)){	condition = condition + "and  	DESIGN_TUBE_TEMP	like '%" +	DESIGN_TUBE_TEMP	+ "%'";}
		if(	isEmpty(	DESIGN_TUBE_PRES	)){	condition = condition + "and  	DESIGN_TUBE_PRES	like '%" +	DESIGN_TUBE_PRES	+ "%'";}
		if(	isEmpty(	OPTION_SHELL_PRESS	)){	condition = condition + "and  	OPTION_SHELL_PRESS	like '%" +	OPTION_SHELL_PRESS	+ "%'";}
		if(	isEmpty(	OPTION_SHELL_IN_TEMP	)){	condition = condition + "and  	OPTION_SHELL_IN_TEMP	like '%" +	OPTION_SHELL_IN_TEMP	+ "%'";}
		if(	isEmpty(	OPTION_SHELL_OUT_TEMP	)){	condition = condition + "and  	OPTION_SHELL_OUT_TEMP	like '%" +	OPTION_SHELL_OUT_TEMP	+ "%'";}
		if(	isEmpty(	OPTION_SHELL_MEDUIM	)){	condition = condition + "and  	OPTION_SHELL_MEDUIM	like '%" +	OPTION_SHELL_MEDUIM	+ "%'";}
		if(	isEmpty(	OPTION_TUBE_IN_TEMP	)){	condition = condition + "and  	OPTION_TUBE_IN_TEMP	like '%" +	OPTION_TUBE_IN_TEMP	+ "%'";}
		if(	isEmpty(	OPTION_TUBE_OUT_TEMP	)){	condition = condition + "and  	OPTION_TUBE_OUT_TEMP	like '%" +	OPTION_TUBE_OUT_TEMP	+ "%'";}
		if(	isEmpty(	OPTION_TUBE_MEDUIM	)){	condition = condition + "and  	OPTION_TUBE_MEDUIM	like '%" +	OPTION_TUBE_MEDUIM	+ "%'";}
		if(	isEmpty(	SHELL_MATERIAL	)){	condition = condition + "and  	SHELL_MATERIAL	like '%" +	SHELL_MATERIAL	+ "%'";}
		if(	isEmpty(	TUBE_MATERIAL	)){	condition = condition + "and  	TUBE_MATERIAL	like '%" +	TUBE_MATERIAL	+ "%'";}
		if(	isEmpty(	HIGH_LEFT	)){	condition = condition + "and  	HIGH_LEFT	like '%" +	HIGH_LEFT	+ "%'";}
		if(	isEmpty(	DISPLACEMENT	)){	condition = condition + "and  	DISPLACEMENT	like '%" +	DISPLACEMENT	+ "%'";}
		if(	isEmpty(	SPINDLE_SPEED	)){	condition = condition + "and  	SPINDLE_SPEED	like '%" +	SPINDLE_SPEED	+ "%'";}
		if(	isEmpty(	IMPELLER_MEDUIM	)){	condition = condition + "and  	IMPELLER_MEDUIM	like '%" +	IMPELLER_MEDUIM	+ "%'";}
		if(	isEmpty(	SPINDLE_MEDUIM	)){	condition = condition + "and  	SPINDLE_MEDUIM	like '%" +	SPINDLE_MEDUIM	+ "%'";}
		if(	isEmpty(	PUMP_MEDUIM	)){	condition = condition + "and  	PUMP_MEDUIM	like '%" +	PUMP_MEDUIM	+ "%'";}
		if(	isEmpty(	WIND_PRESSURE	)){	condition = condition + "and  	WIND_PRESSURE	like '%" +	WIND_PRESSURE	+ "%'";}
		if(	isEmpty(	ENGINE_NUMBER	)){	condition = condition + "and  	ENGINE_NUMBER	like '%" +	ENGINE_NUMBER	+ "%'";}
		if(	isEmpty(	LICENSE_NUMBER	)){	condition = condition + "and  	LICENSE_NUMBER	like '%" +	LICENSE_NUMBER	+ "%'";}
		if(	isEmpty(	CHASSIS_NUMBER	)){	condition = condition + "and  	CHASSIS_NUMBER	like '%" +	CHASSIS_NUMBER	+ "%'";}
		if(	isEmpty(	ENERGY_CONSUMPTION	)){	condition = condition + "and  	ENERGY_CONSUMPTION	like '%" +	ENERGY_CONSUMPTION	+ "%'";}
		if(	isEmpty(	ENERGY_CONSUMPTION_CAT	)){	condition = condition + "and  	ENERGY_CONSUMPTION_CAT	like '%" +	ENERGY_CONSUMPTION_CAT	+ "%'";}
		if(	isEmpty(	EQU_MEMO_TWO	)){	condition = condition + "and  	EQU_MEMO_TWO	like '%" +	EQU_MEMO_TWO	+ "%'";}
		if(	isEmpty(	EQU_MEMO_THREE	)){	condition = condition + "and  	EQU_MEMO_THREE	like '%" +	EQU_MEMO_THREE	+ "%'";}
		if(	isEmpty(	EQU_WHETHER_PERIODIC	)){	condition = condition + "and  	EQU_WHETHER_PERIODIC	like '%" +	EQU_WHETHER_PERIODIC	+ "%'";}
		if(	isEmpty(	EQUIPMENT_ATTACH_URL	)){	condition = condition + "and  	EQUIPMENT_ATTACH_URL	like '%" +	EQUIPMENT_ATTACH_URL	+ "%'";}

		return condition;
	}
	
	    /**
	    * @Title: isEmpty
	    * @Description: 判断字符串是否为空部分加以封装
	    * @param @param name
	    * @param @return    参数
	    * @return Boolean    返回类型
	    * @throws
	    */
	    
	private  Boolean isEmpty(String  name ) {
		if(!(name ==null ||"".equals(name))){
			return true;
 		}
		return false;
		
	};

}
