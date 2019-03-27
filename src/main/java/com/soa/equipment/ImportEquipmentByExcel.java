// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ImportEquipmentByExcel.java

package com.soa.equipment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

// Referenced classes of package com.soa.CZ_PIOTMS.server:
//			DBUtil

public class ImportEquipmentByExcel extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public ImportEquipmentByExcel()
	{
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		String path = (new StringBuilder("f:")).append(File.separator).append("ftp").append(File.separator).append("czqk2").toString();
		String type = request.getParameter("equ_type");
		String Name = null;
		System.out.println((new StringBuilder("设定文件放置位置：")).append(path).toString());
		File file = new File(path);
		System.out.println((new StringBuilder("断点1：")).append(path).toString());
		if (!file.exists())
			file.mkdirs();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(0x100000);
		factory.setRepository(file);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		try
		{
			List list = upload.parseRequest(request);
			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				FileItem item = (FileItem)iterator.next();
				if (item.isFormField())
				{
					type = item.getString("utf-8");
				} else
				{
					Name = item.getName();
					InputStream in = item.getInputStream();
					File f = new File(path, Name);
					f.createNewFile();
					FileOutputStream out = new FileOutputStream(f);
					System.out.println((new StringBuilder("获取的文件名Name：")).append(Name).append("\n").append("对应的文件大小:").append(item.getSize()).toString());
					byte buffer[] = new byte[1024];
					int len;
					while ((len = in.read(buffer)) != -1) 
						out.write(buffer, 0, len);
					out.flush();
					in.close();
					out.close();
					String var = (new StringBuilder(String.valueOf(path))).append(File.separator).append(Name).toString();
					System.out.println((new StringBuilder("获取的文件名lujing：")).append(var).toString());
					System.out.println((new StringBuilder("获取的文件名type：")).append(type).toString());
					JSONObject obj =saveToDb(var, type);
					item.delete();
					response.getWriter().write(obj.toString());
				}
			}

		}
		catch (FileUploadException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	//判断是否是指定的日期字符串格式
	public Boolean dateValidate(String dateString) {
		if(dateString.equals("")||dateString==null) {
			return true;
		}
		String  regex ="((19|20)[0-9]{2})-((0?[1-9])|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		return dateString.matches(regex);
	}
	//判断是否未指定的设备位号格式
	public Boolean pointValidate(String pointString) {
		if(pointString.equals("")||pointString==null) {
			return true;
		}
		String  regex ="((19|20)[0-9]{2})-((0?[1-9])|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		return pointString.matches(regex);
	}
	//分别获取位号，和检定日期的所在excel的位置
	public int findIndex(Map map ,String s) {
		int index=-1;
		Iterator iter = map.entrySet().iterator();
		Boolean flag=false;
		while(iter.hasNext()) {
			index++;
			java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
			String key = (String)entry.getKey();
			if(s.equals(key)) {
				flag=true;
			}
		}
		if(!flag) {
			index=-1;
		}
		return index;
	}
	//获取excel导入的数据，主要是位号和时间
	public  List getExcelData(Sheet sheet,int rowNum,int index) {
		Row row = null;
		List<String> list=new ArrayList<String>();
		for (int a = 3; a <= rowNum; a++)
		{
			row = sheet.getRow(a);
			list.add(getCellValue(row.getCell(index)));

		}
		return list;
		
	}
	public JSONObject saveToDb(String path, String type)
		throws Exception
	{	
	 	JSONArray jsonarray = new JSONArray();  
	 	JSONObject obj=new JSONObject();
		FileInputStream input = new FileInputStream(new File(path));
		Workbook book = null;
		if (path.indexOf(".xlsx") > -1)
		{
			System.out.println("book的book1:");
			book = new XSSFWorkbook(input);
			System.out.println((new StringBuilder("book的book:")).append(book).toString());
		} else
		{
			System.out.println("book的book2:");
			book = new HSSFWorkbook(input);
		}
		Sheet sheet = book.getSheetAt(0);
		Row row = null;
		Cell cell = null;
		int rowNum = sheet.getLastRowNum();
		System.out.println((new StringBuilder("book的book:")).append(book).toString());
		Map excelHeadDetailConfig = getConfigInfo();
		Map currentEquipmentMap = (Map)excelHeadDetailConfig.get(type.trim());
		System.out.println((new StringBuilder("currentEquipmentMap")).append(currentEquipmentMap).toString());
		//1.获取位号的位数
		int pointIndex=findIndex(currentEquipmentMap, "EQU_POSITION_NUM");
		List<String> pointLists=null;
		List<String> nomatachPoint=null;
		//2.获取所有的位号
		if(pointIndex>-1) {
			pointLists=getExcelData(sheet,rowNum,pointIndex);
		}
		//3.获取不满足位号的list
		if(pointLists.size()>0) {
			for(int i=0;i<pointLists.size();i++) {
				if(!pointValidate(pointLists.get(i))) {
					nomatachPoint.add(pointLists.get(i));
				};
			}
		}
		//4.获取鉴定日期的位数
		int checkTimeIndex=findIndex(currentEquipmentMap, "CHECK_TIME");
		List<String> checkLists=null;
		List<String> nomatachCheck=null;
		//5.获取所有的位号
		if(pointIndex>-1) {
			checkLists=getExcelData(sheet,rowNum,checkTimeIndex);
		}
		//6.获取不满足位号的list
		if(checkLists.size()>0) {
			for(int i=0;i<pointLists.size();i++) {
				if(!dateValidate(pointLists.get(i))) {
					nomatachCheck.add(pointLists.get(i));
				};
			}
		}
		if(nomatachPoint.size()>0) {
			obj.put("位号格式不合规", nomatachPoint);
		}
		if(nomatachCheck.size()>0) {
			obj.put("鉴定日期格式不合规", nomatachCheck);
			
		}
		if((nomatachPoint.size()>0||nomatachCheck.size()>0)) {
			obj.put("msg", "1");
			return obj;
		}
		Iterator iter = currentEquipmentMap.entrySet().iterator();
		String setInfo[] = new String[currentEquipmentMap.size() - 2];
		String intoA[] = new String[currentEquipmentMap.size() - 1];
		String val[] = new String[currentEquipmentMap.size() - 1];
		int count = 0;
		while (iter.hasNext()) 
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
			String key = (String)entry.getKey();
			if (!key.equals("order"))
			{
				intoA[count] = (new StringBuilder("info.")).append(key).toString();
				val[count] = "?";
				count++;
			}
		}
		Iterator iter1 = currentEquipmentMap.entrySet().iterator();
		int h = 0;
		while (iter1.hasNext()) 
		{
			java.util.Map.Entry entry = (java.util.Map.Entry)iter1.next();
			String key = (String)entry.getKey();
			if (!key.equals("EQU_POSITION_NUM") && !key.equals("order"))
			{
				setInfo[h] = (new StringBuilder(" info.")).append(key).append("=?").toString();
				h++;
			}
		}
		String sql = (new StringBuilder("merge into cz_equipment_info  info using(select ? as a from dual )t on(info.EQU_POSITION_NUM =t.a)  when matched then update set ")).append(join(setInfo, ",")).append(" ,info.EQUIPMENT_ATTACH_URL=?").append(" when  not matched then insert (").append("info.EQU_ID,").append(join(intoA, ",")).append(" ,info.EQUIPMENT_ATTACH_URL").append(")values(").append("?,").append(join(val, ",")).append(",?").append(")").toString();
		System.out.println((new StringBuilder("根据map动态生成的sql:")).append(sql).toString());

		Connection conn = DBUtil.getConn();
		PreparedStatement ps = conn.prepareStatement(sql);
		conn.setAutoCommit(false);
		for (int a = 3; a <= rowNum; a++)
		{
			row = sheet.getRow(a);
			ps.setString(1, getCellValue(row.getCell(3)));
			for (int b = 1; b <= currentEquipmentMap.size(); b++)
			{
				String cellValue = getCellValue(row.getCell(b));
				if (b <= 2)
					ps.setString(b + 1, cellValue);
				else
					ps.setString(b + 1, getCellValue(row.getCell(b + 1)));
				ps.setString(b + 1 + currentEquipmentMap.size(), getCellValue(row.getCell(b)));
			}

			ps.setString(currentEquipmentMap.size() + 1, UUID.randomUUID().toString());
			ps.addBatch();

		}
		ps.executeBatch();
		conn.commit();
		conn.setAutoCommit(true);
		DBUtil.closeConn();
		obj.put("msg", "0");
		return obj;
	}

	public String getCellValue(Cell cell)
	{
		String cellValue = "";
		if (cell == null)
			return cellValue;
		if (cell.getCellType() == 0)
		{
			if (HSSFDateUtil.isCellDateFormatted(cell))
			{
				java.util.Date d = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				cellValue = formater.format(d);
			} else
			{
				cellValue = String.valueOf(cell.getNumericCellValue());
			}
		} else
		if (cell.getCellType() == 1)
			cellValue = cell.getStringCellValue();
		return cellValue;
	}

	public static String join(Object o[], String flag)
	{
		StringBuffer str_buff = new StringBuffer();
		int i = 0;
		for (int len = o.length; i < len; i++)
		{
			str_buff.append(String.valueOf(o[i]));
			if (i < len - 1)
				str_buff.append(flag);
		}
		return str_buff.toString();
	}

	public static Map getConfigInfo()
	{
		Map excelHeadConfig = new HashMap();
		Map excelHeadDetailConfig = new LinkedHashMap();
		excelHeadDetailConfig.put("order", "序号");
		excelHeadDetailConfig.put("WEL_NAME", "装置列名");
		excelHeadDetailConfig.put("WEL_UNIT", "装置单元");
		excelHeadDetailConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDetailConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDetailConfig.put("EQU_NAME", "设备名称");
		excelHeadDetailConfig.put("MANAGE_TYPE", "器具类别");
		excelHeadDetailConfig.put("EQU_MODEL", "规格型号");
		excelHeadDetailConfig.put("MEARING_RANGE", "测量范围");
		excelHeadDetailConfig.put("MEASURE_ACC", "准确等级");
		excelHeadDetailConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadDetailConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadDetailConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadDetailConfig.put("CHECK_CYCLE", "检定周期");
		excelHeadDetailConfig.put("CHECK_TIME", "检定日期");
		excelHeadDetailConfig.put("NEXT_CHECK_TIME", "下次检定");
		excelHeadDetailConfig.put("REMARK1", "备注信息");
		Map excelHeadYCBConfig = new LinkedHashMap();
		excelHeadYCBConfig.put("order", "序号");
		excelHeadYCBConfig.put("WEL_NAME", "装置列名");
		excelHeadYCBConfig.put("WEL_UNIT", "装置单元");
		excelHeadYCBConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadYCBConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadYCBConfig.put("EQU_NAME", "设备名称");
		excelHeadYCBConfig.put("MANAGE_TYPE", "器具类别");
		excelHeadYCBConfig.put("EQU_MODEL", "规格型号");
		excelHeadYCBConfig.put("MEARING_RANGE", "测量范围");
		excelHeadYCBConfig.put("MEASURE_ACC", "精 度");
		excelHeadYCBConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadYCBConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadYCBConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadYCBConfig.put("CHECK_CYCLE", "检定周期");
		excelHeadYCBConfig.put("CHECK_TIME", "检定日期");
		excelHeadYCBConfig.put("NEXT_CHECK_TIME", "有效期");
		excelHeadYCBConfig.put("REMARK1", "备注信息");
		Map excelHeadWDJConfig = new LinkedHashMap();
		excelHeadWDJConfig.put("order", "序号");
		excelHeadWDJConfig.put("WEL_NAME", "装置列名");
		excelHeadWDJConfig.put("WEL_UNIT", "装置单元");
		excelHeadWDJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadWDJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadWDJConfig.put("EQU_NAME", "设备名称");
		excelHeadWDJConfig.put("EQU_MODEL", "规格型号");
		excelHeadWDJConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadWDJConfig.put("DEEP_LENGTH", "插深");
		excelHeadWDJConfig.put("INTER_SIZE", "接口尺寸");
		excelHeadWDJConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadWDJConfig.put("MEARING_RANGE", "测量范围");
		excelHeadWDJConfig.put("MEASURE_ACC", "精度");
		excelHeadWDJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadWDJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadWDJConfig.put("CHECK_CYCLE", "检定周期");
		excelHeadWDJConfig.put("CHECK_TIME", "检定日期");
		excelHeadWDJConfig.put("NEXT_CHECK_TIME", "有效期");
		excelHeadWDJConfig.put("REMARK1", "备注信息");
		Map excelHeadWBConfig = new LinkedHashMap();
		excelHeadWBConfig.put("order", "序号");
		excelHeadWBConfig.put("WEL_NAME", "装置列名");
		excelHeadWBConfig.put("WEL_UNIT", "装置单元");
		excelHeadWBConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadWBConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadWBConfig.put("EQU_NAME", "设备名称");
		excelHeadWBConfig.put("ORDER_NUM", "分度号");
		excelHeadWBConfig.put("EQU_MODEL", "规格型号");
		excelHeadWBConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadWBConfig.put("DEEP_LENGTH", "插深");
		excelHeadWBConfig.put("INTER_SIZE", "接口尺寸");
		excelHeadWBConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadWBConfig.put("MEARING_RANGE", "测量范围");
		excelHeadWBConfig.put("MEASURE_ACC", "精度");
		excelHeadWBConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadWBConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadWBConfig.put("CHECK_TIME", "测试日期");
		excelHeadWBConfig.put("CHECK_CYCLE", "周期");
		excelHeadWBConfig.put("REMARK1", "备注信息");
		Map excelHeadQDFConfig = new LinkedHashMap();
		excelHeadQDFConfig.put("order", "序号");
		excelHeadQDFConfig.put("WEL_NAME", "装置列名");
		excelHeadQDFConfig.put("WEL_UNIT", "装置单元");
		excelHeadQDFConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadQDFConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadQDFConfig.put("EQU_NAME", "设备名称");
		excelHeadQDFConfig.put("EQU_NAME", "设备名称");
		excelHeadQDFConfig.put("EQU_MODEL", "规格型号");
		excelHeadQDFConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadQDFConfig.put("MEDUIM_TYPE", "介质");
		excelHeadQDFConfig.put("FLA_SIZE", "法兰规格");
		excelHeadQDFConfig.put("ACTION_MODLE", "作用方式");
		excelHeadQDFConfig.put("HAVE_NOT", "有无手轮");
		excelHeadQDFConfig.put("ACTUAL_MODEL", "执行机构型号");
		excelHeadQDFConfig.put("VAVLE_TYPE", "电磁阀型号");
		excelHeadQDFConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadQDFConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadQDFConfig.put("REMARK1", "备注信息");
		Map excelHeadTJFConfig = new LinkedHashMap();
		excelHeadTJFConfig.put("order", "序号");
		excelHeadTJFConfig.put("WEL_NAME", "装置列名");
		excelHeadTJFConfig.put("WEL_UNIT", "装置单元");
		excelHeadTJFConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadTJFConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadTJFConfig.put("EQU_MODEL", "规格型号");
		excelHeadTJFConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadTJFConfig.put("MEDUIM_TYPE", "介质");
		excelHeadTJFConfig.put("MEARING_RANGE", "行程");
		excelHeadTJFConfig.put("CV", "CV值");
		excelHeadTJFConfig.put("FLA_SIZE", "法兰规格");
		excelHeadTJFConfig.put("ACTION_MODLE", "作用方式");
		excelHeadTJFConfig.put("HAVE_NOT", "有无手轮");
		excelHeadTJFConfig.put("ACTUAL_MODEL", "执行机构型号");
		excelHeadTJFConfig.put("GAS_SOURCE", "气源Mpa");
		excelHeadTJFConfig.put("POSITIONER", "定位器");
		excelHeadTJFConfig.put("VAVLE_TYPE", "电磁阀");
		excelHeadTJFConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadTJFConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadTJFConfig.put("REMARK1", "备注");
		Map excelHeadYWJConfig = new LinkedHashMap();
		excelHeadYWJConfig.put("order", "序号");
		excelHeadYWJConfig.put("WEL_NAME", "装置列名");
		excelHeadYWJConfig.put("WEL_UNIT", "装置单元");
		excelHeadYWJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadYWJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadYWJConfig.put("EQU_MODEL", "规格型号");
		excelHeadYWJConfig.put("DEEP_LENGTH", "中心距离");
		excelHeadYWJConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadYWJConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadYWJConfig.put("PROCE_LINK_TYPE", "过程连接尺寸");
		excelHeadYWJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadYWJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadYWJConfig.put("REMARK1", "备注");
		Map excelHeadLLJConfig = new LinkedHashMap();
		excelHeadLLJConfig.put("order", "序号");
		excelHeadLLJConfig.put("WEL_NAME", "装置列名");
		excelHeadLLJConfig.put("WEL_UNIT", "装置单元");
		excelHeadLLJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadLLJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadLLJConfig.put("EQU_NAME", "设备名称");
		excelHeadLLJConfig.put("规格型号", "EQU_MODEL");
		excelHeadLLJConfig.put("安装地点", "EQU_INSTALL_POSITION");
		excelHeadLLJConfig.put("介质", "MEDUIM_TYPE");
		excelHeadLLJConfig.put("流量(max)", "ACTUAL");
		excelHeadLLJConfig.put("流量(正常)", "FLUX");
		excelHeadLLJConfig.put("精 度", "MEASURE_ACC");
		excelHeadLLJConfig.put("测量范围", "MEARING_RANGE");
		excelHeadLLJConfig.put("过程安装方式", "PROCE_LINK_TYPE");
		excelHeadLLJConfig.put("编号", "SERIAL_NUM");
		excelHeadLLJConfig.put("生产厂家", "EQU_MANUFACTURER");
		excelHeadLLJConfig.put("生产日期", "EQU_PRODUC_DATE");
		excelHeadLLJConfig.put("备注", "REMARK1");
		Map excelHeadJLConfig = new LinkedHashMap();
		excelHeadJLConfig.put("order", "序号");
		excelHeadJLConfig.put("WEL_NAME", "装置列名");
		excelHeadJLConfig.put("WEL_UNIT", "装置单元");
		excelHeadJLConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadJLConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadJLConfig.put("EQU_NAME", "设备名称");
		excelHeadJLConfig.put("EQU_MODEL", "规格型号");
		excelHeadJLConfig.put("EQU_INSTALL_POSITION", "安装地点");
		excelHeadJLConfig.put("MEDUIM_TYPE", "测量介质");
		excelHeadJLConfig.put("MEARING_RANGE", "量程");
		excelHeadJLConfig.put("PRESSURE_RANGE", "压力MPa");
		excelHeadJLConfig.put("EQU_WORK_TEMP", "温度");
		excelHeadJLConfig.put("FLA_SIZE", "法兰规格");
		excelHeadJLConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadJLConfig.put("REMARK1", "备注");
		Map excelHeadFXYConfig = new LinkedHashMap();
		excelHeadFXYConfig.put("order", "序号");
		excelHeadFXYConfig.put("WEL_NAME", "装置列名");
		excelHeadFXYConfig.put("WEL_UNIT", "装置单元");
		excelHeadFXYConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadFXYConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadFXYConfig.put("EQU_NAME", "设备名称");
		excelHeadFXYConfig.put("规格型号", "EQU_MODEL");
		excelHeadFXYConfig.put("安装地点", "EQU_INSTALL_POSITION");
		excelHeadFXYConfig.put("测量介质", "MEDUIM_TYPE");
		excelHeadFXYConfig.put("量程", "MEARING_RANGE");
		excelHeadFXYConfig.put("精 度", "MEASURE_ACC");
		excelHeadFXYConfig.put("测量原理", "MEASURE_PRIN");
		excelHeadFXYConfig.put("生产厂家", "EQU_MANUFACTURER");
		excelHeadFXYConfig.put("出厂编号", "SERIAL_NUM");
		excelHeadFXYConfig.put("备注", "REMARK1");
		Map excelHeadZDTTConfig = new LinkedHashMap();
		excelHeadZDTTConfig.put("order", "序号");
		excelHeadZDTTConfig.put("WEL_NAME", "装置列名");
		excelHeadZDTTConfig.put("WEL_UNIT", "装置单元");
		excelHeadZDTTConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadZDTTConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadZDTTConfig.put("EQU_NAME", "设备名称");
		Map excelHeadDSConfig = new LinkedHashMap();
		excelHeadDSConfig.put("order", "序号");
		excelHeadDSConfig.put("WEL_NAME", "装置列名");
		excelHeadDSConfig.put("WEL_UNIT", "装置单元");
		excelHeadDSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadDSConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadDSConfig.put("EQU_NAME", "设备名称");
		excelHeadDSConfig.put("EQU_MODEL", "规格型号");
		excelHeadDSConfig.put("EQU_COMMISSION_DATE", "控制器MD");
		excelHeadDSConfig.put("MEDUIM_TYPE", "控制器MQ");
		excelHeadDSConfig.put("EQU_WORK_TEMP", "AI16");
		excelHeadDSConfig.put("EQU_LASTPERIODIC_DATE", "AI18-R");
		excelHeadDSConfig.put("EQU_PERIODIC_CYCLE", "AI08-R");
		excelHeadDSConfig.put("EQU_PERIODIC_WARNDAYS", "DI32");
		excelHeadDSConfig.put("MEARING_RANGE", "DO32");
		excelHeadDSConfig.put("PRESSURE_RANGE", "串口卡(个)");
		excelHeadDSConfig.put("MANAGE_TYPE", "串口卡(对)");
		excelHeadDSConfig.put("SERIAL_NUM", "SIS卡(对)");
		excelHeadDSConfig.put("CHECK_CYCLE", "24VDC/20A(对)");
		excelHeadDSConfig.put("CHECK_TIME", "24VDC/40A(对)");
		excelHeadDSConfig.put("NEXT_CHECK_TIME", "12VDC/15A(对)");
		excelHeadDSConfig.put("EXPERY_TIME", "中继器");
		excelHeadDSConfig.put("INTER_SIZE", "AI8");
		excelHeadDSConfig.put("MEASURE_ACC", "AI浪涌");
		excelHeadDSConfig.put("DEEP_LENGTH", "DI浪涌");
		excelHeadDSConfig.put("ORDER_NUM", "继电器");
		excelHeadDSConfig.put("REMARK1", "备注");
		Map excelHeadFGSConfig = new LinkedHashMap();
		excelHeadFGSConfig.put("order", "序号");
		excelHeadFGSConfig.put("WEL_NAME", "装置列名");
		excelHeadFGSConfig.put("WEL_UNIT", "装置单元");
		excelHeadFGSConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadFGSConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadFGSConfig.put("EQU_NAME", "设备名称");
		excelHeadFGSConfig.put("MEDUIM_TYPE", "控制器8851");
		excelHeadFGSConfig.put("EQU_WORK_TEMP", "模拟量卡件8810");
		excelHeadFGSConfig.put("EQU_LASTPERIODIC_DATE", "数字量卡件8811");
		excelHeadFGSConfig.put("EQU_PERIODIC_CYCLE", "电源881312VDC/5A");
		excelHeadFGSConfig.put("EQU_PERIODIC_WARNDAYS", "电源RM240-24VDC/40A");
		excelHeadFGSConfig.put("MEARING_RANGE", "电源RM120-24VDC/20A");
		excelHeadFGSConfig.put("REMARK1", "备注");
		Map excelHeadBJConfig = new LinkedHashMap();
		excelHeadBJConfig.put("order", "序号");
		excelHeadBJConfig.put("WEL_NAME", "装置列名");
		excelHeadBJConfig.put("WEL_UNIT", "装置单元");
		excelHeadBJConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadBJConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadBJConfig.put("EQU_NAME", "设备名称");
		excelHeadBJConfig.put("MANAGE_TYPE", "器具类别");
		excelHeadBJConfig.put("EQU_MODEL", "规格型号");
		excelHeadBJConfig.put("EQU_INSTALL_POSITION", "安装使用地点");
		excelHeadBJConfig.put("ACTUAL", "用途");
		excelHeadBJConfig.put("ACTION_MODLE", "安装方式");
		excelHeadBJConfig.put("MEASURE_ACC", "精 度");
		excelHeadBJConfig.put("MEARING_RANGE", "测量范围");
		excelHeadBJConfig.put("ELEC", "供电");
		excelHeadBJConfig.put("CV", "输出信号(mA)");
		excelHeadBJConfig.put("ORDER_NUM", "报警值");
		excelHeadBJConfig.put("EQU_MANUFACTURER", "生产厂家");
		excelHeadBJConfig.put("SERIAL_NUM", "出厂编号");
		excelHeadBJConfig.put("NEXT_CHECK_TIME", "有效期");
		excelHeadBJConfig.put("REMARK1", "备注");
		Map excelHeadQTConfig = new LinkedHashMap();
		excelHeadQTConfig.put("order", "序号");
		excelHeadQTConfig.put("WEL_NAME", "装置列名");
		excelHeadQTConfig.put("WEL_UNIT", "装置单元");
		excelHeadQTConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadQTConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadQTConfig.put("EQU_NAME", "设备名称");
		Map excelHeadPConfig = new LinkedHashMap();
		excelHeadPConfig.put("order", "序号");
		excelHeadPConfig.put("WEL_NAME", "装置列名");
		excelHeadPConfig.put("WEL_UNIT", "装置单元");
		excelHeadPConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadPConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadPConfig.put("EQU_NAME", "设备名称");
		excelHeadPConfig.put("安装位置", "EQU_INSTALL_POSITION");
		excelHeadPConfig.put("类别", "MANAGE_TYPE");
		excelHeadPConfig.put("规格型号", "EQU_MODEL");
		excelHeadPConfig.put("重量", "WEIGHT");
		excelHeadPConfig.put("扬程", "FLUX");
		excelHeadPConfig.put("排量", "COUNT");
		excelHeadPConfig.put("电压", "ELECTRIC_PRES");
		excelHeadPConfig.put("功率", "POWER_RATE");
		excelHeadPConfig.put("转速", "SPEED_RAT");
		excelHeadPConfig.put("介质", "MEDUIM_TYPE");
		excelHeadPConfig.put("泵壳", "CAPCITY");
		excelHeadPConfig.put("叶轮", "BEFORE_BEARING1");
		excelHeadPConfig.put("主轴", "BEFORE_BEARING2");
		excelHeadPConfig.put("制造单位", "EQU_MANUFACTURER");
		excelHeadPConfig.put("出厂编号", "SERIAL_NUM");
		excelHeadPConfig.put("投用年月", "EQU_COMMISSION_DATE");
		excelHeadPConfig.put("备注", "REMARK1");
		Map excelHeadKConfig = new LinkedHashMap();
		excelHeadKConfig.put("order", "序号");
		excelHeadKConfig.put("WEL_NAME", "装置列名");
		excelHeadKConfig.put("WEL_UNIT", "装置单元");
		excelHeadKConfig.put("EQU_POSITION_NUM", "设备位号");
		excelHeadKConfig.put("EQU_MEMO_ONE", "设备类别");
		excelHeadKConfig.put("EQU_NAME", "设备名称");
		excelHeadKConfig.put("安装位置", "EQU_INSTALL_POSITION");
		excelHeadKConfig.put("类别", "MANAGE_TYPE");
		excelHeadKConfig.put("规格型号", "EQU_MODEL");
		excelHeadKConfig.put("重量", "WEIGHT");
		excelHeadKConfig.put("出口风压", "WIND_PRESSURE");
		excelHeadKConfig.put("排量", "COUNT");
		excelHeadKConfig.put("性能转速", "SPEED_RAT");
		excelHeadKConfig.put("电压", "ELECTRIC_PRES");
		excelHeadKConfig.put("功率", "POWER_RATE");
		excelHeadKConfig.put("电机转速", "SPINDLE_SPEED");
		excelHeadKConfig.put("介质", "MEDUIM_TYPE");
		excelHeadKConfig.put("制造单位", "EQU_MANUFACTURER");
		excelHeadKConfig.put("出厂编号", "SERIAL_NUM");
		excelHeadKConfig.put("投用年月", "EQU_COMMISSION_DATE");
		excelHeadKConfig.put("备注", "REMARK1");
		excelHeadConfig.put("压力表", excelHeadDetailConfig);
		excelHeadConfig.put("压力差压变送器", excelHeadYCBConfig);
		excelHeadConfig.put("温度计", excelHeadWDJConfig);
		excelHeadConfig.put("温度变送器", excelHeadWBConfig);
		excelHeadConfig.put("气动切断阀", excelHeadQDFConfig);
		excelHeadConfig.put("气动调节阀", excelHeadTJFConfig);
		excelHeadConfig.put("液位计(含远程)", excelHeadYWJConfig);
		excelHeadConfig.put("流量", excelHeadLLJConfig);
		excelHeadConfig.put("节流装置", excelHeadJLConfig);
		excelHeadConfig.put("在线分析仪", excelHeadFXYConfig);
		excelHeadConfig.put("振动温度探头", excelHeadZDTTConfig);
		excelHeadConfig.put("DCS/SIS系统", excelHeadDSConfig);
		excelHeadConfig.put("FGS系统", excelHeadFGSConfig);
		excelHeadConfig.put("固定式报警仪", excelHeadBJConfig);
		excelHeadConfig.put("其他", excelHeadQTConfig);
		excelHeadConfig.put("P类", excelHeadPConfig);
		excelHeadConfig.put("K类", excelHeadKConfig);
		return excelHeadConfig;
	}
}
