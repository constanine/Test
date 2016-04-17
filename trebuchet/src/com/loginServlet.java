package com;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import secrecy.MD5Util;
import secrecy.SecrecyUtil;

public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(this.getClass());
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPut(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String playerCode = request.getParameter("playerCode");
		String password = request.getParameter("password");
		password = MD5Util.MD5(password);
		Map<String, Object> checkMap;
		try {
			checkMap = SecrecyUtil.login(playerCode, password);
			if(checkMap.get("result").equals("sucess")){
				Date date = new Date();
				DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
				String cutTime = df.format(date);
				log.info(cutTime+",玩家:"+playerCode+",登入");
				HttpSession session = request.getSession();
				session.setAttribute("player", checkMap.get("player"));
				request.getRequestDispatcher("/home.jsp").forward(request, response);
			}else{
				request.setAttribute("errorMsg", checkMap.get("error"));
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			log.debug("System has error:"+e.getMessage());
			request.setAttribute("errorMsg", "登入时,系统错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			
		}
		
		
	}

}
