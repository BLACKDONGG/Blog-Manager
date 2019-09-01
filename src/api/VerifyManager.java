package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.manager.model.Manager;
import com.manager.operator.ManagerOperator;
import com.util.md5.MD5;

public class VerifyManager extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public VerifyManager() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/josn");
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String publicKey = request.getParameter("publicKey");
		String code = "0";
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("WEID") == null){
			ManagerOperator operator = new ManagerOperator();
			List result = operator.getUsername(username);
			if(result.size() <= 0){
				//Ĭ��
				code = "0";
			}else{
				Manager manager = (Manager)result.get(0);
				MD5 MD = new MD5();
				String encodePassword = MD.encryptByMD5(manager.getPassword()+publicKey);
				session.setAttribute("WEID", session.getId());
				if(encodePassword.equals(password)){
					//�ɹ�
					code = "2";
				}else{
					//ʧ��
					code = "1";
				}
			}
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
		}else{
			if(session.getAttribute("WEID").equals(session.getId())){
				code = "2";
				System.out.print(session.getAttribute("WEID"));
				System.out.print("----------------------------------------");
				System.out.print(session.getId());
			}
		}
		
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(code));
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
