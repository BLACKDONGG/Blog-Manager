package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.link.model.Linker;
import com.link.operator.LinkOperator;

public class ApplyLink extends HttpServlet {
	private String CODE_ILLEGAL = "0";
	private String CODE_ERROR = "1";
	private String CODE_SUCCESS = "2";
	private String CODE_EXIST = "3";
	/**
	 * Constructor of the object.
	 */
	public ApplyLink() {
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

		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(CODE_ILLEGAL));
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
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Calendar calendar = Calendar.getInstance();
		String name = request.getParameter("name");
		String email = request.getParameter("email").toLowerCase();
		String website = request.getParameter("website").toLowerCase();
		String avatar = request.getParameter("avatar");
		String publish = request.getParameter("publish");
		String avatarSRCPRE = "http:/www.ditto.ink/images/avatar/avatar";
		LinkOperator o = new LinkOperator();
		if(name.equals("")||email.equals("")||website.equals("")){
			out.write(JSON.toJSONString(CODE_ILLEGAL));
			out.flush();
			out.close();
		}else{
			JsonObject links = new JsonObject();
			LinkOperator operator = new LinkOperator();
			List websitelist = operator.getByWebsite(website);
			if(websitelist.size() > 0){
				links.add("result",new Gson().toJsonTree(o.getLink()));
				links.addProperty("CODE", CODE_EXIST);
				out.write(links.toString());
				out.flush();
				out.close();
			}else{
				Linker linker = new Linker();
				linker.setName(name);
				linker.setEmail(email);
				linker.setWebsite(website);
				linker.setAvatar(avatarSRCPRE+avatar+".jpg");
				linker.setPublish(publish);
				operator.addLink(linker);
				
				links.add("result",new Gson().toJsonTree(o.getLink()));
				links.addProperty("CODE", CODE_SUCCESS);
				out.write(links.toString());
				out.flush();
				out.close();
			}
			
		}
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
