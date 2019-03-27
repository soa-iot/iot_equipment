/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;

/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ 
/*    */ import com.alibaba.fastjson.JSONObject;
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class ChangeEquipmentSpareServlet extends HttpServlet
/*    */ {
/*    */   private static final long serialVersionUID = 1L;
/*    */ 
/*    */   protected void service(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws UnsupportedEncodingException
/*    */   {
/* 20 */     req.setCharacterEncoding("utf-8");
/* 21 */     resp.setContentType("text/html;charset=utf-8");
/* 22 */     String SPARE_ID = req.getParameter("SPARE_ID");
/* 23 */     String MATERIAL_NAME = req.getParameter("MATERIAL_NAME");
/* 24 */     String MATERIAL_SPECIFICATIONS = req.getParameter("MATERIAL_SPECIFICATIONS");
/* 25 */     String UNIT = req.getParameter("UNIT");
/* 26 */     String REASONABLE_INVENTORY = req.getParameter("REASONABLE_INVENTORY");
/* 27 */     String ACTUAL_INVENTORY = req.getParameter("ACTUAL_INVENTORY");
/* 28 */     String REMARKONE = req.getParameter("REMARKONE");
/* 29 */     String SPARE_PARTS_TYPE = req.getParameter("SPARE_PARTS_TYPE");
/* 30 */     String REMARKTWO = req.getParameter("REMARKTWO");
/* 31 */     String sql = "update cz_equipment_spare set MATERIAL_NAME='" + MATERIAL_NAME + "',MATERIAL_SPECIFICATIONS='" + MATERIAL_SPECIFICATIONS + "',UNIT='" + UNIT + "'," + 
/* 32 */       " REASONABLE_INVENTORY='" + REASONABLE_INVENTORY + "', ACTUAL_INVENTORY='" + ACTUAL_INVENTORY + "',REMARKONE='" + REMARKONE + "' where SPARE_ID='" + SPARE_ID + "'";
/* 33 */     int i = DataUtil.doExecuteSql(sql);
/* 34 */     Map map = new HashMap();
/* 35 */     System.out.println("修改库存的sql:" + sql);
/* 36 */     if (i > 0)
/* 37 */       map.put("msg", Integer.valueOf(0));
/*    */     else
/* 39 */       map.put("msg", Integer.valueOf(1));
/*    */     try
/*    */     {
/* 42 */       String result = new JSONObject(map).toString();
/* 43 */       resp.getWriter().write(result);
/*    */     }
/*    */     catch (IOException e) {
/* 46 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.ChangeEquipmentSpareServlet
 * JD-Core Version:    0.6.2
 */