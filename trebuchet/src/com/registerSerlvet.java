package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

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
			
			Map<String, Object> checkMap;
			try {
				checkMap = SecrecyUtil.register(playerCode, name, password, email);
				log.info("用户注册,用户"+playerCode+"/"+name+">>注册完成");
				HttpSession session = request.getSession();
				session.setAttribute("player", checkMap.get("player"));
				PrintWriter out = response.getWriter();
				out.write("{\"forward\":\"./home.jsp\"}");
				response.setContentType("text/json; charset=UTF-8");
			} catch (SQLException e) {
				log.debug("System has error:"+e.getMessage());
				response.setContentType("text/json; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write("{\"errorMsg\":\"注册时,系统错误\"}");
				out.flush();
			}
		}
	}

}
