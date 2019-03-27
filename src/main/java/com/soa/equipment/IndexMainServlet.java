/*     */ package com.soa.equipment;
/*     */ import java.io.IOException;

/*     */ import javax.management.RuntimeErrorException;
/*     */ import javax.servlet.RequestDispatcher;
/*     */ import javax.servlet.ServletException;
/*     */ import javax.servlet.http.HttpServlet;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
/*     */ import org.json.JSONArray;
/*     */ import org.json.JSONException;
/*     */ import org.json.JSONObject;

/*     */ 
/*     */ import com.soa.util.DataUtil;
/*     */ 
/*     */ public class IndexMainServlet extends HttpServlet
/*     */ {
/*     */   private static final long serialVersionUID = 1L;
/*     */ 
/*     */   protected void service(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/*  78 */     int type = 1;
/*  79 */     String typeSrc = request.getParameter("type");
/*  80 */     if (StringUtils.isNotBlank(typeSrc)) {
/*  81 */       type = Integer.parseInt(typeSrc);
/*     */     }
/*  83 */     switch (type)
/*     */     {
/*     */     case 1:
/*  86 */       index(request, response);
/*  87 */       break;
/*     */     case 2:
/*  89 */       main(request, response);
/*  90 */       break;
/*     */     case 3:
/*  92 */       column(request, response);
/*  93 */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void index(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 111 */     String userID = (String)request.getSession().getAttribute("userID");
/*     */ 
/* 114 */     if (StringUtils.isNotBlank(userID))
/*     */     {
/* 116 */       String sql = "";
/* 117 */       sql = sql + " SELECT distinct cpm.MENU_SORT,cpm.*";
/* 118 */       sql = sql + " FROM CZ_POWER_MENU cpm,CZ_POWER_ROLE_MENU cprm,CZ_POWER_USER_ROLE cpur";
/* 119 */       sql = sql + " WHERE cpm.MENU_ID=cprm.MENU_ID AND cprm.ROLE_ID=cpur.ROLE_ID";
/* 120 */       sql = sql + " AND cpm.MENU_TYPE='index'";
/* 121 */       sql = sql + " AND cpur.USER_NAME='" + userID + "'";
/* 122 */       sql = sql + " ORDER BY cpm.MENU_SORT ASC";
/*     */ 
/* 124 */       request.setAttribute("mapList", DataUtil.getDataListMap(sql));
/*     */     }
/*     */ 
/* 127 */     RequestDispatcher rd1 = request.getRequestDispatcher("index.jsp");
/* 128 */     rd1.forward(request, response);
/*     */   }
/*     */ 
/*     */   private void main(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 142 */     total(request, TOTAL_ENUM.TODO);
/*     */ 
/* 144 */     total(request, TOTAL_ENUM.PROBLEM);
/*     */ 
/* 146 */     total(request, TOTAL_ENUM.PROBLEM_WARN);
/*     */ 
/* 148 */     total(request, TOTAL_ENUM.WARN);
/*     */ 
/* 150 */     total(request, TOTAL_ENUM.TASK_RATE);
/*     */ 
/* 152 */     total(request, TOTAL_ENUM.OVERDUE);
/*     */ 
/* 155 */     total(request, TOTAL_ENUM.PROBLEMRECTIFYRATE);
/*     */ 
/* 158 */     energyConsum(request);
/*     */ 
/* 161 */     todo(request, 5);
/*     */ 
/* 165 */     notice(request, "'1','2'");
/*     */ 
/* 167 */     RequestDispatcher rd2 = request.getRequestDispatcher("main.jsp");
/* 168 */     rd2.forward(request, response);
/*     */   }
/*     */ 
/*     */   private void energyConsum(HttpServletRequest request)
/*     */   {
/* 177 */     String sql = "SELECT CAST((SUM(COMPRECONSUM)/1000) AS DECIMAL(20,2)) COMPRECONSUM, CAST((SUM(WATERCONSUM)/10000) AS DECIMAL(20,2)) WATERCONSUM, CAST((SUM(ELECTCONSUM)/10000) AS DECIMAL(20,2)) ELECTCONSUM, CAST((SUM(GASCONSUM)/10000) AS DECIMAL(20,2)) GASCONSUM, CAST((SUM(STEAMCONSUM)/10000) AS DECIMAL(20,2)) STEAMCONSUM FROM BI_FACTORY_CONSUMDAY WHERE TO_CHAR(RECORDDAY,'YYYY') = TO_CHAR(SYSDATE,'YYYY')";
/*     */ 
/* 185 */     JSONArray ja = DataUtil.getData(sql);
/*     */ 
/* 187 */     if (ja.length() <= 1)
/*     */       try {
/* 189 */         JSONObject jo = ja.getJSONObject(0);
/*     */ 
/* 191 */         request.setAttribute("total_COMPRE_CONSUM", jo.get("COMPRECONSUM"));
/*     */ 
/* 193 */         request.setAttribute("total_WATER_CONSUM", jo.get("WATERCONSUM"));
/*     */ 
/* 195 */         request.setAttribute("total_ELECT_CONSUM", jo.get("ELECTCONSUM"));
/*     */ 
/* 197 */         request.setAttribute("total_GAS_CONSUM", jo.get("GASCONSUM"));
/*     */ 
/* 199 */         request.setAttribute("total_STEAM_CONSUM", jo.get("STEAMCONSUM"));
/*     */       }
/*     */       catch (JSONException e) {
/* 202 */         e.printStackTrace();
/*     */       }
/*     */     else
/* 205 */       throw new RuntimeErrorException(null);
/*     */   }
/*     */ 
/*     */   private void total(HttpServletRequest request, TOTAL_ENUM todo)
/*     */   {
/* 219 */     Object total = Integer.valueOf(0);
/*     */ 
/* 221 */     String userID = (String)request.getSession().getAttribute("userID");
/*     */ 
/* 223 */     if (StringUtils.isNotBlank(userID))
/*     */     {
/* 225 */       String sql = "";
/* 226 */       switch (todo)
/*     */       {
/*     */       case OVERDUE:
/* 229 */         sql = sql + " select count(1) TOTAL from v_soabpm_task";
/* 230 */         sql = sql + " where TASK_INST_NAME != '闂闂幆'";
/* 231 */         sql = sql + " and task_state in ('1','2') and pro_inst_state in ('1','6')";
/* 232 */         sql = sql + " and instr('|'||task_inst_owner||'|'||TASK_INST_POTENTIAL_OWNER, '|" + userID + "|') > 0";
/* 233 */         total = Integer.valueOf(DataUtil.getTotal(sql, "TOTAL"));
/* 234 */         break;
/*     */       case PROBLEM:
/* 237 */         sql = sql + "SELECT COUNT(1) TOTAL FROM CZ_PROCESS_EQUIPMENT A,CZ_TASK_PROBLEM_REPORT B ";
/* 238 */         sql = sql + "WHERE A.T_PROBLEM_REP_ID = B.T_PROBLEM_REP_ID ";
/* 239 */         sql = sql + "AND (B.PROBLEMSTATE <> 'FINISHED' OR B.PROBLEMSTATE IS NULL)";
/* 240 */         total = Integer.valueOf(DataUtil.getTotal(sql, "TOTAL"));
/* 241 */         break;
/*     */       case PROBLEMRECTIFYRATE:
/* 243 */         sql = sql + " SELECT COUNT(1) TOTAL FROM CZ_TASK_PROBLEM_REPORT WHERE SP_ID='鏄�'";
/* 244 */         total = Integer.valueOf(DataUtil.getTotal(sql, "TOTAL"));
/* 245 */         break;
/*     */       case PROBLEM_WARN:
/* 248 */         sql = sql + " SELECT COUNT(1) TOTAL FROM CZ_NOTICE_WARN WHERE STATUS = 'FALSE' or STATUS is null ";
/* 249 */         total = Integer.valueOf(DataUtil.getTotal(sql, "TOTAL"));
/* 250 */         break;
/*     */       case TASK_RATE:
/* 252 */         sql = sql + " SELECT TO_CHAR((((SELECT count(TASK_STATE) FROM CZ_TASK_INSPECTION WHERE TASK_STATE='FINISHED')+(SELECT count(TASK_STATE) FROM CZ_TASK_ASSAY_SCHEME WHERE TASK_STATE='FINISHED')+(SELECT count(TASK_STATE) FROM CZ_TASK_ROUTINE WHERE TASK_STATE='FINISHED')+(SELECT count(STATE) FROM CZ_TASK_SUPERVISION_PROCESS WHERE STATE='FINISHED')+(SELECT count(STATE) FROM CZ_TASK_TEMPORARY WHERE STATE='FINISHED')";
/* 253 */         sql = sql + " )/(";
/* 254 */         sql = sql + " (SELECT count(TASK_STATE) FROM CZ_TASK_INSPECTION)+(SELECT count(TASK_STATE) FROM CZ_TASK_ASSAY_SCHEME)+(SELECT count(TASK_STATE) FROM CZ_TASK_ROUTINE)+(SELECT count(STATE) FROM CZ_TASK_SUPERVISION_PROCESS)+(SELECT count(STATE) FROM CZ_TASK_TEMPORARY)";
/* 255 */         sql = sql + " ))*100,'FM9999999990.00')||'%' ONEVAL";
/* 256 */         sql = sql + " FROM dual";
/* 257 */         total = DataUtil.getOneString(sql, "ONEVAL");
/* 258 */         break;
/*     */       case TODO:
/* 260 */         sql = sql + " SELECT COUNT(1) TOTAL FROM CZ_TASK_OVERDUE_VIEW";
/* 261 */         total = DataUtil.getOneString(sql, "TOTAL");
/* 262 */         break;
/*     */       case WARN:
/* 264 */         sql = sql + "SELECT TO_CHAR(((SELECT COUNT(1)  FROM CZ_TASK_PROBLEM_REPORT WHERE PROBLEMSTATE = 'FINISHED')/(SELECT COUNT(1)  FROM CZ_TASK_PROBLEM_REPORT)) * 100 ,'FM9999999990.00') || '%' RATE FROM DUAL";
/*     */ 
/* 267 */         total = DataUtil.getOneString(sql, "RATE");
/* 268 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 273 */     request.setAttribute("total_" + String.valueOf(todo), total);
/*     */   }
/*     */ 
/*     */   private void todo(HttpServletRequest request, int rownum)
/*     */   {
/* 286 */     String userID = (String)request.getSession().getAttribute("userID");
/*     */ 
/* 289 */     if (StringUtils.isNotBlank(userID))
/*     */     {
/* 291 */       String sql = "select t.* from ( ";
/* 292 */       sql = sql + " select x.TASK_INST_NAME,x.STARTER,x.ARRIVE_DATE, x.PRO_INST_ID,x.TASK_INST_POTENTIAL_OWNER,x.TASK_INST_ID,";
/* 293 */       sql = sql + " (case when x.PRO_INST_URGENCY = '1' then '绱ф��' ";
/* 294 */       sql = sql + " when x.PRO_INST_URGENCY = '2' then '涓�鑸�' ";
/* 295 */       sql = sql + " else x.PRO_INST_URGENCY end) as TASK_STATE ";
/* 296 */       sql = sql + " from (select * from (";
/* 297 */       sql = sql + " select * from v_soabpm_task where task_state = '1' or task_state = '2' )t ";
/* 298 */       sql = sql + " where t.pro_inst_state = '1' or  t.pro_inst_state = '6')x ";
/* 299 */       sql = sql + " where (x.task_inst_owner = '" + userID + "' ";
/* 300 */       sql = sql + " or x.TASK_INST_POTENTIAL_OWNER like '%|" + userID + "|%') order by arrive_date desc)t ";
/* 301 */       sql = sql + " where rownum <= " + rownum;
/*     */ 
/* 303 */       request.setAttribute("todoListMap", DataUtil.getDataListMap(sql));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void notice(HttpServletRequest request, String type)
/*     */   {
/* 323 */     String sql = "";
/* 324 */     sql = sql + " SELECT *  FROM CZ_TASK_OVERDUE_VIEW";
/* 325 */     sql = sql + " WHERE rownum<21";
/* 326 */     sql = sql + " ORDER BY OVERTIME DESC";
/*     */ 
/* 328 */     request.setAttribute("noticeListMap", DataUtil.getDataListMap(sql));
/*     */   }
/*     */ 
/*     */   private void column(HttpServletRequest request, HttpServletResponse response)
/*     */     throws ServletException, IOException
/*     */   {
/* 341 */     request.setAttribute("columnType", request.getParameter("column"));
/*     */ 
/* 343 */     RequestDispatcher rd2 = request.getRequestDispatcher("column/column.jsp");
/* 344 */     rd2.forward(request, response);
/*     */   }
/*     */ 
/*     */   private static enum TOTAL_ENUM
/*     */   {
/*  36 */     TODO, 
/*     */ 
/*  40 */     PROBLEM, 
/*     */ 
/*  44 */     PROBLEM_WARN, 
/*     */ 
/*  48 */     WARN, 
/*     */ 
/*  52 */     TASK_RATE, 
/*     */ 
/*  56 */     OVERDUE, 
/*     */ 
/*  60 */     PROBLEMRECTIFYRATE;
/*     */   }
/*     */ }

/* Location:           C:\Users\asus\Desktop\jhc\CZ_PIOTMS\WEB-INF\classes\
 * Qualified Name:     com.soa.CZ_PIOTMS.server.IndexMainServlet
 * JD-Core Version:    0.6.2
 */