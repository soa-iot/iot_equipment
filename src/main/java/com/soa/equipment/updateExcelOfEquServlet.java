// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ExportExcelServlet.java

package com.soa.equipment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.soa.util.DataUtil;
import com.soa.util.EquipmentInfo;
import com.soa.util.Im_ExportExcel;

// Referenced classes of package com.soa.CZ_PIOTMS.server:
//			QueryProblemInfoServlet

public class updateExcelOfEquServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	private String excelContents[][];

	public updateExcelOfEquServlet()
	{
	}

	protected void service(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException
	{
		req.setCharacterEncoding("utf-8");
		res.setHeader("Connection", "close");
		List<EquipmentInfo> lists=new ArrayList<EquipmentInfo>();
		
		String EQU_MEMO_ONE = req.getParameter("EQU_MEMO_ONE");
		System.out.println("设备导入EQU_MEMO_ONE:" + EQU_MEMO_ONE );
		
		try {			
			FileItemFactory factory = new DiskFileItemFactory();			// 文件上传核心工具类			
			ServletFileUpload upload = new ServletFileUpload(factory);			
			upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件大小限制		
			upload.setSizeMax(50 * 1024 * 1024); // 总文件大小限制			
			upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理 		
			if (ServletFileUpload.isMultipartContent(req)) {			
				List<FileItem> list = upload.parseRequest(req);				// 遍历				
				for (FileItem item : list) {					
					if (!item.isFormField()) {						
						lists =  Im_ExportExcel.importExcel( item.getInputStream(), EQU_MEMO_ONE ) ;				
					}				
				}			
			}		
		} catch (Exception e) {		
			e.printStackTrace();		
		}

		List<String> sqls=new ArrayList<String>();
		for(int i=0;i<lists.size();i++) {
			EquipmentInfo equ=lists.get(i);
			String EQU_POSITION_NUM = equ.getEQU_POSITION_NUM().trim();
			String updateSql=" update CZ_EQUIPMENT_INFO "
					+ "set EQU_MEMO_ONE=' " + equ.getEQU_MEMO_ONE().trim()
					+ "',  EQU_NAME='" + equ.getEQU_NAME().trim() 
					+ "',  MANAGE_TYPE='" + equ.getMANAGE_TYPE().trim()
					+ "',  EQU_MODEL='" + equ.getEQU_MODEL().trim() 
					+ "',  MEARING_RANGE='" + equ.getMEARING_RANGE().trim() 
					+ "',  MEASURE_ACC='" + equ.getMEASURE_ACC().trim() 
					+ "',  EQU_INSTALL_POSITION='" + equ.getEQU_INSTALL_POSITION().trim() 
					+ "',  EQU_MANUFACTURER='" + equ.getEQU_MANUFACTURER().trim() 
					+ "',  SERIAL_NUM='" + equ.getSERIAL_NUM().trim() 
					+ "',  CHECK_CYCLE='" + equ.getCHECK_CYCLE().trim() 
					+ "',  CHECK_TIME='" + equ.getCHECK_TIME().trim() 
					+ "',  NEXT_CHECK_TIME='" + equ.getNEXT_CHECK_TIME().trim() 
					+ "',  REMARK1='" + equ.getREMARK1().trim() 
					+ "',  EQUIPMENT_ATTACH_URL='" + equ.getEQUIPMENT_ATTACH_URL().trim() 
					+ "'  where EQU_POSITION_NUM= '" + equ.getEQU_POSITION_NUM().trim() + "'";
			String insertSql=" insert into CZ_EQUIPMENT_INFO "
					+ " ( EQU_ID,EQU_POSITION_NUM,EQU_MEMO_ONE,EQU_NAME,MANAGE_TYPE, "
					+ "   EQU_MODEL,MEARING_RANGE,MEASURE_ACC,EQU_INSTALL_POSITION,EQU_MANUFACTURER,  "
					+ "	  SERIAL_NUM,CHECK_CYCLE,CHECK_TIME,NEXT_CHECK_TIME,REMARK1,EQUIPMENT_ATTACH_URL,"
					+ "   SECONDCLASS_EQUIPMENT) values "
					+ " ('" + equ.getEQU_ID().trim() + "','" + equ.getEQU_POSITION_NUM().trim()+ "','" + 
					 		  equ.getEQU_MEMO_ONE().trim() + "','" + equ.getEQU_NAME().trim() + "','" + 
					 		  equ.getMANAGE_TYPE().trim() + "','" + equ.getEQU_MODEL().trim() + "','" + 
					 		  equ.getMEARING_RANGE().trim() + "','" + equ.getMEASURE_ACC().trim() + "','" + 
					 		  equ.getEQU_INSTALL_POSITION().trim() + "','" + equ.getEQU_MANUFACTURER().trim() + "','" + 
					 		  equ.getSERIAL_NUM().trim() + "','" + equ.getCHECK_CYCLE().trim() + "','" + 
					 		  equ.getCHECK_TIME().trim() + "','" + equ.getNEXT_CHECK_TIME().trim() + "','" + 
					 		  equ.getREMARK1().trim().trim() + "','仪表设备')";
			String sql = 
					" merge into CZ_EQUIPMENT_INFO d " +
					" using (select * from CZ_EQUIPMENT_INFO ) s " + 
					//" where EQU_POSITION_NUM = '" + EQU_POSITION_NUM + "') s"  +
					" on (s.EQU_POSITION_NUM = '" + EQU_POSITION_NUM + "')" +
					" when matched then " + updateSql +
					" when not matched then " + insertSql ;
			System.out.println("sql:" + sql);
		    sqls.add( sql );
		} 
		DataUtil.doExecuteSqlBatch(sqls);
		//res.sendRedirect("http://10.89.90.118:10239/CZ_PIOTMS/equipmentManage.html");
		res.sendRedirect("equipmentManage.html");
	}
}