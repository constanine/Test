package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import DB.DBUtil;

import com.alibaba.fastjson.JSON;

import functions.CommonUtil;
import secrecy.MD5Util;
import secrecy.SecrecyUtil;

/**
 * Servlet implementation class registerSerlvet
 */

public class registerSerlvet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = Logger.getLogger(this.getClass());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerSerlvet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String type = request.getParameter("type");
		if("create".equals(type)){
			String playerCode = request.getParameter("r_playerCode");
			String password = request.getParameter("r_password");
			String email = request.getParameter("r_email");
			String name = request.getParameter("r_name");
			password = MD5Util.MD5(password);
			log.info("用户注册,用户"+playerCode+"/"+name+">>验证");
			
			Map<String,Object> result= new HashMap<String,Object>();
			try {
				boolean canDoNext = true;
				if(!SecrecyUtil.isNameValid(name)){
					result.put("result", "failure");
					result.put("errorMsg", "账昵不规则");		
					canDoNext = false;
				}
				if(!SecrecyUtil.registerCheckPlayerCode(playerCode)){
					result.put("result", "failure");
					result.put("errorMsg", "账号已存在");		
					canDoNext = false;
				}
				if(! SecrecyUtil.registerCheckName(name) && canDoNext){
					result.put("result", "failure");
					result.put("errorMsg", "昵称已存在");
				}
				if(canDoNext){
					Map<String, Object> playInfo = SecrecyUtil.register(playerCode, name, password, email);
					log.info("用户注册,用户"+playerCode+"/"+name+">>注册完成");
					HttpSession session = request.getSession();
					session.setAttribute("player", playInfo.get("player"));
					result.put("result", "sucess");
					result.put("forward", "./home.jsp");
				}
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				String json = JSON.toJSONString(result);
				out.write(json);
			} catch (SQLException e) {
				CommonUtil.LogError(log,e);
				result.put("errorMsg","注册时,系统错误");
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				String json = JSON.toJSONString(result);
				out.write(json);
				out.flush();
			}
		}else if("checkCode".equals(type)){
			String playerCode = request.getParameter("r_playerCode");
			Map<String,Object> result= new HashMap<String,Object>();
			try {
				boolean canNext = SecrecyUtil.registerCheckPlayerCode(playerCode);				
				PrintWriter out = response.getWriter();
				if(canNext){
					result.put("canDoNetx","sucess");	
				}else{
					result.put("canDoNetx","failure");	
				}
				response.setContentType("text/json; charset=UTF-8");
				String json = JSON.toJSONString(result);
				out.write(json);
			} catch (SQLException e) {
				CommonUtil.LogError(log,e);
				result.put("errorMsg","注册时,系统错误");
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				String json = JSON.toJSONString(result);
				out.write(json);
				out.flush();
			}
		}else if("checkName".equals(type)){
			String name = request.getParameter("r_name");
			Map<String,Object> result= new HashMap<String,Object>();
			try {
				boolean canNext = SecrecyUtil.registerCheckName(name);				
				PrintWriter out = response.getWriter();
				if(canNext){
					result.put("canDoNetx","sucess");					
				}else{
					result.put("canDoNetx","failure");
				}
				response.setContentType("text/json; charset=UTF-8");
				String json = JSON.toJSONString(result);
				out.write(json);
			} catch (SQLException e) {
				CommonUtil.LogError(log,e);
				result.put("errorMsg","注册时,系统错误");
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				String json = JSON.toJSONString(result);
				out.write(json);
				out.flush();
			}
		}
	}
	
	protected void destory(){
		DBUtil.finishSqlResult();
	}
}
