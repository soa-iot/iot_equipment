package com.soa.equipment;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.soa.util.DataUtil;
import com.soa.util.Im_ExportExcel;
public class ModifyEquipmentBatchServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private String excelContents[][];

	protected void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		String nowDate = sdf.format(new Date(System.currentTimeMillis()));
		String excelName = (new StringBuilder(String.valueOf(nowDate)))
				.append("设备基础信息").toString();
		res.setHeader("Content-Disposition",
				(new StringBuilder("attachment;filename="))
				.append(new String(excelName.getBytes("gb2312"), "ISO8859-1"))
				.append(".xls").toString());
		res.setHeader("Connection", "close");
		res.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
		OutputStream out = res.getOutputStream();
				
		/*
		 * 参数定义
		 */
		//设备对应的后端附件存储位置,初始化excel配置信息
		Map<String,String> equipAttachLocationUrlConfig = new HashMap<String,String>();
		equipAttachLocationUrlConfig.put( "压力表", "E:/仪表设备/仪表设备/压力表" );
		equipAttachLocationUrlConfig.put( "压力差压变送器", "E:/仪表设备/压力差压变送器" );
		equipAttachLocationUrlConfig.put( "温度计", "E:/仪表设备/温度计" );
		equipAttachLocationUrlConfig.put( "温度变送器", "E:/仪表设备/温度变送器" );
		equipAttachLocationUrlConfig.put( "气动切断阀", "E:/仪表设备/气动切断阀" );
		equipAttachLocationUrlConfig.put( "气动调节阀", "E:/仪表设备/气动调节阀" );
		equipAttachLocationUrlConfig.put( "液位计(含远程)", "E:/仪表设备/液位计(含远程)" );
		equipAttachLocationUrlConfig.put( "流量计", "E:/仪表设备/流量计" );
		equipAttachLocationUrlConfig.put( "节流装置", "E:/仪表设备/节流装置" );
		equipAttachLocationUrlConfig.put( "在线分析仪", "E:/仪表设备/在线分析仪" );
		equipAttachLocationUrlConfig.put( "振动温度探头", "E:/仪表设备/振动温度探头" );
		equipAttachLocationUrlConfig.put( "DCS SIS系统", "E:/仪表设备/DCS/SIS系统" );
		equipAttachLocationUrlConfig.put( "FGS系统", "E:/仪表设备/FGS系统" );
		equipAttachLocationUrlConfig.put( "固定式报警仪", "E:/仪表设备/固定式报警仪" );
		equipAttachLocationUrlConfig.put( "其他", "E:/仪表设备/其他" );
		equipAttachLocationUrlConfig.put( "C类设备", "E:/机械设备/C类设备" );
		equipAttachLocationUrlConfig.put( "D类设备", "E:/机械设备/D类设备" );
		equipAttachLocationUrlConfig.put( "E类设备", "E:/机械设备/D类设备" );
		equipAttachLocationUrlConfig.put( "F类设备", "E:/机械设备/F类设备" );
		equipAttachLocationUrlConfig.put( "H类设备", "E:/机械设备/H类设备" );
		equipAttachLocationUrlConfig.put( "R类设备", "E:/机械设备/R类设备" );
		equipAttachLocationUrlConfig.put( "P类设备", "E:/机械设备/P类设备" );
		equipAttachLocationUrlConfig.put( "K类设备", "E:/机械设备/K类设备" );
		equipAttachLocationUrlConfig.put( "机修类", "E:/机械设备/机修类" );
		equipAttachLocationUrlConfig.put( "车辆类", "E:/机械设备/车辆类" );
		equipAttachLocationUrlConfig.put( "其他", "E:/机械设备/其他" );
		equipAttachLocationUrlConfig.put( "EPS电源系统", "E:/机械设备/EPS电源系统" );
		equipAttachLocationUrlConfig.put( "UPS电源系统", "E:/机械设备/UPS电源系统" );
		equipAttachLocationUrlConfig.put( "低压配电柜", "E:/机械设备/低压配电柜" );
		equipAttachLocationUrlConfig.put( "电动机", "E:/机械设备/电动机" );
		equipAttachLocationUrlConfig.put( "干式变压器", "E:/机械设备/干式变压器" );
		equipAttachLocationUrlConfig.put( "高压配电柜", "E:/机械设备/高压配电柜" );
		equipAttachLocationUrlConfig.put( "现场配电箱", "E:/机械设备/现场配电箱" );
		equipAttachLocationUrlConfig.put( "直流电源系统", "E:/机械设备/直流电源系统" );
		equipAttachLocationUrlConfig.put( "A类分析仪器", "E:/机械设备/A类分析仪器" );

		//Excel标题配置信息
		Map<String,String> excelTitleConfig = new HashMap<String,String>();
		excelTitleConfig.put( "压力表", "遂宁龙王庙天然气净化厂压力表台账" );
		excelTitleConfig.put( "压力差压变送器", "遂宁龙王庙天然气净化厂压力差压变送器台账" );
		excelTitleConfig.put( "温度计", "遂宁龙王庙天然气净化厂温度计台账" );
		excelTitleConfig.put( "温度变送器", "遂宁龙王庙天然气净化厂温度变送器台账" );
		excelTitleConfig.put( "气动切断阀", "遂宁龙王庙天然气净化厂气动切断阀台账" );
		excelTitleConfig.put( "气动调节阀", "遂宁龙王庙天然气净化厂气动调节阀台账" );
		excelTitleConfig.put( "液位计(含远程)", "遂宁龙王庙天然气净化厂液位计(含远程)台账" );
		excelTitleConfig.put( "流量计", "遂宁龙王庙天然气净化厂流量计台账" );
		excelTitleConfig.put( "节流装置", "遂宁龙王庙天然气净化厂节流装置台账" );
		excelTitleConfig.put( "在线分析仪", "遂宁龙王庙天然气净化厂在线分析仪台账" );
		excelTitleConfig.put( "振动温度探头", "遂宁龙王庙天然气净化厂振动温度探头台账" );
		excelTitleConfig.put( "DCS SIS系统", "遂宁龙王庙天然气净化厂DCS SIS系统台账" );
		excelTitleConfig.put( "FGS系统", "遂宁龙王庙天然气净化厂FGS系统台账" );
		excelTitleConfig.put( "固定式报警仪", "遂宁龙王庙天然气净化厂固定式报警仪台账" );
		excelTitleConfig.put( "其它", "遂宁龙王庙天然气净化厂其它台账" );
		excelTitleConfig.put( "P类设备", "遂宁龙王庙天然气净化厂P类台账" );
		excelTitleConfig.put( "K类设备", "遂宁龙王庙天然气净化厂K类台账" );
		excelTitleConfig.put( "C类设备", "遂宁龙王庙天然气净化厂C类设备台账" );
		excelTitleConfig.put( "D类设备", "遂宁龙王庙天然气净化厂D类设备台账" );
		excelTitleConfig.put( "E类设备", "遂宁龙王庙天然气净化厂E类设备台账" );
		excelTitleConfig.put( "F类设备", "遂宁龙王庙天然气净化厂F类设备台账" );
		excelTitleConfig.put( "H类设备", "遂宁龙王庙天然气净化厂H类设备台账" );
		excelTitleConfig.put( "R类设备", "遂宁龙王庙天然气净化厂R类设备台账" );
		excelTitleConfig.put( "机修类", "遂宁龙王庙天然气净化厂机修类台账" );
		excelTitleConfig.put( "车辆类", "遂宁龙王庙天然气净化厂车辆类台账" );
		excelTitleConfig.put( "其他", "遂宁龙王庙天然气净化厂其他台账" );
		excelTitleConfig.put( "EPS电源系统", "遂宁龙王庙天然气净化厂EPS电源系统台账" );
		excelTitleConfig.put( "UPS电源系统", "遂宁龙王庙天然气净化厂UPS电源系统台账" );
		excelTitleConfig.put( "低压配电柜", "遂宁龙王庙天然气净化厂低压配电柜台账" );
		excelTitleConfig.put( "电动机", "遂宁龙王庙天然气净化厂电动机台账" );
		excelTitleConfig.put( "干式变压器", "遂宁龙王庙天然气净化厂干式变压器台账" );
		excelTitleConfig.put( "高压配电柜", "遂宁龙王庙天然气净化厂高压配电柜台账" );
		excelTitleConfig.put( "现场配电箱", "遂宁龙王庙天然气净化厂现场配电箱台账" );
		excelTitleConfig.put( "直流电源系统", "遂宁龙王庙天然气净化厂直流电源系统台账" );
		excelTitleConfig.put( "A类分析仪器", "遂宁龙王庙天然气净化厂A类分析仪器台账" );

		
		//Excel表头配置信息
		Map<String,Map<String,String>> excelHeadConfig = 
				new HashMap<String,Map<String,String>>();
		//压力表具体表头配置
		Map<String,String> excelHeadDetailConfig = new LinkedHashMap<String,String>();
		excelHeadDetailConfig.put( "order", "序号" );
		excelHeadDetailConfig.put( "WEL_NAME", "装置列名" );
		excelHeadDetailConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadDetailConfig.put( "EQU_POSITION_NUM", "设备位号" );
		excelHeadDetailConfig.put( "EQU_MEMO_ONE", "设备类别" );		
		excelHeadDetailConfig.put( "EQU_NAME", "设备名称" );
		excelHeadDetailConfig.put( "MANAGE_TYPE", "器具类别" );
		excelHeadDetailConfig.put( "EQU_MODEL", "规格型号" );
		excelHeadDetailConfig.put( "MEARING_RANGE", "测量范围" );
		excelHeadDetailConfig.put( "MEASURE_ACC", "准确等级" );
		excelHeadDetailConfig.put( "EQU_INSTALL_POSITION", "安装地点" );
		excelHeadDetailConfig.put( "EQU_MANUFACTURER", "生产厂家" );
		excelHeadDetailConfig.put( "SERIAL_NUM", "出厂编号" );
		excelHeadDetailConfig.put( "CHECK_CYCLE", "检定周期" );
		excelHeadDetailConfig.put( "CHECK_TIME", "检定日期" );
		excelHeadDetailConfig.put( "NEXT_CHECK_TIME", "下次检定" );
		excelHeadDetailConfig.put( "REMARK1", "备注信息" );	
		
		//压力差压变送器具体表头配置
		Map<String,String> excelHeadYCBConfig = new LinkedHashMap<String,String>();
		excelHeadYCBConfig.put( "order", "序号") ;
		excelHeadYCBConfig.put( "WEL_NAME", "装置列名" );
		excelHeadYCBConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadYCBConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadYCBConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadYCBConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadYCBConfig.put( "MANAGE_TYPE", "器具类别") ;
		excelHeadYCBConfig.put( "EQU_MODEL", "规格型号") ;
		excelHeadYCBConfig.put( "MEARING_RANGE", "测量范围") ;
		excelHeadYCBConfig.put( "MEASURE_ACC", "精 度") ;
		excelHeadYCBConfig.put( "EQU_INSTALL_POSITION", "安装地点") ;
		excelHeadYCBConfig.put( "EQU_MANUFACTURER", "生产厂家") ;
		excelHeadYCBConfig.put( "SERIAL_NUM", "出厂编号" );
		excelHeadYCBConfig.put( "CHECK_CYCLE", "检定周期" );
		excelHeadYCBConfig.put( "CHECK_TIME", "检定日期" );
		excelHeadYCBConfig.put( "NEXT_CHECK_TIME", "有效期" );
		excelHeadYCBConfig.put( "REMARK1", "备注信息" );	
		
		//温度计具体表头配置
		Map<String,String> excelHeadWDJConfig = new LinkedHashMap<String,String>();
		excelHeadWDJConfig.put( "order", "序号") ;
		excelHeadWDJConfig.put( "WEL_NAME", "装置列名" );
		excelHeadWDJConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadWDJConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadWDJConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadWDJConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadWDJConfig.put( "EQU_MODEL" ,"规格型号" );
		excelHeadWDJConfig.put( "EQU_INSTALL_POSITION", "安装地点" );
		excelHeadWDJConfig.put( "DEEP_LENGTH", "插深" );
		excelHeadWDJConfig.put( "INTER_SIZE", "接口尺寸");
		excelHeadWDJConfig.put( "MEDUIM_TYPE", "测量介质");
		excelHeadWDJConfig.put( "MEARING_RANGE", "测量范围");
		excelHeadWDJConfig.put( "MEASURE_ACC", "精度");				
		excelHeadWDJConfig.put( "EQU_MANUFACTURER", "生产厂家");
		excelHeadWDJConfig.put( "SERIAL_NUM", "出厂编号");
		excelHeadWDJConfig.put( "CHECK_CYCLE", "检定周期");
		excelHeadWDJConfig.put( "CHECK_TIME", "检定日期");
		excelHeadWDJConfig.put( "NEXT_CHECK_TIME", "有效期");
		excelHeadWDJConfig.put( "REMARK1", "备注信息");	
		
		//温度变送器具体表头配置
		Map<String,String> excelHeadWBConfig = new LinkedHashMap<String,String>();
		excelHeadWBConfig.put( "order", "序号") ;
		excelHeadWBConfig.put( "WEL_NAME", "装置列名" );
		excelHeadWBConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadWBConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadWBConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadWBConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadWBConfig.put( "ORDER_NUM", "分度号") ;
		excelHeadWBConfig.put( "EQU_MODEL", "规格型号") ;
		excelHeadWBConfig.put( "EQU_INSTALL_POSITION", "安装地点") ;
		excelHeadWBConfig.put( "DEEP_LENGTH", "插深") ;
		excelHeadWBConfig.put( "INTER_SIZE", "接口尺寸") ;
		excelHeadWBConfig.put( "MEDUIM_TYPE", "测量介质") ;
		excelHeadWBConfig.put( "MEARING_RANGE", "测量范围") ;
		excelHeadWBConfig.put( "MEASURE_ACC", "精度") ;
		excelHeadWBConfig.put( "EQU_MANUFACTURER", "生产厂家") ;
		excelHeadWBConfig.put( "SERIAL_NUM", "出厂编号") ;
		excelHeadWBConfig.put( "CHECK_TIME", "测试日期") ;
		excelHeadWBConfig.put( "CHECK_CYCLE", "周期") ;
		excelHeadWBConfig.put( "REMARK1", "备注信息") ;


		
		//气动切断阀具体表头配置
		Map<String,String> excelHeadQDFConfig = new LinkedHashMap<String,String>();
		excelHeadQDFConfig.put( "order", "序号") ;
		excelHeadQDFConfig.put( "WEL_NAME", "装置列名" );
		excelHeadQDFConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadQDFConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadQDFConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadQDFConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadQDFConfig.put( "EQU_MODEL", "规格型号") ;
		excelHeadQDFConfig.put( "EQU_INSTALL_POSITION", "安装地点") ;
		excelHeadQDFConfig.put( "MEDUIM_TYPE", "介质" ) ; 
		excelHeadQDFConfig.put( "FLA_SIZE", "法兰规格" ) ; 
		excelHeadQDFConfig.put( "ACTION_MODLE", "作用方式" ) ; 
		excelHeadQDFConfig.put( "HAVE_NOT", "有无手轮" ) ; 
		excelHeadQDFConfig.put( "ACTUAL_MODEL", "执行机构型号" ) ; 
		excelHeadQDFConfig.put( "VAVLE_TYPE", "电磁阀型号" ) ; 
		excelHeadQDFConfig.put( "EQU_MANUFACTURER", "生产厂家" ) ; 
		excelHeadQDFConfig.put( "SERIAL_NUM", "出厂编号" ) ; 
		excelHeadQDFConfig.put( "REMARK1", "备注信息" ) ; 	
		
		//气动调节阀具体表头配置
		Map<String,String> excelHeadTJFConfig = new LinkedHashMap<String,String>();
		excelHeadTJFConfig.put( "order", "序号") ;
		excelHeadTJFConfig.put( "WEL_NAME", "装置列名" );
		excelHeadTJFConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadTJFConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadTJFConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadTJFConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadTJFConfig.put( "EQU_MODEL", "规格型号") ;
		excelHeadTJFConfig.put( "EQU_INSTALL_POSITION", "安装地点") ;
		excelHeadTJFConfig.put( "MEDUIM_TYPE", "介质") ;
		excelHeadTJFConfig.put( "MEARING_RANGE", "行程") ;
		excelHeadTJFConfig.put( "CV", "CV值") ;
		excelHeadTJFConfig.put( "FLA_SIZE", "法兰规格") ;
		excelHeadTJFConfig.put( "ACTION_MODLE", "作用方式") ;
		excelHeadTJFConfig.put( "HAVE_NOT", "有无手轮") ;
		excelHeadTJFConfig.put( "ACTUAL_MODEL", "执行机构型号") ;
		excelHeadTJFConfig.put( "GAS_SOURCE", "气源Mpa") ;
		excelHeadTJFConfig.put( "POSITIONER", "定位器") ;
		excelHeadTJFConfig.put( "VAVLE_TYPE", "电磁阀") ;
		excelHeadTJFConfig.put( "EQU_MANUFACTURER", "生产厂家") ;
		excelHeadTJFConfig.put( "SERIAL_NUM", "出厂编号") ;
		excelHeadTJFConfig.put( "REMARK1",	"备注") ;

		
		//液位计(含远程)具体表头配置
		Map<String,String> excelHeadYWJConfig = new LinkedHashMap<String,String>();
		excelHeadYWJConfig.put( "order", "序号") ;
		excelHeadYWJConfig.put( "WEL_NAME", "装置列名" );
		excelHeadYWJConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadYWJConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadYWJConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadYWJConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadYWJConfig.put( "EQU_MODEL", "规格型号") ;
		excelHeadYWJConfig.put( "DEEP_LENGTH", "中心距离") ;
		excelHeadYWJConfig.put( "EQU_INSTALL_POSITION", "安装地点") ;
		excelHeadYWJConfig.put( "MEDUIM_TYPE", "测量介质") ;
		excelHeadYWJConfig.put( "PROCE_LINK_TYPE", "过程连接尺寸") ;
		excelHeadYWJConfig.put( "EQU_MANUFACTURER", "生产厂家") ;
		excelHeadYWJConfig.put( "SERIAL_NUM", "出厂编号") ;
		excelHeadYWJConfig.put( "REMARK1", "备注") ;

			
		
		//流量计具体表头配置
		Map<String,String> excelHeadLLJConfig = new LinkedHashMap<String,String>();
		excelHeadLLJConfig.put( "order", "序号") ;
		excelHeadLLJConfig.put( "WEL_NAME", "装置列名" );
		excelHeadLLJConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadLLJConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadLLJConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadLLJConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadLLJConfig.put( "EQU_MODEL", "规格型号" );				
		excelHeadLLJConfig.put( "EQU_INSTALL_POSITION", "安装地点" );
		excelHeadLLJConfig.put( "MEDUIM_TYPE", "介质" );
		excelHeadLLJConfig.put( "ACTUAL", "流量(max)" );
		excelHeadLLJConfig.put( "FLUX", "流量(正常)" );
		excelHeadLLJConfig.put( "MEASURE_ACC", "精 度" );
		excelHeadLLJConfig.put( "MEARING_RANGE", "测量范围" );			
		excelHeadLLJConfig.put( "PROCE_LINK_TYPE", "过程安装方式" );				
		excelHeadLLJConfig.put( "SERIAL_NUM", "编号" );
		excelHeadLLJConfig.put( "EQU_MANUFACTURER", "生产厂家" );
		excelHeadLLJConfig.put( "EQU_PRODUC_DATE", "生产日期" );
		excelHeadLLJConfig.put( "REMARK1", "备注" );	
		
		
		//节流装置具体表头配置
		Map<String,String> excelHeadJLConfig = new LinkedHashMap<String,String>();
		excelHeadJLConfig.put( "order", "序号") ;
		excelHeadJLConfig.put( "WEL_NAME", "装置列名" );
		excelHeadJLConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadJLConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadJLConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadJLConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadJLConfig.put( "EQU_MODEL", "规格型号") ; 
		excelHeadJLConfig.put( "EQU_INSTALL_POSITION", "安装地点") ; 
		excelHeadJLConfig.put( "MEDUIM_TYPE", "测量介质") ; 
		excelHeadJLConfig.put( "MEARING_RANGE", "量程") ; 
		excelHeadJLConfig.put( "PRESSURE_RANGE", "压力MPa") ; 
		excelHeadJLConfig.put( "EQU_WORK_TEMP", "温度") ; 
		excelHeadJLConfig.put( "FLA_SIZE", "法兰规格") ; 
		excelHeadJLConfig.put( "EQU_MANUFACTURER", "生产厂家") ; 
		excelHeadJLConfig.put( "REMARK1", "备注") ; 
		
		//在线分析仪具体表头配置
		Map<String,String> excelHeadFXYConfig = new LinkedHashMap<String,String>();
		excelHeadFXYConfig.put( "order", "序号") ;
		excelHeadFXYConfig.put( "WEL_NAME", "装置列名" );
		excelHeadFXYConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadFXYConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadFXYConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadFXYConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadFXYConfig.put( "EQU_MODEL", "规格型号" );
		excelHeadFXYConfig.put( "EQU_INSTALL_POSITION", "安装地点" );
		excelHeadFXYConfig.put( "MEDUIM_TYPE", "测量介质" );
		excelHeadFXYConfig.put( "MEARING_RANGE", "量程" );
		excelHeadFXYConfig.put( "MEASURE_ACC", "精 度" );
		excelHeadFXYConfig.put( "MEASURE_PRIN", "测量原理" );
		excelHeadFXYConfig.put( "EQU_MANUFACTURER", "生产厂家" );
		excelHeadFXYConfig.put( "SERIAL_NUM", "出厂编号" );
		excelHeadFXYConfig.put( "REMARK1", "备注" );
		
		
		//振动温度探头具体表头配置
		Map<String,String> excelHeadZDTTConfig = new LinkedHashMap<String,String>();
		excelHeadZDTTConfig.put( "order", "序号") ;
		excelHeadZDTTConfig.put( "WEL_NAME", "装置列名" );
		excelHeadZDTTConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadZDTTConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadZDTTConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadZDTTConfig.put( "EQU_NAME", "设备名称") ;
		
		//DCS/SIS系统具体表头配置
		Map<String,String> excelHeadDSConfig = new LinkedHashMap<String,String>();
		excelHeadDSConfig.put( "order", "序号") ;
		excelHeadDSConfig.put( "WEL_NAME", "装置列名" );
		excelHeadDSConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadDSConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadDSConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadDSConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadDSConfig.put( "ELEC_MODEL", "控制器电源") ;
		excelHeadDSConfig.put( "EQU_COMMISSION_DATE", "控制器MD") ; 
		excelHeadDSConfig.put( "MEDUIM_TYPE", "控制器MQ") ; 
		excelHeadDSConfig.put( "EQU_WORK_TEMP", "AI16") ; 
		excelHeadDSConfig.put( "EQU_LASTPERIODIC_DATE", "AI18-R") ; 
		excelHeadDSConfig.put( "EQU_PERIODIC_CYCLE", "AI08-R") ; 
		excelHeadDSConfig.put( "EQU_PERIODIC_WARNDAYS", "DI32") ; 
		excelHeadDSConfig.put( "MEARING_RANGE", "DO32") ; 
		excelHeadDSConfig.put( "PRESSURE_RANGE", "串口卡(个)") ; 
		excelHeadDSConfig.put( "MANAGE_TYPE", "串口卡(对)") ; 
		excelHeadDSConfig.put( "SERIAL_NUM", "SIS卡(对)") ; 
		excelHeadDSConfig.put( "CHECK_CYCLE", "24VDC/20A(对)") ; 
		excelHeadDSConfig.put( "CHECK_TIME", "24VDC/40A(对)") ; 
		excelHeadDSConfig.put( "NEXT_CHECK_TIME", "12VDC/15A(对)") ; 
		excelHeadDSConfig.put( "EXPERY_TIME", "中继器") ; 
		excelHeadDSConfig.put( "INTER_SIZE", "AI8") ; 
		excelHeadDSConfig.put( "MEASURE_ACC", "AI浪涌") ; 
		excelHeadDSConfig.put( "DEEP_LENGTH", "DI浪涌") ; 
		excelHeadDSConfig.put( "ORDER_NUM", "继电器") ; 
		excelHeadDSConfig.put( "REMARK1", "备注") ; 
		
		//FGS系统具体表头配置
		Map<String,String> excelHeadFGSConfig = new LinkedHashMap<String,String>();
		excelHeadFGSConfig.put( "order", "序号") ;
		excelHeadFGSConfig.put( "WEL_NAME", "装置列名" );
		excelHeadFGSConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadFGSConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadFGSConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadFGSConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadFGSConfig.put( "MEDUIM_TYPE", "控制器8851") ; 
		excelHeadFGSConfig.put( "EQU_WORK_TEMP", "模拟量卡件8810") ; 
		excelHeadFGSConfig.put( "EQU_LASTPERIODIC_DATE", "数字量卡件8811") ; 
		excelHeadFGSConfig.put( "EQU_PERIODIC_CYCLE", "电源881312VDC/5A") ; 
		excelHeadFGSConfig.put( "EQU_PERIODIC_WARNDAYS", "电源RM240-24VDC/40A") ; 
		excelHeadFGSConfig.put( "MEARING_RANGE", "电源RM120-24VDC/20A") ; 
		excelHeadFGSConfig.put( "REMARK1", "备注") ; 
		
		//固定式报警仪具体表头配置
		Map<String,String> excelHeadBJConfig = new LinkedHashMap<String,String>();
		excelHeadBJConfig.put( "order", "序号") ;
		excelHeadBJConfig.put( "WEL_NAME", "装置列名" );
		excelHeadBJConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadBJConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadBJConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadBJConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadBJConfig.put( "MANAGE_TYPE", "器具类别") ; 
		excelHeadBJConfig.put( "EQU_MODEL", "规格型号") ; 
		excelHeadBJConfig.put( "EQU_INSTALL_POSITION", "安装使用地点") ; 
		excelHeadBJConfig.put( "ACTUAL", "用途") ; 
		excelHeadBJConfig.put( "ACTION_MODLE", "安装方式") ; 
		excelHeadBJConfig.put( "MEASURE_ACC", "精 度") ; 
		excelHeadBJConfig.put( "MEARING_RANGE", "测量范围") ; 
		excelHeadBJConfig.put( "ELEC", "供电") ; 
		excelHeadBJConfig.put( "CV", "输出信号(mA)") ; 
		excelHeadBJConfig.put( "ORDER_NUM", "报警值") ; 
		excelHeadBJConfig.put( "EQU_MANUFACTURER", "生产厂家") ; 
		excelHeadBJConfig.put( "SERIAL_NUM", "出厂编号") ; 
		excelHeadBJConfig.put( "NEXT_CHECK_TIME", "有效期") ; 
		excelHeadBJConfig.put( "REMARK1", "备注") ; 
		
		//其它具体表头配置
		Map<String,String> excelHeadQTConfig = new LinkedHashMap<String,String>();
		excelHeadQTConfig.put( "order", "序号") ;
		excelHeadQTConfig.put( "WEL_NAME", "装置列名" );
		excelHeadQTConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadQTConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadQTConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadQTConfig.put( "EQU_NAME", "设备名称") ;
		
		//P类具体表头配置
		Map<String,String> excelHeadPConfig = new LinkedHashMap<String,String>();
		excelHeadPConfig.put( "order", "序号") ;
		excelHeadPConfig.put( "WEL_NAME", "装置列名" );
		excelHeadPConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadPConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadPConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadPConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadPConfig.put( "EQU_INSTALL_POSITION", "安装位置" );
		excelHeadPConfig.put( "MANAGE_TYPE", "类别" );
		excelHeadPConfig.put( "EQU_MODEL", "规格型号" );
		excelHeadPConfig.put( "WEIGHT", "重量" );
		excelHeadPConfig.put( "FLUX", "扬程" );
		excelHeadPConfig.put( "COUNT", "排量" );
		excelHeadPConfig.put( "ELECTRIC_PRES", "电压" );
		excelHeadPConfig.put( "POWER_RATE", "功率" );
		excelHeadPConfig.put( "SPEED_RAT", "转速" );
		excelHeadPConfig.put( "MEDUIM_TYPE", "介质" );
		excelHeadPConfig.put( "CAPCITY", "泵壳" );
		excelHeadPConfig.put( "BEFORE_BEARING1", "叶轮" );
		excelHeadPConfig.put( "BEFORE_BEARING2", "主轴" );
		excelHeadPConfig.put( "EQU_MANUFACTURER", "制造单位" );
		excelHeadPConfig.put( "SERIAL_NUM", "出厂编号" );
		excelHeadPConfig.put( "EQU_COMMISSION_DATE", "投用年月" );
		excelHeadPConfig.put( "REMARK1", "备注" );
		
		//K类具体表头配置
		Map<String,String> excelHeadKConfig = new LinkedHashMap<String,String>();
		excelHeadKConfig.put( "order", "序号") ;
		excelHeadKConfig.put( "WEL_NAME", "装置列名" );
		excelHeadKConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadKConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadKConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadKConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadKConfig.put( "EQU_INSTALL_POSITION", "安装位置" );
		excelHeadKConfig.put( "MANAGE_TYPE", "类别" );
		excelHeadKConfig.put( "EQU_MODEL", "规格型号" );
		excelHeadKConfig.put( "WEIGHT", "重量" );
		excelHeadKConfig.put( "WIND_PRESSURE", "出口风压" );
		excelHeadKConfig.put( "COUNT", "排量" );
		excelHeadKConfig.put( "SPEED_RAT", "性能转速" );
		excelHeadKConfig.put( "ELECTRIC_PRES", "电压" );
		excelHeadKConfig.put( "POWER_RATE", "功率" );
		excelHeadKConfig.put( "SPINDLE_SPEED", "电机转速" );
		excelHeadKConfig.put( "MEDUIM_TYPE", "介质" );
		excelHeadKConfig.put( "EQU_MANUFACTURER", "制造单位" );
		excelHeadKConfig.put( "SERIAL_NUM", "出厂编号" );
		excelHeadKConfig.put( "EQU_COMMISSION_DATE", "投用年月" );
		excelHeadKConfig.put( "REMARK1", "备注" );

		//C类具体表头配置
		Map<String,String> excelHeadCConfig = new LinkedHashMap<String,String>();
		excelHeadCConfig.put( "order", "序号") ;
		excelHeadCConfig.put( "WEL_NAME", "装置列名" );
		excelHeadCConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadCConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadCConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadCConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadCConfig.put( "EQU_MODEL", "规格型号(φ×δ×h)" );	
		excelHeadCConfig.put( "EQU_COMMISSION_DATE", "投用年月 " );		
		excelHeadCConfig.put( "EQU_INSTALL_POSITION", "安装位置" );		
		excelHeadCConfig.put( "EQU_MANUFACTURER", "制造单位" );		
		excelHeadCConfig.put( "EQU_POSITION_NUM", "设备位号" );		
		excelHeadCConfig.put( "MEDUIM_TYPE", "介质" );		
		excelHeadCConfig.put( "MEARING_RANGE", "容积(m3)" );		
		excelHeadCConfig.put( "MANAGE_TYPE", "类别" );		
		excelHeadCConfig.put( "SERIAL_NUM", "出厂编号" );		
		excelHeadCConfig.put( "MEASURE_ACC", "腐蚀裕度(mm)" );		
		excelHeadCConfig.put( "ACTION_MODLE", "铭牌位号" );		
		excelHeadCConfig.put( "COUNT", "塔盘层数" );		
		excelHeadCConfig.put( "WEIGHT", "重量(Kg)" );		
		excelHeadCConfig.put( "MATERIAL", "主体材质" );		
		excelHeadCConfig.put( "DESIGN_TUBE_TEMP", "设计条件温度(℃)" );		
		excelHeadCConfig.put( "DESIGN_TUBE_PRES", "设计条件压力(MPa)" );		
		excelHeadCConfig.put( "OPTION_SHELL_PRESS", "操作条件压力(MPa)" );		
		excelHeadCConfig.put( "OPTION_SHELL_IN_TEMP", "操作条件温度(℃)" );		
		excelHeadCConfig.put( "REMARK1", "备注" );			
		
		//D类具体表头配置
		Map<String,String> excelHeadDConfig = new LinkedHashMap<String,String>();
		excelHeadDConfig.put( "order", "序号") ;
		excelHeadDConfig.put( "WEL_NAME", "装置列名" );
		excelHeadDConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadDConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadDConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadDConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadDConfig.put( "EQU_MODEL", "规格型号(φ×δ×h)" );	
		excelHeadDConfig.put( "EQU_COMMISSION_DATE", "投用年月 " );	
		excelHeadDConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadDConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadDConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadDConfig.put( "MEDUIM_TYPE", "介质" );	
		excelHeadDConfig.put( "MEARING_RANGE", "容积(m3)" );	
		excelHeadDConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadDConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadDConfig.put( "MEASURE_ACC", "腐蚀裕度(mm)" );	
		excelHeadDConfig.put( "ACTION_MODLE", "铭牌位号" );	
		excelHeadDConfig.put( "WEIGHT", "重量(Kg)" );	
		excelHeadDConfig.put( "MATERIAL", "主体材质" );	
		excelHeadDConfig.put( "DESIGN_TUBE_TEMP", "设计条件温度(℃)" );	
		excelHeadDConfig.put( "DESIGN_TUBE_PRES", "设计条件压力(MPa)" );	
		excelHeadDConfig.put( "OPTION_SHELL_PRESS", "操作条件压力(MPa)" );	
		excelHeadDConfig.put( "OPTION_SHELL_IN_TEMP", "操作条件温度(℃)" );	
		excelHeadDConfig.put( "REMARK1", "备注" );	
			
		//E类具体表头配置
		Map<String,String> excelHeadEConfig = new LinkedHashMap<String,String>();
		excelHeadEConfig.put( "order", "序号") ;
		excelHeadEConfig.put( "WEL_NAME", "装置列名" );
		excelHeadEConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadEConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadEConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadEConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadEConfig.put( "EQU_MODEL", "型式" );	
		excelHeadEConfig.put( "EQU_COMMISSION_DATE", "投用年月" );	
		excelHeadEConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadEConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadEConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadEConfig.put( "MEARING_RANGE", "换热面积(m2)" );	
		excelHeadEConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadEConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadEConfig.put( "WEIGHT", "重量(Kg)" );
		excelHeadEConfig.put( "DESIGN_SHELL_PRES", "设计条件管程压力(MPa)" );	
		excelHeadEConfig.put( "DESIGN_SHELL_TEMP", "设计条件管程温度(℃)" );	
		excelHeadEConfig.put( "DESIGN_TUBE_TEMP", "设计条件壳程温度(℃)" );	
		excelHeadEConfig.put( "DESIGN_TUBE_PRES", "设计条件壳程压力(MPa)" );	
		excelHeadEConfig.put( "OPTION_SHELL_PRESS", "操作条件管程压力(MPa)" );	
		excelHeadEConfig.put( "OPTION_SHELL_IN_TEMP", "操作条件管程进口温度(℃)" );	
		excelHeadEConfig.put( "OPTION_SHELL_OUT_TEMP", "操作条件管程出口温度(℃)" );	
		excelHeadEConfig.put( "OPTION_SHELL_MEDUIM", "操作条件管程介质" );	
		excelHeadEConfig.put( "OPTION_TUBE_IN_TEMP", "操作条件进口温度(℃)" );	
		excelHeadEConfig.put( "OPTION_TUBE_OUT_TEMP", "操作条件管程出口温度(℃)" );	
		excelHeadEConfig.put( "OPTION_TUBE_MEDUIM", "操作条件管程介质" );	
		excelHeadEConfig.put( "SHELL_MATERIAL", "主体材质壳体" );	
		excelHeadEConfig.put( "TUBE_MATERIAL", "主体材质列管" );	
		excelHeadEConfig.put( "REMARK1", "备注" );	
		
		//F类具体表头配置
		Map<String,String> excelHeadFConfig = new LinkedHashMap<String,String>();
		excelHeadFConfig.put( "order", "序号") ;
		excelHeadFConfig.put( "WEL_NAME", "装置列名" );
		excelHeadFConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadFConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadFConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadFConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadFConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadFConfig.put( "EQU_COMMISSION_DATE", "投用年月" );	
		excelHeadFConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadFConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadFConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadFConfig.put( "MEDUIM_TYPE", "介质" );	
		excelHeadFConfig.put( "MEARING_RANGE", "容积(m3)" );	
		excelHeadFConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadFConfig.put( "MEASURE_ACC", "腐蚀裕度(mm)" );
		excelHeadFConfig.put( "ACTION_MODLE", "铭牌位号" );	
		excelHeadFConfig.put( "WEIGHT", "重量(Kg)" );	
		excelHeadFConfig.put( "MATERIAL", "主体材质" );	
		excelHeadFConfig.put( "DESIGN_TUBE_TEMP", "设计条件温度(℃)" );	
		excelHeadFConfig.put( "DESIGN_TUBE_PRES", "设计条件压力(MPa)" );	
		excelHeadFConfig.put( "OPTION_SHELL_PRESS", "操作条件压力(MPa)" );	
		excelHeadFConfig.put( "OPTION_SHELL_IN_TEMP", "操作条件温度(℃)" );	
		excelHeadFConfig.put( "REMARK1", "备注" );	
		
		//H类具体表头配置
		Map<String,String> excelHeadHConfig = new LinkedHashMap<String,String>();
		excelHeadHConfig.put( "order", "序号") ;
		excelHeadHConfig.put( "WEL_NAME", "装置列名" );
		excelHeadHConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadHConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadHConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadHConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadHConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadHConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadHConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadHConfig.put( "MEDUIM_TYPE", "介质" );	
		excelHeadHConfig.put( "MEARING_RANGE", "容积(m3)" );	
		excelHeadHConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadHConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadHConfig.put( "MEASURE_ACC", "腐蚀裕度(mm)" );	
		excelHeadHConfig.put( "ACTION_MODLE", "铭牌位号" );	
		excelHeadHConfig.put( "WEIGHT", "重量(Kg)" );	
		excelHeadHConfig.put( "MATERIAL", "主体材质" );	
		excelHeadHConfig.put( "DESIGN_TUBE_TEMP", "设计条件温度(℃)" );	
		excelHeadHConfig.put( "DESIGN_TUBE_PRES", "设计条件压力(MPa)" );	
		excelHeadHConfig.put( "OPTION_SHELL_PRESS", "操作条件压力(MPa)" );	
		excelHeadHConfig.put( "OPTION_SHELL_IN_TEMP", "操作条件温度(℃)" );	
		excelHeadHConfig.put( "REMARK1", "备注" );	
		
		//R类具体表头配置
		Map<String,String> excelHeadRConfig = new LinkedHashMap<String,String>();
		excelHeadRConfig.put( "order", "序号") ;
		excelHeadRConfig.put( "WEL_NAME", "装置列名" );
		excelHeadRConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadRConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadRConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadRConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadRConfig.put( "EQU_MODEL", "规格型号(φ×δ×h)" );	
		excelHeadRConfig.put( "EQU_COMMISSION_DATE", "投用年月 " );	
		excelHeadRConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadRConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadRConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadRConfig.put( "MEARING_RANGE", "容积(m3)" );	
		excelHeadRConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadRConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadRConfig.put( "MEASURE_ACC", "腐蚀裕度(mm)" );	
		excelHeadRConfig.put( "WEIGHT", "重量(Kg)" );	
		excelHeadRConfig.put( "MATERIAL", "主体材质" );	
		excelHeadRConfig.put( "DESIGN_TUBE_TEMP", "设计条件温度(℃)" );	
		excelHeadRConfig.put( "DESIGN_TUBE_PRES", "设计条件压力(MPa)" );	
		excelHeadRConfig.put( "OPTION_SHELL_PRESS", "操作条件压力(MPa)" );	
		excelHeadRConfig.put( "OPTION_SHELL_IN_TEMP", "操作条件温度(℃)" );	
		excelHeadRConfig.put( "REMARK1", "备注" );	
		
		//机修类具体表头配置
		Map<String,String> excelHeadJXConfig = new LinkedHashMap<String,String>();
		excelHeadJXConfig.put( "order", "序号") ;
		excelHeadJXConfig.put( "WEL_NAME", "装置列名" );
		excelHeadJXConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadJXConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadJXConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadJXConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadJXConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadJXConfig.put( "EQU_COMMISSION_DATE", "投用年月" );	
		excelHeadJXConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadJXConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadJXConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadJXConfig.put( "COUNT", "数量" );	
		excelHeadJXConfig.put( "REMARK1", "备注" );	
		
		//车辆类具体表头配置
		Map<String,String> excelHeadCLConfig = new LinkedHashMap<String,String>();
		excelHeadCLConfig.put( "order", "序号") ;
		excelHeadCLConfig.put( "WEL_NAME", "装置列名" );
		excelHeadCLConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadCLConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadCLConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadCLConfig.put( "EQU_NAME", "车辆名称") ;
		excelHeadCLConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadCLConfig.put( "EQU_COMMISSION_DATE", "投用年月" );	
		excelHeadCLConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadCLConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadCLConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadCLConfig.put( "CAPCITY", "能力" );	
		excelHeadCLConfig.put( "ENGINE_NUMBER", "发动机号" );	
		excelHeadCLConfig.put( "LICENSE_NUMBER", "车牌号" );	
		excelHeadCLConfig.put( "CHASSIS_NUMBER", "底盘号" );	
		excelHeadCLConfig.put( "ENERGY_CONSUMPTION", "能耗(L)" );	
		excelHeadCLConfig.put( "ENERGY_CONSUMPTION_CAT", "耗能种类" );	
		excelHeadCLConfig.put( "REMARK1", "备注" );	
		
		//其他类具体表头配置
		Map<String,String> excelHeadQConfig = new LinkedHashMap<String,String>();
		excelHeadQConfig.put( "order", "序号") ;
		excelHeadQConfig.put( "WEL_NAME", "装置列名" );
		excelHeadQConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadQConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadQConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadQConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadQConfig.put( "EQU_COMMISSION_DATE", "投用年月" );	
		excelHeadQConfig.put( "EQU_MANUFACTURER", "制造单位" );	
		excelHeadQConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadQConfig.put( "MANAGE_TYPE", "类别" );	
		excelHeadQConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadQConfig.put( "COUNT", "数量" );	
		excelHeadQConfig.put( "HEIGHT_ELECTRIC_TENSION", "主要技术参数" );	
		excelHeadQConfig.put( "REMARK1", "备注" );	
		
		//A类分析仪器
		Map<String,String> excelHeadAConfig = new LinkedHashMap<String,String>();
		excelHeadAConfig.put( "order", "序号") ;
		excelHeadAConfig.put( "WEL_NAME", "装置列名" );
		excelHeadAConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadAConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadAConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadAConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadAConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadAConfig.put( "EQU_INSTALL_POSITION", "安装地点" );	
		excelHeadAConfig.put( "EQU_MANUFACTURER", "生产厂家" );	
		excelHeadAConfig.put( "EQU_POSITION_NUM", "位号" );	
		excelHeadAConfig.put( "MEDUIM_TYPE", "介质" );	
		excelHeadAConfig.put( "MEARING_RANGE", "测量范围" );	
		excelHeadAConfig.put( "MANAGE_TYPE", "计量器具管理类别" );	
		excelHeadAConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadAConfig.put( "CHECK_CYCLE", "检定周期(月)" );	
		excelHeadAConfig.put( "CHECK_TIME", "检定日期" );	
		excelHeadAConfig.put( "NEXT_CHECK_TIME", "下次检定日期" );	
		excelHeadAConfig.put( "MEASURE_ACC", "准确度等级" );	
		excelHeadAConfig.put( "ACTION_MODLE", "检定单位" );	
		excelHeadAConfig.put( "REMARK1", "备注" );	
			
		//EPS电源系统
		Map<String,String> excelHeadEPSConfig = new LinkedHashMap<String,String>();
		excelHeadEPSConfig.put( "order", "序号") ;
		excelHeadEPSConfig.put( "WEL_NAME", "装置列名" );
		excelHeadEPSConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadEPSConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadEPSConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadEPSConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadEPSConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadEPSConfig.put( "EQU_PRODUC_DATE", "生产日期" );	
		excelHeadEPSConfig.put( "EQU_COMMISSION_DATE", "投运日期" );	
		excelHeadEPSConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadEPSConfig.put( "EQU_MANUFACTURER", "生产厂家" );	
		excelHeadEPSConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadEPSConfig.put( "MEDUIM_TYPE", "工作性质" );	
		excelHeadEPSConfig.put( "EQU_WORK_TEMP", "蓄电池型号" );	
		excelHeadEPSConfig.put( "EQU_LASTPERIODIC_DATE", "蓄电池品牌" );	
		excelHeadEPSConfig.put( "EQU_PERIODIC_CYCLE", "蓄电池容量" );	
		excelHeadEPSConfig.put( "MEARING_RANGE", "防护等级" );	
		excelHeadEPSConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadEPSConfig.put( "COUNT", "蓄电池数量" );	
		excelHeadEPSConfig.put( "POWER_RATE", "功率" );	
		excelHeadEPSConfig.put( "ELECTRIC_PRES", "输入电压" );	
		excelHeadEPSConfig.put( "ELECTRIC_TENSION", "输出电压" );	
		excelHeadEPSConfig.put( "FREQUENCY", "频率" );	
		excelHeadEPSConfig.put( "REMARK1", "备注" );	
		
		//UPS电源系统具体表头配置
		Map<String,String> excelHeadUPSConfig = new LinkedHashMap<String,String>();
		excelHeadUPSConfig.put( "order", "序号") ;
		excelHeadUPSConfig.put( "WEL_NAME", "装置列名" );
		excelHeadUPSConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadUPSConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadUPSConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadUPSConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadUPSConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadUPSConfig.put( "EQU_PRODUC_DATE", "生产日期" );	
		excelHeadUPSConfig.put( "EQU_COMMISSION_DATE", "投运日期" );	
		excelHeadUPSConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadUPSConfig.put( "EQU_MANUFACTURER", "生产厂家" );	
		excelHeadUPSConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadUPSConfig.put( "MEDUIM_TYPE", "工作性质" );	
		excelHeadUPSConfig.put( "EQU_WORK_TEMP", "蓄电池型号" );	
		excelHeadUPSConfig.put( "EQU_LASTPERIODIC_DATE", "蓄电池品牌" );	
		excelHeadUPSConfig.put( "EQU_PERIODIC_CYCLE", "蓄电池容量" );	
		excelHeadUPSConfig.put( "MEARING_RANGE", "防护等级" );	
		excelHeadUPSConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadUPSConfig.put( "COUNT", "蓄电池数量" );	
		excelHeadUPSConfig.put( "ELECTRIC_PRES", "输入电压" );	
		excelHeadUPSConfig.put( "ELECTRIC_TENSION", "输出电压" );	
		excelHeadUPSConfig.put( "FREQUENCY", "频率" );	
		excelHeadUPSConfig.put( "REMARK1", "备注" );	
		
		//低压配电柜具体表头配置
		Map<String,String> excelHeadDPDConfig = new LinkedHashMap<String,String>();
		excelHeadDPDConfig.put( "order", "序号") ;
		excelHeadDPDConfig.put( "WEL_NAME", "装置列名" );
		excelHeadDPDConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadDPDConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadDPDConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadDPDConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadDPDConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadDPDConfig.put( "EQU_PRODUC_DATE", "生产日期" );	
		excelHeadDPDConfig.put( "EQU_COMMISSION_DATE", "投运日期" );	
		excelHeadDPDConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadDPDConfig.put( "EQU_MANUFACTURER", "生产厂家" );	
		excelHeadDPDConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadDPDConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadDPDConfig.put( "ELECTRIC_PRES", "额定电压" );	
		excelHeadDPDConfig.put( "ELECTRIC_TENSION", "额定电流" );	
		excelHeadDPDConfig.put( "REMARK1", "备注" );	
			
		//电动机具体表头配置  电动机
		Map<String,String> excelHeadDDJConfig = new LinkedHashMap<String,String>();
		excelHeadDDJConfig.put( "order", "序号") ;
		excelHeadDDJConfig.put( "WEL_NAME", "装置列名" );
		excelHeadDDJConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadDDJConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadDDJConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadDDJConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadDDJConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadDDJConfig.put( "EQU_PRODUC_DATE", "生产日期" );	
		excelHeadDDJConfig.put( "EQU_COMMISSION_DATE", "投运日期" );	
		excelHeadDDJConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadDDJConfig.put( "EQU_MANUFACTURER", "生产厂家" );	
		excelHeadDDJConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadDDJConfig.put( "MEDUIM_TYPE", "工作性质" );	
		excelHeadDDJConfig.put( "EQU_WORK_TEMP", "蓄电池型号" );	
		excelHeadDDJConfig.put( "EQU_LASTPERIODIC_DATE", "蓄电池品牌" );	
		excelHeadDDJConfig.put( "EQU_PERIODIC_CYCLE", "蓄电池容量" );	
		excelHeadDDJConfig.put( "MEARING_RANGE", "防护等级" );	
		excelHeadDDJConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadDDJConfig.put( "COUNT", "蓄电池数量" );	
		excelHeadDDJConfig.put( "ELECTRIC_PRES", "输入电压" );	
		excelHeadDDJConfig.put( "ELECTRIC_TENSION", "输出电压" );	
		excelHeadDDJConfig.put( "FREQUENCY", "频率" );	
		excelHeadDDJConfig.put( "REMARK1", "备注" );	
			
		//干式变压器具体表头配置
		Map<String,String> excelHeadGBYQConfig = new LinkedHashMap<String,String>();
		excelHeadGBYQConfig.put( "order", "序号") ;
		excelHeadGBYQConfig.put( "WEL_NAME", "装置列名" );
		excelHeadGBYQConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadGBYQConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadGBYQConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadGBYQConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadGBYQConfig.put( "EQU_MODEL","规格型号" );	
		excelHeadGBYQConfig.put( "EQU_PRODUC_DATE","出厂时间" );	
		excelHeadGBYQConfig.put( "EQU_COMMISSION_DATE","投运日期" );	
		excelHeadGBYQConfig.put( "EQU_INSTALL_POSITION","安装位置" );	
		excelHeadGBYQConfig.put( "EQU_MANUFACTURER","生产厂家" );	
		excelHeadGBYQConfig.put( "EQU_POSITION_NUM","设备位号" );	
		excelHeadGBYQConfig.put( "EQU_PERIODIC_CYCLE","容量" );	
		excelHeadGBYQConfig.put( "MEARING_RANGE","绝缘等级" );	
		excelHeadGBYQConfig.put( "MANAGE_TYPE","接线组别" );	
		excelHeadGBYQConfig.put( "SERIAL_NUM","出厂编号" );	
		excelHeadGBYQConfig.put( "COUNT","相数" );	
		excelHeadGBYQConfig.put( "ELECTRIC_PRES","高压侧电压" );	
		excelHeadGBYQConfig.put( "ELECTRIC_TENSION","高压侧电流" );	
		excelHeadGBYQConfig.put( "FREQUENCY","低压侧电压" );	
		excelHeadGBYQConfig.put( "BRAND","低压侧电流" );	
		excelHeadGBYQConfig.put( "WEIGHT","重量" );	
		excelHeadGBYQConfig.put( "REMARK1","备注" );	
		
		//高压配电柜具体表头配置
		Map<String,String> excelHeadGPDGConfig = new LinkedHashMap<String,String>();
		excelHeadGPDGConfig.put( "order", "序号") ;
		excelHeadGPDGConfig.put( "WEL_NAME", "装置列名" );
		excelHeadGPDGConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadGPDGConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadGPDGConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadGPDGConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadGPDGConfig.put( "EQU_MODEL","规格型号" );	
		excelHeadGPDGConfig.put( "EQU_PRODUC_DATE","生产日期" );	
		excelHeadGPDGConfig.put( "EQU_COMMISSION_DATE","投运日期" );	
		excelHeadGPDGConfig.put( "EQU_INSTALL_POSITION","安装位置" );	
		excelHeadGPDGConfig.put( "EQU_MANUFACTURER","生产厂家" );	
		excelHeadGPDGConfig.put( "EQU_POSITION_NUM","设备位号" );	
		excelHeadGPDGConfig.put( "MEARING_RANGE","防护等级" );	
		excelHeadGPDGConfig.put( "SERIAL_NUM","出厂编号" );	
		excelHeadGPDGConfig.put( "ACTION_MODLE","对应设备" );	
		excelHeadGPDGConfig.put( "ELECTRIC_PRES","额定短时工频耐受电压" );	
		excelHeadGPDGConfig.put( "ELECTRIC_TENSION","额定雷电冲击耐受电压" );	
		excelHeadGPDGConfig.put( "FREQUENCY","额定短时耐受电流" );	
		excelHeadGPDGConfig.put( "BRAND","额定峰值耐受电流" );	
		excelHeadGPDGConfig.put( "WEIGHT","相数" );	
		excelHeadGPDGConfig.put( "REMARK1","备注" );	
		
		//现场配电箱具体表头配置
		Map<String,String> excelHeadXPDXConfig = new LinkedHashMap<String,String>();
		
		excelHeadXPDXConfig.put( "order", "序号") ;
		excelHeadXPDXConfig.put( "WEL_NAME", "装置列名" );
		excelHeadXPDXConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadXPDXConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadXPDXConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadXPDXConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadXPDXConfig.put( "EQU_MODEL","规格型号" );	
		excelHeadXPDXConfig.put( "EQU_PRODUC_DATE","生产日期" );	
		excelHeadXPDXConfig.put( "EQU_COMMISSION_DATE","投运日期" );	
		excelHeadXPDXConfig.put( "EQU_INSTALL_POSITION","安装位置" );	
		excelHeadXPDXConfig.put( "EQU_MANUFACTURER","生产厂家" );	
		excelHeadXPDXConfig.put( "EQU_POSITION_NUM","设备位号" );	
		excelHeadXPDXConfig.put( "MEDUIM_TYPE","设备用途" );	
		excelHeadXPDXConfig.put( "MEARING_RANGE","防爆区域等级" );	
		excelHeadXPDXConfig.put( "SERIAL_NUM","出厂编号" );	
		excelHeadXPDXConfig.put( "HEIGHT_ELECTRIC_TENSION","主要运行参数" );	
		excelHeadXPDXConfig.put( "WEIGHT","防爆合格证号" );	
		excelHeadXPDXConfig.put( "MATERIAL","防爆标志" );	
		excelHeadXPDXConfig.put( "REMARK1","备注" );	
		
		//直流电源系统具体表头配置
		Map<String,String> excelHeadZLDYConfig = new LinkedHashMap<String,String>();
		excelHeadZLDYConfig.put( "order", "序号") ;
		excelHeadZLDYConfig.put( "WEL_NAME", "装置列名" );
		excelHeadZLDYConfig.put( "WEL_UNIT", "装置单元" );		
		excelHeadZLDYConfig.put( "EQU_POSITION_NUM", "设备位号") ;
		excelHeadZLDYConfig.put( "EQU_MEMO_ONE", "设备类别") ;
		excelHeadZLDYConfig.put( "EQU_NAME", "设备名称") ;
		excelHeadZLDYConfig.put( "EQU_MODEL", "规格型号" );	
		excelHeadZLDYConfig.put( "EQU_PRODUC_DATE", "生产日期" );	
		excelHeadZLDYConfig.put( "EQU_COMMISSION_DATE", "投运日期" );	
		excelHeadZLDYConfig.put( "EQU_INSTALL_POSITION", "安装位置" );	
		excelHeadZLDYConfig.put( "EQU_MANUFACTURER", "生产厂家" );	
		excelHeadZLDYConfig.put( "EQU_POSITION_NUM", "设备位号" );	
		excelHeadZLDYConfig.put( "EQU_WORK_TEMP", "蓄电池型号" );	
		excelHeadZLDYConfig.put( "EQU_LASTPERIODIC_DATE", "品牌" );	
		excelHeadZLDYConfig.put( "EQU_PERIODIC_CYCLE", "蓄电池容量" );	
		excelHeadZLDYConfig.put( "MEARING_RANGE", "防护等级" );	
		excelHeadZLDYConfig.put( "SERIAL_NUM", "出厂编号" );	
		excelHeadZLDYConfig.put( "COUNT", "蓄电池数量" );	
		excelHeadZLDYConfig.put( "ELECTRIC_PRES", "额定交流电压" );	
		excelHeadZLDYConfig.put( "ELECTRIC_TENSION", "额定电流" );	
		excelHeadZLDYConfig.put( "FREQUENCY", "频率" );	
		excelHeadZLDYConfig.put( "BRAND", "额定直流电压" );	
		excelHeadZLDYConfig.put( "REMARK1", "备注" );	
		
		/*
		 * 接受请求参数
		 */
		String moduleType = req.getParameter( "moduleType" );
		String EQU_MEMO_ONE = req.getParameter( "EQU_MEMO_ONE" );
		
		//判断		
		if( EQU_MEMO_ONE == null ) {
			res.getWriter().write( "请求失败,设备类型为空" );
		}
		if( moduleType == null ) {
			res.getWriter().write( "请求失败,请求类型为空" );
		}		
		//具体表头配置增加……
		//通用具体表头配置
		if( "2".equals( moduleType.trim() ) ) {	
			excelHeadDetailConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadYCBConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadWDJConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadWBConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadQDFConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadTJFConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadYWJConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadLLJConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadJLConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadFXYConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadZDTTConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadDSConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadFGSConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadBJConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadQTConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadCConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadDConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadEConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadFConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadHConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadRConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadKConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadPConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadJXConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadCLConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadQTConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadAConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadEPSConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadUPSConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadDPDConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadDDJConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadGBYQConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadGPDGConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadXPDXConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );
			excelHeadZLDYConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );			
		}
		
		excelHeadConfig.put( "压力表", excelHeadDetailConfig );	
		excelHeadConfig.put( "压力差压变送器", excelHeadYCBConfig );	
		excelHeadConfig.put( "温度计", excelHeadWDJConfig );	
		excelHeadConfig.put( "温度变送器", excelHeadWBConfig );			
		excelHeadConfig.put( "气动切断阀",  excelHeadQDFConfig );
		excelHeadConfig.put( "气动调节阀", excelHeadTJFConfig );
		excelHeadConfig.put( "液位计(含远程)", excelHeadYWJConfig );
		excelHeadConfig.put( "流量", excelHeadLLJConfig );
		excelHeadConfig.put( "节流装置", excelHeadJLConfig );
		excelHeadConfig.put( "在线分析仪", excelHeadFXYConfig );
		excelHeadConfig.put( "振动温度探头", excelHeadZDTTConfig );
		excelHeadConfig.put( "DCS SIS系统", excelHeadDSConfig );
		excelHeadConfig.put( "FGS系统",  excelHeadFGSConfig );
		excelHeadConfig.put( "固定式报警仪",  excelHeadBJConfig );
		excelHeadConfig.put( "其他",  excelHeadQTConfig );
		excelHeadConfig.put( "C类设备",  excelHeadCConfig );
		excelHeadConfig.put( "D类设备",  excelHeadDConfig );
		excelHeadConfig.put( "E类设备",  excelHeadEConfig );
		excelHeadConfig.put( "F类设备",  excelHeadFConfig );
		excelHeadConfig.put( "H类设备",  excelHeadHConfig );
		excelHeadConfig.put( "R类设备",  excelHeadRConfig );
		excelHeadConfig.put( "K类设备",  excelHeadKConfig );
		excelHeadConfig.put( "P类设备",  excelHeadPConfig );
		excelHeadConfig.put( "机修类",  excelHeadJXConfig );
		excelHeadConfig.put( "车辆类",  excelHeadCLConfig );
		excelHeadConfig.put( "其他",  excelHeadQTConfig );
		excelHeadConfig.put( "A类分析仪器",  excelHeadAConfig );
		excelHeadConfig.put( "EPS电源系统",  excelHeadEPSConfig );
		excelHeadConfig.put( "UPS电源系统",  excelHeadUPSConfig );
		excelHeadConfig.put( "低压配电柜",  excelHeadDPDConfig );
		excelHeadConfig.put( "电动机",  excelHeadDDJConfig );
		excelHeadConfig.put( "干式变压器",  excelHeadGBYQConfig );
		excelHeadConfig.put( "高压配电柜",  excelHeadGPDGConfig );
		excelHeadConfig.put( "现场配电箱",  excelHeadXPDXConfig );
		excelHeadConfig.put( "直流电源系统",  excelHeadZLDYConfig );

										
		//动态生成表格标题
		String currentExcelTitle = excelTitleConfig.get( EQU_MEMO_ONE.trim() );
		System.out.println("--------Excel表格标题--------" + currentExcelTitle );
		
		//动态生成表格表头
		Map<String,String> currentEquipmentMap = excelHeadConfig.get( EQU_MEMO_ONE.trim() );
		String[] excelHeader = new String[currentEquipmentMap.size()];
		int aFor = 0;
		for ( Map.Entry<String,String> entry : currentEquipmentMap.entrySet()) {			
			excelHeader[aFor] = entry.getValue();
			aFor ++;
		}
		System.out.println("--------Excel表格表头--------" + excelHeader.toString() );
		
		//动态生成数据Excel内容
		JSONArray excelJsonArr = new JSONArray();		
		
		if( !"2".equals( moduleType.trim() ) ) {
			System.out.println( "--------导出Excel数据--------" );
			//通用
			String WEL_NAME = req.getParameter("WEL_NAME");
			String WEL_UNIT = req.getParameter("WEL_UNIT");
			String EQU_POSITION_NUM = req.getParameter("EQU_POSITION_NUM");
			String EQU_NAME = req.getParameter("EQU_NAME");
			
			//设备大类
			String SECONDCLASS_EQUIPMENT = req.getParameter("SECONDCLASS_EQUIPMENT");
	
		
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
			

			String MEDUIM_TYPE = req.getParameter( "MEDUIM_TYPE" );
		
			//判断查询方式
			String condition = "";
			if( SECONDCLASS_EQUIPMENT != null && !"".equals( SECONDCLASS_EQUIPMENT.trim() )) {
				if( "全部设备".equals( SECONDCLASS_EQUIPMENT.trim() )) {
					condition = createTotalCondition(  EQU_MEMO_ONE, 
							EQU_POSITION_NUM, EQU_NAME );
				}else {
					condition = createSecondEquipmentCondition( SECONDCLASS_EQUIPMENT,
							WEL_NAME,WEL_UNIT,EQU_MEMO_ONE, EQU_POSITION_NUM,
							EQU_NAME );
				}				
			}else{
				condition = createThirdCondition( WEL_NAME,WEL_UNIT,EQU_MEMO_ONE, 
						EQU_POSITION_NUM, EQU_NAME,//通用
						MANAGE_TYPE, EQU_MODEL, MEARING_RANGE, MEASURE_ACC,
						EQU_INSTALL_POSITION, EQU_MANUFACTURER, SERIAL_NUM,
						CHECK_CYCLE, CHECK_TIME, NEXT_CHECK_TIME, REMARK1,//压力表
						MEDUIM_TYPE//压力差压变送器
				);
			}
			System.out.println("导出excel----------------condition:" + condition);
			excelJsonArr = getEquList( condition );
		
			excelContents = new String[excelJsonArr.length()][currentEquipmentMap.size()];
			try{
				int bFor = 0;
				for (int i = 0; i < excelJsonArr.length(); i++) {
					bFor = 1;
					JSONObject json = excelJsonArr.getJSONObject(i);
					excelContents[i][0] = ( i + 1 ) + "" ;
					for ( Map.Entry<String,String> entry : currentEquipmentMap.entrySet()) {
						if( "order".equals( entry.getKey().trim() )) {
							continue;
						}
						
						excelContents[i][bFor] = json.getString( entry.getKey().trim() );
						bFor ++;
					}
				}
			}catch (JSONException e){
				e.printStackTrace();
				excelContents = null;
			}
			System.out.println("--------Excel表格数据内容--------" + excelContents.toString() );
			
			/*
			 * 生成excel
			 */
			exportExcelPath( excelHeader, excelContents, currentExcelTitle, out );
		}else {
			System.out.println( "--------导出空白模板--------" );
			excelContents = new String[1][currentEquipmentMap.size()];
			int bFor = 1;
			excelContents[0][0] = "1";
			for ( Map.Entry<String,String> entry : currentEquipmentMap.entrySet()) {
				if( "EQUIPMENT_ATTACH_URL".equals( entry.getKey().trim() )) {
					excelContents[0][bFor] = 
							equipAttachLocationUrlConfig.get( EQU_MEMO_ONE.trim() );
					continue;
				}
				if( "order".equals( entry.getKey().trim() )) {
					continue;
				}
				excelContents[0][bFor] = " ";
				bFor ++;
			}			
			/*
			 * 生成excel
			 */
			exportExcelPath( excelHeader, excelContents, currentExcelTitle, out );
		}
	}

	
	    /**
	    * @Title: exportExcelPath
	    * @Description: 生成excel
	    * @param @param excelHeader
	    * @param @param excelContent
	    * @param @param currentExcelTitle
	    * @param @param out    参数
	    * @return void    返回类型
	    * @throws
	    */	    
	public static void exportExcelPath(String excelHeader[], String excelContent[][], 
			String currentExcelTitle, OutputStream out) {
		try{
			HSSFWorkbook excel = Im_ExportExcel.exportExcel(
					excelHeader, excelContent, currentExcelTitle);
			excel.write( out );
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
    /**
	    * @Title: createTotalCondition
	    * @Description: 查询所有设备
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	    */	    
	private String createTotalCondition( 
			String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME ) {	
		String condition = "";
//		if( isEmpty( WEL_NAME ) ) {
//			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
//		}
//		if( isEmpty( WEL_UNIT ) ) {
//			condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
//		}
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
			String WEL_NAME,String WEL_UNIT,String EQU_MEMO_ONE, 
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
	    
	private String createThirdCondition( 
			String WEL_NAME, String WEL_UNIT,String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME,//通用
			String MANAGE_TYPE, String EQU_MODEL, String MEARING_RANGE, 
			String MEASURE_ACC,String EQU_INSTALL_POSITION, String EQU_MANUFACTURER, 
			String SERIAL_NUM,String CHECK_CYCLE, String CHECK_TIME, 
			String NEXT_CHECK_TIME, String REMARK1,
			String MEDUIM_TYPE){
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
	private JSONArray getEquList( String condition ) {
		String  dataSql= " select  * from  cz_equipment_info "
				+ "	where 1=1  " + condition;				
		System.out.println("导出excel----------------查询：" + dataSql);
		JSONArray dataArr = DataUtil.getData( dataSql );
		return dataArr;
	}
}