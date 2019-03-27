package com.soa.equipment;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.soa.util.DataUtil;

public class GetEquipmentSpareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		JSONArray data = new JSONArray();
		JSONObject json=new JSONObject();
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String EQU_POSITION_NUM=request.getParameter("EQU_POSITION_NUM");
		String EQU_MEMO_ONE= request.getParameter("EQU_MEMO_ONE");
		int page=Integer.parseInt(request.getParameter("page"));
		int rows=Integer.parseInt(request.getParameter("rows"));
		String rowSql="select * from  (select rownum rn,files.*  from cz_equipment_spare files where  REMARKTWO= '"+EQU_POSITION_NUM+"'"+"or SPARE_PARTS_TYPE='"+EQU_MEMO_ONE+"'and rownum<"+page*rows+")a WHERE rn>="+(page-1)*rows;
		String totalSql="select COUNT(*) TOTAL  from cz_equipment_spare files where  REMARKTWO= '"+EQU_POSITION_NUM+"'"+"or SPARE_PARTS_TYPE='"+EQU_MEMO_ONE+"'";
		System.out.println("查询总数："+rowSql);
		data = DataUtil.getData(rowSql);
		
		try {
			json.put("total", DataUtil.getTotal(totalSql, "TOTAL"));
			json.put("rows", data);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
