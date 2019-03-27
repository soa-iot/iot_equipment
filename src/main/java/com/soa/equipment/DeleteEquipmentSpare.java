/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;

/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ 
/*    */ import com.alibaba.fastjson.JSONObject;
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class DeleteEquipmentSpare extends HttpServlet
/*    */ {
/*    */   protected void service(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 18 */     String MATERIAL_ID = req.getParameter("MATERIAL_ID");
/*    */ 
/* 20 */     String sql = "delete  from cz_equipment_spare where SPARE_ID='" + MATERIAL_ID + "'";
/* 21 */     int i = DataUtil.doExecuteSql(sql);
/* 22 */     System.out.println("删除北平北京的sql�?" + sql);
/* 23 */     Map map = new HashMap();
/* 24 */     System.out.println(map);
/* 25 */     if (i != 0)
/* 26 */       map.put("msg", Integer.valueOf(0));
/*    */     else {
/* 28 */       map.put("msg", Integer.valueOf(1));
/*    */     }
/* 30 */     String result = new JSONObject(map).toString();
/* 31 */     resp.getWriter().write(result);
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.DeleteEquipmentSpare
 * JD-Core Version:    0.6.2
 */