package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.blog.operator.BlogOperator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BlogClassify extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BlogClassify() {
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
		this.doPost(request, response);
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
		
		response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        
		String id = request.getParameter("id");
		String classify = request.getParameter("classify");
		
		String book = "";
		if(classify.equals("note")){
			if(id.equals("0")){
				book = "JavaScript";
			}else if(id.equals("1")){
				book = "Java";
			}else if(id.equals("2")){
				book = "CN";
			}else if(id.equals("3")){
				book = "DS";
			}else if(id.equals("4")){
				book = "OS";
			}
		}
		
		if(classify.equals("blog")){
			if(id.equals("0")){
				book = "React";
			}else if(id.equals("1")){
				book = "Vue";
			}else if(id.equals("2")){
				book = "HTML&CSS";
			}else if(id.equals("3")){
				book = "Ubuntu";
			}
		}
		
		Blog blog = new Blog();
		
		JsonArray info = new JsonArray();/*最外层info数组*/
		JsonObject i = new JsonObject();/*次一层json对象*/
		
		JsonArray content = new JsonArray();/*第三层content数组*/
		JsonObject j = new JsonObject();/*最内层json对象*/
		
		response.setContentType("application/json");
		BlogOperator operator = new BlogOperator();
		List result = operator.getBookByDate(book);
		System.out.print(JSON.toJSONString(result));
		Blog FIRST = (Blog)result.get(0);
		
		Iterator iterator = result.iterator();
 		String compare = FIRST.getPublish().substring(0,7);

 		int count = 0;
		while(iterator.hasNext()){
			
			blog = (Blog) iterator.next();
			
			if(blog.getPublish().substring(0,7).equals(compare)){
			
				if(count == 0){
					i.addProperty("time", compare);
				}
				
				j.addProperty("id", blog.getBlog_id());
				j.addProperty("title", blog.getTitle());
				j.addProperty("publish", blog.getPublish());
				
				content.add(j);
				
				j = null;
				j = new JsonObject();
				
			}else{
				
				i.add("content",content);
				info.add(i);
				content = null;
				content = new JsonArray();
				
				i = null;
				i = new JsonObject();
				
				compare = blog.getPublish().substring(0,7);
				i.addProperty("time", compare);
				
				j.addProperty("id", blog.getBlog_id());
				j.addProperty("title", blog.getTitle());
				j.addProperty("publish", blog.getPublish());
				
				content.add(j);
				j = null;
				j = new JsonObject();
			}
			count++;
			
		}
		PrintWriter out = response.getWriter();
		out.write(info.toString());
		System.out.print(id);
		System.out.print(classify);
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
