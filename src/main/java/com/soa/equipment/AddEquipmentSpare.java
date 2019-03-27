/*    */ package com.soa.equipment;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;

/*    */ import javax.servlet.ServletException;
/*    */ import javax.servlet.http.HttpServlet;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;

/*    */ 
/*    */ import com.alibaba.fastjson.JSONObject;
/*    */ import com.soa.util.DataUtil;
/*    */ 
/*    */ public class AddEquipmentSpare extends HttpServlet
/*    */ {
/*    */   protected void service(HttpServletRequest req, HttpServletResponse resp)
/*    */     throws ServletException, IOException
/*    */   {
/* 18 */     req.setCharacterEncoding("utf-8");
/* 19 */     resp.setContentType("text/html;charset=utf-8");
/* 20 */     String MATERIAL_NAME = req.getParameter("MATERIAL_NAME");
/* 21 */     String MATERIAL_SPECIFICATIONS = req.getParameter("MATERIAL_SPECIFICATIONS");
/* 22 */     String UNIT = req.getParameter("UNIT");
/* 23 */     String REASONABLE_INVENTORY = req.getParameter("REASONABLE_INVENTORY");
/* 24 */     String ACTUAL_INVENTORY = req.getParameter("ACTUAL_INVENTORY");
/* 25 */     String SPARE_PARTS_TYPE = req.getParameter("SPARE_PARTS_TYPE");
/* 26 */     String REMARKTWO = req.getParameter("REMARKTWO");
/* 27 */     String REMARKONE = req.getParameter("REMARKONE");
/* 28 */     String MATERIAL_ID = UUID.randomUUID().toString();
/* 29 */     String sql = "insert into cz_equipment_spare (MATERIAL_ID,MATERIAL_NAME,MATERIAL_SPECIFICATIONS,UNIT,REASONABLE_INVENTORY,ACTUAL_INVENTORY,REMARKONE,SPARE_PARTS_TYPE,REMARKTWO) values('" + MATERIAL_ID + "','" + MATERIAL_NAME + "','" + MATERIAL_SPECIFICATIONS + "','" + UNIT + "','" + REASONABLE_INVENTORY + "','" + ACTUAL_INVENTORY + "','" + REMARKONE + "','" + SPARE_PARTS_TYPE + "','" + REMARKTWO + "') ";
/* 30 */     int i = DataUtil.doExecuteSql(sql);
/* 31 */     Map map = new HashMap();
/* 32 */     System.out.println(map);  
/* 33 */     if (i != 0) 
/* 34 */       map.put("msg", Integer.valueOf(0));
/*    */     else {
/* 36 */       map.put("msg", Integer.valueOf(1));
/*    */     }
/* 38 */     String result = new JSONObject(map).toString();
/* 39 */     resp.getWriter().write(result);
/*    */   }
/*    */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.AddEquipmentSpare
 * JD-Core Version:    0.6.2
 */