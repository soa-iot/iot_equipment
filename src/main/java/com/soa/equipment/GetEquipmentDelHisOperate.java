
    /**  
    * @Title: GetEquipmentHisOperate.java
    * @Package com.soa.CZ_PIOTMS.server
    * @Description: TODO(用一句话描述该文件做什么)
    * @author yanghua
    * @date 2019年3月5日
    * @version V1.0  
    */
    
package com.soa.equipment;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.soa.util.DataUtil;

/**
    * @ClassName: GetEquipmentHisOperate
    * @Description: 获取设备台账删除操作信息
    * @author 朱刚
    * @date 2019年3月5日
    *
    */

public class GetEquipmentDelHisOperate extends HttpServlet {
	private static final long serialVersionUID = 16L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		request.setCharacterEncoding( "utf-8" );
		response.setContentType( "text/html;charset=utf-8" );
		
		//返回值
		JSONObject json = new JSONObject();
		
		String pageNumStr = request.getParameter( "page" );
		String pageSizeStr = request.getParameter( "limit" );
		
		String time = request.getParameter( "time" );
		String positionNum = request.getParameter( "positionNum" );
		
		if( pageNumStr == null || pageSizeStr == null   ) {
			try {
				json.put( "state" , 1 );
				json.put( "messge" , "查询历史删除数据失败，分页查询参数为空" );
				json.put( "data" , "" );
			} catch ( Exception e) {
				e.printStackTrace();
				try {
					json.put( "state" , 1 );
					json.put( "messge" , "查询历史删除数据失败，分页查询参数为空" );
					json.put( "count" , 0 );
					json.put( "data" , "" );
				} catch ( Exception e1) {
					e1.printStackTrace();
				}
				response.getWriter().write( json.toString() );
			}
			response.getWriter().write( json.toString() );
		}	
		
		int pageNum = Integer.parseInt( pageNumStr );
		int pageSize = Integer.parseInt( pageSizeStr );
			
		//参数验证
		String condition = "";
		if( StringUtils.isNotBlank( time ) && StringUtils.isNotBlank( positionNum ) ) {
			condition = condition + " and to_char(OPERATEDATE,'YYYY-MM-DD') like '" + time.trim() +
					"' and EQU_POSITION_NUM like '" + positionNum.trim() + "'";
		}else if( StringUtils.isNotBlank( time ) && !StringUtils.isNotBlank( positionNum )){
			condition = "  and to_char(OPERATEDATE,'YYYY-MM-DD') like  '" + time.trim() + "'";;
		}else if( !StringUtils.isNotBlank( time ) && StringUtils.isNotBlank( positionNum )){
			condition = "  and EQU_POSITION_NUM like '" + positionNum.trim() + "'";;
		}else {
			condition = "";
		}	
		System.out.println(condition);
		
		//获取数据库数据
		String sql = " select * from ( "
				+ "		   select i.*,Rownum rn from ( "
				+ " 		   select * from IOT_EQUIPMENT_OPERATE_RECODE where type='delete' " + condition + " order by OPERATEDATE  "
				+ " 	  )  i where Rownum <= " + pageNum*pageSize
				+ " 	) where rn >= " + ( pageNum - 1 )*pageSize;
		System.out.println( sql );
		JSONArray array = DataUtil.getData(sql);
		ArrayList<JSONObject> lists = new ArrayList<JSONObject>();
		for( int i = 0; i < array.length(); i++ ) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = array.getJSONObject(i);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if( jsonObject != null ) {
				lists.add( jsonObject );
			}					
		}
		System.out.println( lists.toString() );
		
		if( lists.isEmpty()) {
			try {
				json.put( "state" , 1 );
				json.put( "messge" , "获取设备操作历史信息失败" );
				json.put( "data" , "" );
				json.put( "count" , 0 );
			} catch (Exception e) {
				e.printStackTrace();
				try {
					json.put( "state" , 1 );
					json.put( "messge" , "获取设备操作历史信息失败" );
					json.put( "data" , "" );
					json.put( "count" , 0 );
				} catch (Exception e1) {
					e1.printStackTrace();
					response.getWriter().write( "未知错误" );
				}
				response.getWriter().write( json.toString() );
			}
			response.getWriter().write( json.toString() );
		}else {
			try {
				json.put( "state" , 0 );
				json.put( "messge" , "获取设备操作历史信息成功" );
				json.put( "count" , lists.size() );
				json.put( "data" , lists );
			} catch (Exception e) {
				e.printStackTrace();
				try {
					json.put( "state" , 0 );
					json.put( "messge" , "获取设备操作历史信息成功" );
					json.put( "count" , lists.size() );
					json.put( "data" , lists );
				} catch (JSONException e1) {
					e1.printStackTrace();
					response.getWriter().write( "未知错误" );
				}
				response.getWriter().write( json.toString() );
			}
			response.getWriter().write( json.toString() );
		}
	}
}
