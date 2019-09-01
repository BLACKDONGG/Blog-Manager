package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.link.model.Linker;
import com.link.operator.LinkOperator;

public class GetLinkList extends HttpServlet {
	private String CODE_ILLEGAL = "0"; //非法请求
	private String CODE_ERROR = "1";	//服务器错误
	private String CODE_SUCCESS = "2";	//请求成功
	/**
	 * Constructor of the object.
	 */
	public GetLinkList() {
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		JsonObject RESULT = new JsonObject();
		JsonObject CODE = new JsonObject();
		if(true){
			LinkOperator operator = new LinkOperator();
			JsonArray results = new JsonArray();
			JsonObject item = null;
			Linker linker = null;
			List result = operator.getLink();
			Iterator iterator = result.iterator();
			while(iterator.hasNext()){
				linker = (Linker)iterator.next();
				item = new JsonObject();
				
				item.addProperty("avatar", linker.getAvatar());
				item.addProperty("name", linker.getName());
				item.addProperty("email", linker.getEmail());
				item.addProperty("website", linker.getWebsite());
			
				results.add(item);
				
				item = null;
			}
			RESULT.add("results", results);
			results = null;
			item = null;
			out.write(RESULT.toString());
			out.flush();
			out.close();
		}else{
			RESULT.addProperty("CODE", CODE_ILLEGAL);
			out.write(RESULT.toString());
			out.flush();
			out.close();
		}
		
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
