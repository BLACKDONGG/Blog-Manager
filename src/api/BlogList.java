package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.*;
import com.alibaba.fastjson.JSON;
import com.blog.operator.BlogOperator;

public class BlogList extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BlogList() {
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
		String privateKey = "000000";
		String publicKey = "000000";
		int[] pagestart = {0,9,18,27,36,45,54,63,72,81,90};
		String page = request.getParameter("currentPage");
		int  pagination = Integer.parseInt(page);	
		response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
		if(privateKey.equals(publicKey)){
			BlogOperator operator = new BlogOperator();
			List result = operator.get(pagestart[pagination-1],9);
			String json = JSON.toJSONString(result);
	        PrintWriter out = response.getWriter();
	        System.out.print(result);
	        out.write(json);
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
		this.doGet(request, response);
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
