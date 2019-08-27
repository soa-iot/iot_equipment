// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExportExcelServlet.java

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

public class ExportExcelOfEquServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String excelContents[][];

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
		String nowDate = sdf.format(new Date(System.currentTimeMillis()));
		String excelName = (new StringBuilder(String.valueOf(nowDate))).append("设备基础信息").toString();
		res.setHeader("Content-Disposition", (new StringBuilder("attachment;filename="))
				.append(new String(excelName.getBytes("gb2312"), "ISO8859-1")).append(".xls").toString());
		res.setHeader("Connection", "close");
		res.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
		OutputStream out = res.getOutputStream();

		/*
		 * 参数定义
		 */
		// 设备对应的后端附件存储位置,初始化excel配置信息
		Map<String, String> equipAttachLocationUrlConfig = new HashMap<String, String>();

		equipAttachLocationUrlConfig.put("控制系统", "九龙山天然气净化厂控制系统台账");
		equipAttachLocationUrlConfig.put("DCS系统", "九龙山天然气净化厂DCS系统台账");
		equipAttachLocationUrlConfig.put("SIS/FGS系统", "九龙山天然气净化厂SIS/FGS系统台账");
		equipAttachLocationUrlConfig.put("导热油控制系统", "九龙山天然气净化厂导热油控制系统台账");
		equipAttachLocationUrlConfig.put("除盐水控制系统", "九龙山天然气净化厂除盐水控制系统台账");
		equipAttachLocationUrlConfig.put("硫磺过滤机控制系统", "九龙山天然气净化厂硫磺过滤机控制系统台账");
		equipAttachLocationUrlConfig.put("火炬控制系统", "九龙山天然气净化厂火炬控制系统台账");
		equipAttachLocationUrlConfig.put("变频供水控制系统", "九龙山天然气净化厂变频供水控制系统台账");
		equipAttachLocationUrlConfig.put("检测回路", "九龙山天然气净化厂检测回路台账");
		equipAttachLocationUrlConfig.put("控制回路", "九龙山天然气净化厂控制回路台账");
		equipAttachLocationUrlConfig.put("控制系统安全联锁回路", "九龙山天然气净化厂控制系统安全联锁回路台账");
		equipAttachLocationUrlConfig.put("气动（电动）切断阀", "九龙山天然气净化厂气动（电动）切断阀台账");
		equipAttachLocationUrlConfig.put("气动（电动）调节阀", "九龙山天然气净化厂气动（电动）调节阀台账");
		equipAttachLocationUrlConfig.put("电工器具", "九龙山天然气净化厂电工器具台账");
		equipAttachLocationUrlConfig.put("静设备", "九龙山天然气净化厂静设备台账");
		equipAttachLocationUrlConfig.put("动设备", "九龙山天然气净化厂动设备台账");
		equipAttachLocationUrlConfig.put("管道", "九龙山天然气净化厂管道台账");
		equipAttachLocationUrlConfig.put("安全阀", "九龙山天然气净化厂安全阀台账");
		equipAttachLocationUrlConfig.put("锅炉", "九龙山天然气净化厂锅炉台账");
		equipAttachLocationUrlConfig.put("厂内车辆", "九龙山天然气净化厂厂内车辆台账");
		equipAttachLocationUrlConfig.put("气体报警仪表", "九龙山天然气净化厂气体报警仪表台账");
		equipAttachLocationUrlConfig.put("标准孔板", "九龙山天然气净化厂标准孔板台账");
		equipAttachLocationUrlConfig.put("计量标准器具", "九龙山天然气净化厂计量标准器具台账");
		equipAttachLocationUrlConfig.put("流量计量器具", "九龙山天然气净化厂流量计量器具台账");
		equipAttachLocationUrlConfig.put("生产过程监控计量器具", "九龙山天然气净化厂生产过程监控计量器具台账");

		// Excel标题配置信息//
		Map<String, String> excelTitleConfig = new HashMap<String, String>();
		excelTitleConfig.put("控制系统", "九龙山天然气净化厂控制系统台账");
		excelTitleConfig.put("DCS系统", "九龙山天然气净化厂DCS系统台账");
		excelTitleConfig.put("SIS/FGS系统", "九龙山天然气净化厂SIS/FGS系统台账");
		excelTitleConfig.put("导热油控制系统", "九龙山天然气净化厂导热油控制系统台账");
		excelTitleConfig.put("除盐水控制系统", "九龙山天然气净化厂除盐水控制系统台账");
		excelTitleConfig.put("硫磺过滤机控制系统", "九龙山天然气净化厂硫磺过滤机控制系统台账");
		excelTitleConfig.put("火炬控制系统", "九龙山天然气净化厂火炬控制系统台账");
		excelTitleConfig.put("变频供水控制系统", "九龙山天然气净化厂变频供水控制系统台账");
		excelTitleConfig.put("检测回路", "九龙山天然气净化厂检测回路台账");
		excelTitleConfig.put("控制回路", "九龙山天然气净化厂控制回路台账");
		excelTitleConfig.put("控制系统安全联锁回路", "九龙山天然气净化厂控制系统安全联锁回路台账");
		excelTitleConfig.put("气动（电动）切断阀", "九龙山天然气净化厂气动（电动）切断阀台账");
		excelTitleConfig.put("气动（电动）调节阀", "九龙山天然气净化厂气动（电动）调节阀台账");
		excelTitleConfig.put("电工器具", "九龙山天然气净化厂电工器具台账");
		excelTitleConfig.put("静设备", "九龙山天然气净化厂静设备台账");
		excelTitleConfig.put("动设备", "九龙山天然气净化厂动设备台账");
		excelTitleConfig.put("管道", "九龙山天然气净化厂管道台账");
		excelTitleConfig.put("安全阀", "九龙山天然气净化厂安全阀台账");
		excelTitleConfig.put("锅炉", "九龙山天然气净化厂锅炉台账");
		excelTitleConfig.put("厂内车辆", "九龙山天然气净化厂厂内车辆台账");
		excelTitleConfig.put("气体报警仪表", "九龙山天然气净化厂气体报警仪表台账");
		excelTitleConfig.put("标准孔板", "九龙山天然气净化厂标准孔板台账");
		excelTitleConfig.put("计量标准器具", "九龙山天然气净化厂计量标准器具台账");
		excelTitleConfig.put("流量计量器具", "九龙山天然气净化厂流量计量器具台账");
		excelTitleConfig.put("生产过程监控计量器具", "九龙山天然气净化厂生产过程监控计量器具台账");

		// Excel表头配置信息
		Map<String, Map<String, String>> excelHeadConfig = new HashMap<String, Map<String, String>>();
		//控制系统
		Map<String, String> excelHeadDetailConfig1 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig1.put("EQU_NAME","名称");
		excelHeadDetailConfig1.put("ELEC","功能描述");
		excelHeadDetailConfig1.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig1.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig1.put("FLUX","单位");
		excelHeadDetailConfig1.put("EQU_MANUFACTURER","制造厂");
		excelHeadDetailConfig1.put("EQU_COMMISSION_DATE","投用时间");
		excelHeadDetailConfig1.put("CAPCITY","备注");
		//DCS系统
		Map<String, String> excelHeadDetailConfig2 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig2.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig2.put("EQU_NAME","名称");
		excelHeadDetailConfig2.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig2.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig2.put("FLUX","单位");
		excelHeadDetailConfig2.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig2.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig2.put("CAPCITY","备注");
		//SIS/FGS系统
		Map<String, String> excelHeadDetailConfig3 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig3.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig3.put("EQU_NAME","名称");
		excelHeadDetailConfig3.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig3.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig3.put("FLUX","单位");
		excelHeadDetailConfig3.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig3.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig3.put("CAPCITY","备注");
		//导热油控制系统
		Map<String, String> excelHeadDetailConfig4 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig4.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig4.put("EQU_NAME","名称");
		excelHeadDetailConfig4.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig4.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig4.put("FLUX","单位");
		excelHeadDetailConfig4.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig4.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig4.put("CAPCITY","备注");
		//除盐水控制系统
		Map<String, String> excelHeadDetailConfig5 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig5.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig5.put("EQU_NAME","名称");
		excelHeadDetailConfig5.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig5.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig5.put("FLUX","单位");
		excelHeadDetailConfig5.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig5.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig5.put("CAPCITY","备注");
		//硫磺过滤机控制系统
		Map<String, String> excelHeadDetailConfig6 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig6.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig6.put("EQU_NAME","名称");
		excelHeadDetailConfig6.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig6.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig6.put("FLUX","单位");
		excelHeadDetailConfig6.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig6.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig6.put("CAPCITY","备注");
		//火炬控制系统
		Map<String, String> excelHeadDetailConfig7 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig7.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig7.put("EQU_NAME","名称");
		excelHeadDetailConfig7.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig7.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig7.put("FLUX","单位");
		excelHeadDetailConfig7.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig7.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig7.put("CAPCITY","备注");
		//变频供水控制系统
		Map<String, String> excelHeadDetailConfig8 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig8.put("EQU_WORK_TEMP","系统序号");
		excelHeadDetailConfig8.put("EQU_NAME","名称");
		excelHeadDetailConfig8.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig8.put("MEASURE_PRIN","数量");
		excelHeadDetailConfig8.put("FLUX","单位");
		excelHeadDetailConfig8.put("PROCE_LINK_TYPE","输入点类型");
		excelHeadDetailConfig8.put("FLA_SIZE","输入点数量");
		excelHeadDetailConfig8.put("CAPCITY","备注");
		//检测回路
		Map<String, String> excelHeadDetailConfig9 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig9.put("EQU_POSITION_NUM","位号");
		excelHeadDetailConfig9.put("ELEC","功能描述");
		excelHeadDetailConfig9.put("MEDUIM_TYPE","仪表类型");
		excelHeadDetailConfig9.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig9.put("VAVLE_TYPE","测量范围");
		excelHeadDetailConfig9.put("FLUX","单位");
		excelHeadDetailConfig9.put("EQU_MANUFACTURER","厂家");
		excelHeadDetailConfig9.put("CAPCITY","备注");
		//控制回路
		Map<String, String> excelHeadDetailConfig10 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig10.put("ACTUAL_MODEL","控制功能描述");
		excelHeadDetailConfig10.put("EQU_POSITION_NUM","控制变量-位号");
		excelHeadDetailConfig10.put("ACTUAL","控制变量-测量范围");
		excelHeadDetailConfig10.put("ELEC_MODEL","控制变量-控制范围");
		excelHeadDetailConfig10.put("ELEC","控制变量-单位");
		excelHeadDetailConfig10.put("MEASURE_PRIN","执行设备-位号");
		excelHeadDetailConfig10.put("PIPE_OUTER","执行设备-功能描述");
		excelHeadDetailConfig10.put("FLUX","执行设备-控制规律");
		excelHeadDetailConfig10.put("CAPCITY","备注");
		//控制系统安全联锁回路
		Map<String, String> excelHeadDetailConfig11 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig11.put("DEEP_LENGTH","联锁条件-位号");
		excelHeadDetailConfig11.put("VAVLE_TYPE","联锁条件-联锁条件描述");
		excelHeadDetailConfig11.put("ACTUAL","联锁条件-测量范围");
		excelHeadDetailConfig11.put("ACTUAL_MODEL","联锁条件-联锁范围");
		excelHeadDetailConfig11.put("ELEC_MODEL","联锁条件-单位");
		excelHeadDetailConfig11.put("PIPE_OUTER","联锁执行设备-位号");
		excelHeadDetailConfig11.put("POSITIONER","联锁执行设备-功能描述");
		excelHeadDetailConfig11.put("PROCE_LINK_TYPE","联锁执行设备-联锁动作");
		excelHeadDetailConfig11.put("CAPCITY","备注");
		//气动（电动）切断阀
		Map<String, String> excelHeadDetailConfig12 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig12.put("EQU_POSITION_NUM","位号");
		excelHeadDetailConfig12.put("MEDUIM_TYPE","功能描述");
		excelHeadDetailConfig12.put("DEEP_LENGTH","阀体-规格型号");
		excelHeadDetailConfig12.put("VAVLE_TYPE","阀体-工程直径");
		excelHeadDetailConfig12.put("MEASURE_PRIN","阀体-材质");
		excelHeadDetailConfig12.put("PIPE_OUTER","执行器-型号");
		excelHeadDetailConfig12.put("POSITIONER","执行器-电气接口尺寸");
		excelHeadDetailConfig12.put("PROCE_LINK_TYPE","执行器-气源接口尺寸");
		excelHeadDetailConfig12.put("ELECTRIC_PRES","执行器-供气（供电）电源");
		excelHeadDetailConfig12.put("EQU_MANUFACTURER","执行器-厂家");
		excelHeadDetailConfig12.put("EQU_COMMISSION_DATE","投用时间");
		excelHeadDetailConfig12.put("CAPCITY","备注");
		//气动（电动）调节阀
		Map<String, String> excelHeadDetailConfig13 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig13.put("EQU_POSITION_NUM","位号");
		excelHeadDetailConfig13.put("WEL_NAME","所在装置");
		excelHeadDetailConfig13.put("ACTUAL","功能描述");
		excelHeadDetailConfig13.put("DEEP_LENGTH","阀体-规格型号");
		excelHeadDetailConfig13.put("VAVLE_TYPE","阀体-工程直径");
		excelHeadDetailConfig13.put("MEASURE_PRIN","阀体-材质");
		excelHeadDetailConfig13.put("PIPE_OUTER","执行器-型号");
		excelHeadDetailConfig13.put("POSITIONER","执行器-电气接口尺寸");
		excelHeadDetailConfig13.put("PROCE_LINK_TYPE","执行器-气源接口尺寸");
		excelHeadDetailConfig13.put("ELECTRIC_PRES","执行器-供气（供电）电源");
		excelHeadDetailConfig13.put("ELEC","执行器-流量特性");
		excelHeadDetailConfig13.put("CV","执行器-额定CV");
		excelHeadDetailConfig13.put("EQU_MANUFACTURER","执行器-厂家");
		excelHeadDetailConfig13.put("EQU_COMMISSION_DATE","投用时间");
		excelHeadDetailConfig13.put("CAPCITY","备注");
		//电工器具
		Map<String, String> excelHeadDetailConfig14 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig14.put("WEL_NAME","单位");
		excelHeadDetailConfig14.put("EQU_NAME","名称");
		excelHeadDetailConfig14.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig14.put("EQU_WORK_TEMP","出厂/自编号");
		excelHeadDetailConfig14.put("EQU_MANUFACTURER","生产厂家");
		excelHeadDetailConfig14.put("DEEP_LENGTH","放置地点");
		excelHeadDetailConfig14.put("PROCE_LINK_TYPE","使用状态");
		excelHeadDetailConfig14.put("ACTUAL","检定周期");
		excelHeadDetailConfig14.put("EQU_COMMISSION_DATE","上次检定日期");
		excelHeadDetailConfig14.put("FREQUENCY","1次计划检定日期");
		excelHeadDetailConfig14.put("BRAND","2次计划检定日期");
		excelHeadDetailConfig14.put("FLUX","检测单位");
		excelHeadDetailConfig14.put("GAS_SOURCE","检定结果");
		excelHeadDetailConfig14.put("ELECTRIC_PRES","保管人");
		excelHeadDetailConfig14.put("CAPCITY","备注");
		//静设备
		Map<String, String> excelHeadDetailConfig15 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig15.put("EQU_NAME","设备名称");
		excelHeadDetailConfig15.put("EQU_POSITION_NUM","设备位号");
		excelHeadDetailConfig15.put("WEL_NAME","生产装置");
		excelHeadDetailConfig15.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig15.put("SPINDLE_SPEED","设备类型");
		excelHeadDetailConfig15.put("MEDUIM_TYPE","资产原值");
		excelHeadDetailConfig15.put("EQU_MANUFACTURER","生产厂家");
		excelHeadDetailConfig15.put("EQU_PRODUC_DATE","出厂日期");
		excelHeadDetailConfig15.put("EQU_WORK_TEMP","出厂编号");
		excelHeadDetailConfig15.put("EQU_COMMISSION_DATE","投用日期");
		excelHeadDetailConfig15.put("DEEP_LENGTH","主体材质");
		excelHeadDetailConfig15.put("VAVLE_TYPE","重量Kg");
		excelHeadDetailConfig15.put("ACTUAL_MODEL","壳程-介质");
		excelHeadDetailConfig15.put("ACTUAL","壳程-设计压力MPa");
		excelHeadDetailConfig15.put("ELEC_MODEL","壳程-设计温度℃");
		excelHeadDetailConfig15.put("ELEC","管程-介质");
		excelHeadDetailConfig15.put("MEASURE_PRIN","管程-设计压力MPa");
		excelHeadDetailConfig15.put("PIPE_OUTER","管程-设计温度℃");
		excelHeadDetailConfig15.put("FLUX","换热面积m2");
		excelHeadDetailConfig15.put("PIPE_THICK","安全阀-数量");
		excelHeadDetailConfig15.put("CV","安全阀-规格型号");
		excelHeadDetailConfig15.put("POSITIONER","腐蚀余量");
		excelHeadDetailConfig15.put("GAS_SOURCE","过滤精度");
		excelHeadDetailConfig15.put("FLA_SIZE","容器类别");
		excelHeadDetailConfig15.put("PROCE_LINK_TYPE","注册编号");
		excelHeadDetailConfig15.put("POWER_RATE","使用证编号");
		excelHeadDetailConfig15.put("ELECTRIC_PRES","检验报告编号");
		excelHeadDetailConfig15.put("ELECTRIC_TENSION","安全状况等级");
		excelHeadDetailConfig15.put("FREQUENCY","检验情况-上次检验日期");
		excelHeadDetailConfig15.put("BRAND","检验情况-下次检验日期");
		excelHeadDetailConfig15.put("CAPCITY","备注");
		//动设备
		Map<String, String> excelHeadDetailConfig16 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig16.put("EQU_NAME","设备名称");
		excelHeadDetailConfig16.put("EQU_POSITION_NUM","设备位号");
		excelHeadDetailConfig16.put("WEL_NAME","生产装置");
		excelHeadDetailConfig16.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig16.put("SPINDLE_SPEED","设备类型");
		excelHeadDetailConfig16.put("MEDUIM_TYPE","资产原值");
		excelHeadDetailConfig16.put("EQU_MANUFACTURER","生产厂家");
		excelHeadDetailConfig16.put("EQU_PRODUC_DATE","出厂日期");
		excelHeadDetailConfig16.put("EQU_WORK_TEMP","出厂编号");
		excelHeadDetailConfig16.put("DEEP_LENGTH","主体材质");
		excelHeadDetailConfig16.put("VAVLE_TYPE","重量KG");
		excelHeadDetailConfig16.put("ACTUAL_MODEL","介质");
		excelHeadDetailConfig16.put("AFTER_BEARING1","流量（m3/h）");
		excelHeadDetailConfig16.put("GREASE_INTERV","扬程（m）");
		excelHeadDetailConfig16.put("GREASE_QUAN","效率（%）");
		excelHeadDetailConfig16.put("INSULATION_RATE","进/出口 压力（MPa）");
		excelHeadDetailConfig16.put("PROTECTION_RATE","密封方式");
		excelHeadDetailConfig16.put("PHASE_NUMBER","冷却方式");
		excelHeadDetailConfig16.put("CONNECTION_GROUP","联轴器结构形式");
		excelHeadDetailConfig16.put("HEIGHT_ELECTRIC_PRES	","电机功率（kw）");
		excelHeadDetailConfig16.put("WEIGHT","轴转速（rpm）");
		excelHeadDetailConfig16.put("SNATCH_ELECTRIC_PRES","备注");
		//管道
		Map<String, String> excelHeadDetailConfig17 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig17.put("WEL_NAME","所在装置名称");
		excelHeadDetailConfig17.put("EQU_NAME","管道名称");
		excelHeadDetailConfig17.put("EQU_WORK_TEMP","管道编号");
		excelHeadDetailConfig17.put("EQU_MANUFACTURER","设计单位");
		excelHeadDetailConfig17.put("PEAK_TENSION","安装单位");
		excelHeadDetailConfig17.put("EQU_DESIGN_TEMP","安装年月");
		excelHeadDetailConfig17.put("CORROSION_FATIGUE","投用年月");
		excelHeadDetailConfig17.put("DESIGN_TUBE_TEMP","规格-公称直径mm");
		excelHeadDetailConfig17.put("DESIGN_TUBE_PRES","规格-公称壁厚mm");
		excelHeadDetailConfig17.put("OPTION_SHELL_PRESS","规格-管道长度mm");
		excelHeadDetailConfig17.put("OPTION_SHELL_IN_TEMP","设计/操作条件-压力Mpa");
		excelHeadDetailConfig17.put("OPTION_SHELL_OUT_TEMP","设计/操作条件-温度℃");
		excelHeadDetailConfig17.put("OPTION_SHELL_MEDUIM","设计/操作条件-介质");
		excelHeadDetailConfig17.put("OPTION_TUBE_IN_TEMP","防护保温涂层方式");
		excelHeadDetailConfig17.put("OPTION_TUBE_OUT_TEMP","管道材质");
		excelHeadDetailConfig17.put("OPTION_TUBE_MEDUIM","焊口数量");
		excelHeadDetailConfig17.put("SHELL_MATERIAL","管道级别");
		excelHeadDetailConfig17.put("TUBE_MATERIAL","安全状况等级");
		excelHeadDetailConfig17.put("EQU_LASTPERIODIC_DATE","检验情况-上次检验日期");
		excelHeadDetailConfig17.put("DISPLACEMENT","检验情况-计划检验日期");
		excelHeadDetailConfig17.put("SPINDLE_MEDUIM","报告编号");
		excelHeadDetailConfig17.put("PUMP_MEDUIM","备注");
		//安全阀
		Map<String, String> excelHeadDetailConfig18 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig18.put("EQU_MANUFACTURER","单位");
		excelHeadDetailConfig18.put("EQU_INSTALL_POSITION","安装地点");
		excelHeadDetailConfig18.put("EQU_MODEL","安全阀型号、规格");
		excelHeadDetailConfig18.put("WIND_PRESSURE","整定压力（MPa）");
		excelHeadDetailConfig18.put("WEL_NAME","实际工作压力（MPa）");
		excelHeadDetailConfig18.put("ENGINE_NUMBER","制造单位");
		excelHeadDetailConfig18.put("LICENSE_NUMBER","压力级别范围（Mpa）");
		excelHeadDetailConfig18.put("EQU_WORK_TEMP","产品编号");
		excelHeadDetailConfig18.put("EQU_PRODUC_DATE","出厂日期");
		excelHeadDetailConfig18.put("EQU_COMMISSION_DATE","启用日期");
		excelHeadDetailConfig18.put("EQU_LASTPERIODIC_DATE","上次校验日期");
		excelHeadDetailConfig18.put("DISPLACEMENT","计划校验日期");
		excelHeadDetailConfig18.put("ENERGY_CONSUMPTION_CAT","工作介质");
		excelHeadDetailConfig18.put("SERIAL_NUM","工作温度（℃）");
		excelHeadDetailConfig18.put("CHECK_CYCLE","安装高度（米）");
		excelHeadDetailConfig18.put("MEASURE_ACC","备注");
		//锅炉
		Map<String, String> excelHeadDetailConfig19 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig19.put("EQU_POSITION_NUM","位号");
		excelHeadDetailConfig19.put("EQU_NAME","名称");
		excelHeadDetailConfig19.put("EQU_WORK_TEMP","出厂编号");
		excelHeadDetailConfig19.put("EQU_MODEL","技术规格");
		excelHeadDetailConfig19.put("EQU_MANUFACTURER","制造单位");
		excelHeadDetailConfig19.put("DEEP_LENGTH","登记编号");
		excelHeadDetailConfig19.put("ORDER_NUM","注册代码");
		excelHeadDetailConfig19.put("VAVLE_TYPE","投用年月");
		excelHeadDetailConfig19.put("ACTION_MODLE","额定出力");
		excelHeadDetailConfig19.put("HAVE_NOT","技术特性-设计压力壳/管(Mpa)");
		excelHeadDetailConfig19.put("ACTUAL_MODEL","技术特性-设计温度壳/管(℃)");
		excelHeadDetailConfig19.put("ACTUAL","技术特性-操作压力壳/管(Mpa)");
		excelHeadDetailConfig19.put("ELEC_MODEL","技术特性-操作温度壳/管(℃)");
		excelHeadDetailConfig19.put("ELEC","技术特性-物料名称");
		excelHeadDetailConfig19.put("MEASURE_PRIN","检验报告编号");
		excelHeadDetailConfig19.put("EQU_LASTPERIODIC_DATE","检验情况-上次检验日期");
		excelHeadDetailConfig19.put("DISPLACEMENT","检验情况-计划检验日期");
		excelHeadDetailConfig19.put("PIPE_OUTER","资产价值");
		excelHeadDetailConfig19.put("FLUX","备注");
		//厂内车辆
		Map<String, String> excelHeadDetailConfig20 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig20.put("EQU_NAME","名称");
		excelHeadDetailConfig20.put("EQU_WORK_TEMP","出厂编号");
		excelHeadDetailConfig20.put("EQU_MODEL","技术规格");
		excelHeadDetailConfig20.put("DEEP_LENGTH","厂内编号");
		excelHeadDetailConfig20.put("POWER_RATE","厂内牌照编号");
		excelHeadDetailConfig20.put("ELECTRIC_PRES","空车  重量（kg）");
		excelHeadDetailConfig20.put("ELECTRIC_TENSION","载重量（kg）");
		excelHeadDetailConfig20.put("FREQUENCY","最高时速（km/h）");
		excelHeadDetailConfig20.put("BRAND","燃料种类");
		excelHeadDetailConfig20.put("CAPCITY","设备注册代码");
		excelHeadDetailConfig20.put("EQU_MANUFACTURER","制造单位");
		excelHeadDetailConfig20.put("SPEED_RAT","投用年月");
		excelHeadDetailConfig20.put("EQU_LASTPERIODIC_DATE","上次检验日期");
		excelHeadDetailConfig20.put("DISPLACEMENT","计划检验日期	");
		excelHeadDetailConfig20.put("GREASE_QUAN","定检报告");
		excelHeadDetailConfig20.put("EQU_INSTALL_POSITION","安装位置");
		excelHeadDetailConfig20.put("PIPE_OUTER","资产价值");
		excelHeadDetailConfig20.put("PROTECTION_RATE","备注");
		//气体报警仪表
		Map<String, String> excelHeadDetailConfig21 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig21.put("HAVE_NOT","站点名称");
		excelHeadDetailConfig21.put("ACTUAL_MODEL","类别");
		excelHeadDetailConfig21.put("EQU_PRODUC_DATE","投运日期");
		excelHeadDetailConfig21.put("EQU_NAME","气体报警器-名称");
		excelHeadDetailConfig21.put("EQU_MODEL","气体报警器-型号规格");
		excelHeadDetailConfig21.put("EQU_WORK_TEMP","气体报警器-器具编号");
		excelHeadDetailConfig21.put("FREQUENCY","气体报警器-准确度等级");
		excelHeadDetailConfig21.put("BRAND","气体报警器-测量范围");
		excelHeadDetailConfig21.put("EQU_INSTALL_POSITION","气体报警器-安装位置");
		excelHeadDetailConfig21.put("EQU_MANUFACTURER","气体报警器-生产厂家");
		excelHeadDetailConfig21.put("DEEP_LENGTH","固定式报警仪信号-使用状态");
		excelHeadDetailConfig21.put("ORDER_NUM","固定式报警仪信号-系统型号规格");
		excelHeadDetailConfig21.put("VAVLE_TYPE","固定式报警仪信号-生产厂家");
		excelHeadDetailConfig21.put("OPTION_TUBE_IN_TEMP","固定式报警仪信号-报警、连锁功能是否正常");
		excelHeadDetailConfig21.put("CHECK_CYCLE","检定周期");
		excelHeadDetailConfig21.put("EQU_LASTPERIODIC_DATE","上次检定日期");
		excelHeadDetailConfig21.put("OPTION_TUBE_OUT_TEMP","计划检定日期1");
		excelHeadDetailConfig21.put("OPTION_TUBE_MEDUIM","计划检定日期2");
		excelHeadDetailConfig21.put("GREASE_QUAN","检定方式");
		//标准孔板
		Map<String, String> excelHeadDetailConfig22 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig22.put("GREASE_INTERV","外径");
		excelHeadDetailConfig22.put("GREASE_QUAN","内径-出厂值");
		excelHeadDetailConfig22.put("INSULATION_RATE","内径-实测值");
		excelHeadDetailConfig22.put("PROTECTION_RATE","厚度(mm)");
		excelHeadDetailConfig22.put("EXPLOSION_RATE","材质");
		excelHeadDetailConfig22.put("EQU_MANUFACTURER","制造厂家");
		excelHeadDetailConfig22.put("FREQUENCY","检定单位");
		excelHeadDetailConfig22.put("BRAND","检定日期");
		excelHeadDetailConfig22.put("EQU_COMMISSION_DATE","投运日期");
		excelHeadDetailConfig22.put("CONNECTION_GROUP","场站");
		excelHeadDetailConfig22.put("SNATCH_ELECTRIC_PRES","井号或用户");
		excelHeadDetailConfig22.put("SNATCH_ELECTRIC_TENSION","状态");
		excelHeadDetailConfig22.put("MATERIAL","存放地点");
		excelHeadDetailConfig22.put("EQU_DESIGN_TEMP","新/报废");
		excelHeadDetailConfig22.put("EQU_PRODUC_DATE","入库日期/停用日期");
		excelHeadDetailConfig22.put("CAPCITY","备注");
		//计量标准器具
		Map<String, String> excelHeadDetailConfig23 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig23.put("WEL_NAME","单位");
		excelHeadDetailConfig23.put("EQU_NAME","名称");
		excelHeadDetailConfig23.put("EQU_MODEL","规格型号");
		excelHeadDetailConfig23.put("DEEP_LENGTH","准确度等级");
		excelHeadDetailConfig23.put("EQU_WORK_TEMP","出厂编号");
		excelHeadDetailConfig23.put("EQU_PRODUC_DATE","出厂日期");
		excelHeadDetailConfig23.put("EQU_MANUFACTURER","生产厂家");
		excelHeadDetailConfig23.put("EQU_COMMISSION_DATE","投用日期");
		excelHeadDetailConfig23.put("POSITIONER","放置地点");
		excelHeadDetailConfig23.put("GAS_SOURCE","使用状态");
		excelHeadDetailConfig23.put("FLA_SIZE","检定周期");
		excelHeadDetailConfig23.put("FREQUENCY","上次检定日期");
		excelHeadDetailConfig23.put("BRAND","计划检定日期");
		excelHeadDetailConfig23.put("MEASURE_PRIN","检定单位");
		excelHeadDetailConfig23.put("PIPE_THICK","检定结果");
		excelHeadDetailConfig23.put("SHELL_MATERIAL","保管人");
		excelHeadDetailConfig23.put("CAPCITY","备注");
		//流量计量器具
		Map<String, String> excelHeadDetailConfig24 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig24.put("CATEGORY","站名");
		excelHeadDetailConfig24.put("EXPLOSION_RATE","地理位置");
		excelHeadDetailConfig24.put("DEVICE_TYPE","计量对象");
		excelHeadDetailConfig24.put("HEIGHT_ELECTRIC_TENSION","计量性质");
		excelHeadDetailConfig24.put("PHASE_NUMBER","资产归属");
		excelHeadDetailConfig24.put("CONNECTION_GROUP","投运日期");
		excelHeadDetailConfig24.put("WEL_NAME","流量计量-装置名称");
		excelHeadDetailConfig24.put("WEIGHT","流量计量-型号规格");
		excelHeadDetailConfig24.put("MATERIAL","流量计量-编号");
		excelHeadDetailConfig24.put("CORROSION_FATIGUE","流量计量-准确度等级");
		excelHeadDetailConfig24.put("EQU_DESIGN_TEMP","流量计量-PN");
		excelHeadDetailConfig24.put("DESIGN_PRESSURE_RANGE","流量计量-DN");
		excelHeadDetailConfig24.put("SURFACE_HEAT_TRANSFER","流量计量-D20");
		excelHeadDetailConfig24.put("EQU_MANUFACTURER","流量计量-生产厂家");
		excelHeadDetailConfig24.put("DESIGN_SHELL_PRES","流量计量-使用状态");
		excelHeadDetailConfig24.put("DESIGN_SHELL_TEMP","计量系统-名称");
		excelHeadDetailConfig24.put("DESIGN_TUBE_TEMP","计量系统-型号");
		excelHeadDetailConfig24.put("ENERGY_CONSUMPTION_CAT","计量系统-生产厂家");
		excelHeadDetailConfig24.put("OPTION_SHELL_PRESS","检定周期");
		excelHeadDetailConfig24.put("FREQUENCY","上次检定日期");
		excelHeadDetailConfig24.put("BRAND","计划检定日期");
		excelHeadDetailConfig24.put("OPTION_SHELL_IN_TEMP","检定方式");
		excelHeadDetailConfig24.put("OPTION_SHELL_OUT_TEMP","检定单位");
		excelHeadDetailConfig24.put("OPTION_SHELL_MEDUIM","检定结果");
		excelHeadDetailConfig24.put("CAPCITY","备注");
		//生产过程监控计量器具
		Map<String, String> excelHeadDetailConfig25 = new LinkedHashMap<String, String>();
		excelHeadDetailConfig25.put("WEL_NAME","站点名称");
		excelHeadDetailConfig25.put("DEVICE_TYPE","类别");
		excelHeadDetailConfig25.put("EQU_COMMISSION_DATE","投运日期");
		excelHeadDetailConfig25.put("EQU_NAME","名称");
		excelHeadDetailConfig25.put("EQU_MODEL","型号规格");
		excelHeadDetailConfig25.put("EQU_WORK_TEMP","器具编号");
		excelHeadDetailConfig25.put("LICENSE_NUMBER","准确度等级");
		excelHeadDetailConfig25.put("WIND_PRESSURE","测量范围");
		excelHeadDetailConfig25.put("TUBE_MATERIAL","安装位置");
		excelHeadDetailConfig25.put("OPTION_TUBE_OUT_TEMP","生产厂家");
		excelHeadDetailConfig25.put("OPTION_TUBE_IN_TEMP","使用状态");
		excelHeadDetailConfig25.put("OPTION_SHELL_MEDUIM","检定周期");
		excelHeadDetailConfig25.put("FREQUENCY","上次检定日期");
		excelHeadDetailConfig25.put("BRAND","计划检定日期");
		excelHeadDetailConfig25.put("PHASE_NUMBER","检定方式");
		excelHeadDetailConfig25.put("HEIGHT_ELECTRIC_TENSION","检定单位");
		excelHeadDetailConfig25.put("COUNT","检定结果");
		excelHeadDetailConfig25.put("CAPCITY","备注");


		/*
		 * 接受请求参数
		 */
		String moduleType = req.getParameter("moduleType");
		String EQU_MEMO_ONE = req.getParameter("EQU_MEMO_ONE");

		// 判断
		if (EQU_MEMO_ONE == null) {
			res.getWriter().write("请求失败,设备类型为空");
		}
		if (moduleType == null) {
			res.getWriter().write("请求失败,请求类型为空");
		}
		// 具体表头配置增加……
		// 通用具体表头配置
		if ("2".equals(moduleType.trim())) {
			// excelHeadDetailConfig.put( "EQUIPMENT_ATTACH_URL", "附件位置" );

		}

		//控制系统
		excelHeadConfig.put("控制系统", excelHeadDetailConfig1);
		//DCS系统
		excelHeadConfig.put("DCS系统", excelHeadDetailConfig2);
		//SIS/FGS系统
		excelHeadConfig.put("SIS/FGS系统", excelHeadDetailConfig3);
		//导热油控制系统
		excelHeadConfig.put("导热油控制系统", excelHeadDetailConfig4);
		//除盐水控制系统
		excelHeadConfig.put("除盐水控制系统", excelHeadDetailConfig5);
		//硫磺过滤机控制系统
		excelHeadConfig.put("硫磺过滤机控制系统", excelHeadDetailConfig6);
		//火炬控制系统
		excelHeadConfig.put("火炬控制系统", excelHeadDetailConfig7);
		//变频供水控制系统
		excelHeadConfig.put("变频供水控制系统", excelHeadDetailConfig8);
		//检测回路
		excelHeadConfig.put("检测回路", excelHeadDetailConfig9);
		//控制回路
		excelHeadConfig.put("控制回路", excelHeadDetailConfig10);
		//控制系统安全联锁回路
		excelHeadConfig.put("控制系统安全联锁回路", excelHeadDetailConfig11);
		//气动（电动）切断阀
		excelHeadConfig.put("气动（电动）切断阀", excelHeadDetailConfig12);
		//气动（电动）调节阀
		excelHeadConfig.put("气动（电动）调节阀", excelHeadDetailConfig13);
		//电工器具
		excelHeadConfig.put("电工器具", excelHeadDetailConfig14);
		//静设备
		excelHeadConfig.put("静设备", excelHeadDetailConfig15);
		//动设备
		excelHeadConfig.put("动设备", excelHeadDetailConfig16);
		//管道
		excelHeadConfig.put("管道", excelHeadDetailConfig17);
		//安全阀
		excelHeadConfig.put("安全阀", excelHeadDetailConfig18);
		//锅炉
		excelHeadConfig.put("锅炉", excelHeadDetailConfig19);
		//厂内车辆
		excelHeadConfig.put("厂内车辆", excelHeadDetailConfig20);
		//气体报警仪表
		excelHeadConfig.put("气体报警仪表", excelHeadDetailConfig21);
		//标准孔板
		excelHeadConfig.put("标准孔板", excelHeadDetailConfig22);
		//计量标准器具
		excelHeadConfig.put("计量标准器具", excelHeadDetailConfig23);
		//流量计量器具
		excelHeadConfig.put("流量计量器具", excelHeadDetailConfig24);
		//生产过程监控计量器具
		excelHeadConfig.put("生产过程监控计量器具", excelHeadDetailConfig25);

		// 动态生成表格标题
		String currentExcelTitle = excelTitleConfig.get(EQU_MEMO_ONE.trim());
		System.out.println("--------Excel表格标题--------" + currentExcelTitle);

		// 动态生成表格表头
		Map<String, String> currentEquipmentMap = excelHeadConfig.get(EQU_MEMO_ONE.trim());
		String[] excelHeader = new String[currentEquipmentMap.size()];
		int aFor = 0;
		for (Map.Entry<String, String> entry : currentEquipmentMap.entrySet()) {
			excelHeader[aFor] = entry.getValue();
			aFor++;
		}
		System.out.println("--------Excel表格表头--------" + excelHeader.toString());

		// 动态生成数据Excel内容
		JSONArray excelJsonArr = new JSONArray();

		if (!"2".equals(moduleType.trim())) {
			System.out.println("--------导出Excel数据--------");
			// 通用
			String WEL_NAME = req.getParameter("WEL_NAME");
			String WEL_UNIT = req.getParameter("WEL_UNIT");
			String EQU_POSITION_NUM = req.getParameter("EQU_POSITION_NUM");
			String EQU_NAME = req.getParameter("EQU_NAME");

			// 设备大类
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

			String MEDUIM_TYPE = req.getParameter("MEDUIM_TYPE");

			// 判断查询方式
			String condition = "";
			if (SECONDCLASS_EQUIPMENT != null && !"".equals(SECONDCLASS_EQUIPMENT.trim())) {
				if ("全部设备".equals(SECONDCLASS_EQUIPMENT.trim())) {
					condition = createTotalCondition(EQU_MEMO_ONE, EQU_POSITION_NUM, EQU_NAME);
				} else {
					condition = createSecondEquipmentCondition(SECONDCLASS_EQUIPMENT, WEL_NAME, WEL_UNIT, EQU_MEMO_ONE,
							EQU_POSITION_NUM, EQU_NAME);
				}
			} else {
				condition = createThirdCondition(WEL_NAME, WEL_UNIT, EQU_MEMO_ONE, EQU_POSITION_NUM, EQU_NAME, // 通用
						MANAGE_TYPE, EQU_MODEL, MEARING_RANGE, MEASURE_ACC, EQU_INSTALL_POSITION, EQU_MANUFACTURER,
						SERIAL_NUM, CHECK_CYCLE, CHECK_TIME, NEXT_CHECK_TIME, REMARK1, // 压力表
						MEDUIM_TYPE// 压力差压变送器
				);
			}
			System.out.println("导出excel----------------condition:" + condition);
			excelJsonArr = getEquList(condition);

			excelContents = new String[excelJsonArr.length()][currentEquipmentMap.size()];
			System.out.println("excelJsonArr.length()>>>>>>>>>>>" + excelJsonArr.length());
			System.out.println("currentEquipmentMap.size()>>>>>>>>>" + currentEquipmentMap.size());
			System.out.println("excelJsonArr.length()>>>>>>>>>" + excelJsonArr.length());

			try {
				int bFor = 0;
				for (int i = 0; i < excelJsonArr.length(); i++) {
					bFor = 0;
					JSONObject json = excelJsonArr.getJSONObject(i);
					excelContents[i][0] = (i + 1) + "";
					for (Map.Entry<String, String> entry : currentEquipmentMap.entrySet()) {
						if ("order".equals(entry.getKey().trim())) {
							continue;
						}

						excelContents[i][bFor] = json.getString(entry.getKey().trim());
						bFor++;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				excelContents = null;
			}
			System.out.println("--------Excel表格数据内容--------" + excelContents.toString());

			/*
			 * 生成excel
			 */
			exportExcelPath(excelHeader, excelContents, currentExcelTitle, out);
		} else {
			System.out.println("--------导出空白模板--------");
			excelContents = new String[1][currentEquipmentMap.size()];
			int bFor = 1;
			excelContents[0][0] = "1";
			for (Map.Entry<String, String> entry : currentEquipmentMap.entrySet()) {
				if ("EQUIPMENT_ATTACH_URL".equals(entry.getKey().trim())) {
					excelContents[0][bFor] = equipAttachLocationUrlConfig.get(EQU_MEMO_ONE.trim());
					continue;
				}
				if ("order".equals(entry.getKey().trim())) {
					continue;
				}
				excelContents[0][bFor] = " ";
				bFor++;
			}
			/*
			 * 生成excel
			 */
			exportExcelPath(excelHeader, excelContents, currentExcelTitle, out);
		}
	}

	/**
	 * @Title: exportExcelPath @Description: 生成excel @param @param
	 *         excelHeader @param @param excelContent @param @param
	 *         currentExcelTitle @param @param out 参数 @return void 返回类型 @throws
	 */
	public static void exportExcelPath(String excelHeader[], String excelContent[][], String currentExcelTitle,
			OutputStream out) {
		try {
			HSSFWorkbook excel = Im_ExportExcel.exportExcel(excelHeader, excelContent, currentExcelTitle);
			excel.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: createTotalCondition @Description: 查询所有设备 @param @return 参数 @return
	 *         String 返回类型 @throws
	 */
	private String createTotalCondition(String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME) {
		String condition = "";
		// if( isEmpty( WEL_NAME ) ) {
		// condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		// }
		// if( isEmpty( WEL_UNIT ) ) {
		// condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
		// }
		if (isEmpty(EQU_MEMO_ONE)) {
			condition = condition + "and EQU_MEMO_ONE like '%" + EQU_MEMO_ONE + "%'";
		}
		if (isEmpty(EQU_POSITION_NUM)) {
			condition = condition + "and EQU_POSITION_NUM like '%" + EQU_POSITION_NUM + "%'";
		}
		if (isEmpty(EQU_NAME)) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		return condition;
	}

	/**
	 * @Title: createSecondEquipmentCondition @Description: 按照二级分类查询 @param @return
	 *         参数 @return String 返回类型 @throws
	 */

	private String createSecondEquipmentCondition(String SECONDCLASS_EQUIPMENT, String WEL_NAME, String WEL_UNIT,
			String EQU_MEMO_ONE, String EQU_POSITION_NUM, String EQU_NAME) {
		String condition = "";
		if (isEmpty(WEL_NAME)) {
			condition = condition + "and WEL_NAME like '%" + WEL_NAME + "%'";
		}
		if (isEmpty(WEL_UNIT)) {
			condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
		}
		if (isEmpty(EQU_MEMO_ONE)) {
			condition = condition + "and EQU_MEMO_ONE like '%" + EQU_MEMO_ONE + "%'";
		}
		if (isEmpty(EQU_POSITION_NUM)) {
			condition = condition + "and EQU_POSITION_NUM like '%" + EQU_POSITION_NUM + "%'";
		}
		if (isEmpty(EQU_NAME)) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		if (isEmpty(SECONDCLASS_EQUIPMENT)) {
			condition = condition + "and SECONDCLASS_EQUIPMENT like '%" + SECONDCLASS_EQUIPMENT + "%'";
		}
		return condition;
	}

	/**
	 * @Title: createThirdCondition @Description: 按照三级分类查询 @param @param
	 *         WEL_NAME @param @param WEL_UNIT @param @param
	 *         EQU_MEMO_ONE @param @param EQU_POSITION_NUM @param @param
	 *         EQU_NAME @param @param MANAGE_TYPE @param @param
	 *         EQU_MODEL @param @param MEARING_RANGE @param @param
	 *         MEASURE_ACC @param @param EQU_INSTALL_POSITION @param @param
	 *         EQU_MANUFACTURER @param @param SERIAL_NUM @param @param
	 *         CHECK_CYCLE @param @param CHECK_TIME @param @param
	 *         NEXT_CHECK_TIME @param @param REMARK1 @param @return 参数 @return
	 *         String 返回类型 @throws
	 */

	private String createThirdCondition(String WEL_NAME, String WEL_UNIT, String EQU_MEMO_ONE, String EQU_POSITION_NUM,
			String EQU_NAME, // 通用
			String MANAGE_TYPE, String EQU_MODEL, String MEARING_RANGE, String MEASURE_ACC, String EQU_INSTALL_POSITION,
			String EQU_MANUFACTURER, String SERIAL_NUM, String CHECK_CYCLE, String CHECK_TIME, String NEXT_CHECK_TIME,
			String REMARK1, String MEDUIM_TYPE) {
		String condition = "";
		if (isEmpty(WEL_NAME)) {
			condition = condition + "and WEL_NAME like '%" + WEL_NAME + "%'";
		}
		if (isEmpty(WEL_UNIT)) {
			condition = condition + "and WEL_UNIT like '%" + WEL_UNIT + "%'";
		}
		if (isEmpty(EQU_MEMO_ONE)) {
			condition = condition + "and EQU_MEMO_ONE like '%" + EQU_MEMO_ONE + "%'";
		}
		if (isEmpty(EQU_POSITION_NUM)) {
			condition = condition + "and EQU_POSITION_NUM like '%" + EQU_POSITION_NUM + "%'";
		}
		if (isEmpty(EQU_NAME)) {
			condition = condition + "and EQU_NAME like '%" + EQU_NAME + "%'";
		}
		if (isEmpty(MANAGE_TYPE)) {
			condition = condition + "and MANAGE_TYPE like '%" + MANAGE_TYPE + "%'";
		}
		if (isEmpty(EQU_MODEL)) {
			condition = condition + "and EQU_MODEL like '%" + EQU_MODEL + "%'";
		}
		if (isEmpty(MEARING_RANGE)) {
			condition = condition + "and MEARING_RANGE like '%" + MEARING_RANGE + "%'";
		}
		if (isEmpty(MEASURE_ACC)) {
			condition = condition + "and MEASURE_ACC like '%" + MEASURE_ACC + "%'";
		}
		if (isEmpty(EQU_INSTALL_POSITION)) {
			condition = condition + "and EQU_INSTALL_POSITION like '%" + EQU_INSTALL_POSITION + "%'";
		}
		if (isEmpty(EQU_MANUFACTURER)) {
			condition = condition + "and EQU_MANUFACTURER like '%" + EQU_MANUFACTURER + "%'";
		}
		if (isEmpty(SERIAL_NUM)) {
			condition = condition + "and SERIAL_NUM like '%" + SERIAL_NUM + "%'";
		}
		if (isEmpty(CHECK_CYCLE)) {
			condition = condition + "and CHECK_CYCLE like '%" + CHECK_CYCLE + "%'";
		}
		if (isEmpty(CHECK_TIME)) {
			condition = condition + "and CHECK_TIME like '%" + CHECK_TIME + "%'";
		}
		if (isEmpty(NEXT_CHECK_TIME)) {
			condition = condition + "and NEXT_CHECK_TIME like '%" + NEXT_CHECK_TIME + "%'";
		}
		if (isEmpty(REMARK1)) {
			condition = condition + "and REMARK1 like '%" + REMARK1 + "%'";
		}
		if (isEmpty(MEDUIM_TYPE)) {
			condition = condition + "and MEDUIM_TYPE like '%" + MEDUIM_TYPE + "%'";
		}
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

	};

	/**
	 * @Title: getEquList @Description: 获取数据 @param @param pageNumber @param @param
	 *         pageSize @param @param condition @param @return 参数 @return JSONObject
	 *         返回类型 @throws
	 */
	private JSONArray getEquList(String condition) {
		String dataSql = " select  * from  cz_equipment_info " + "	where 1=1  " + condition;
		System.out.println("导出excel----------------查询：" + dataSql);
		JSONArray dataArr = DataUtil.getData(dataSql);
		return dataArr;
	}
}