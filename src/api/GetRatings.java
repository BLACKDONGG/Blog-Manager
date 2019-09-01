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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.observer.model.Observer;
import com.observer.operator.ObserverOperator;

public class GetRatings extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetRatings() {
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
		response.setContentType("application/json");
		String blog_id = request.getParameter("blog_id");
		System.out.print(blog_id);
		if(blog_id == null || blog_id.equals("")){
			return;
		}else{
			int i = Integer.parseInt(blog_id);
			ObserverOperator operator = new ObserverOperator();
			List result = operator.getByBlogID(i);
			Observer observer = new Observer();
			Observer pobserver = new Observer();
			Observer cobserver = new Observer();
			int pid = 0;
			int id = 0;
			//创建存储comment的json数组
			JsonArray comment = new JsonArray();
			//创建临时存储的json对象
			JsonObject item = new JsonObject();
			//创建子json数组
			JsonArray children = new JsonArray();
			if(result.size()!=0){
				Iterator itResult = result.iterator();
				while(itResult.hasNext()){
					observer = (Observer) itResult.next();
					item = new JsonObject();
					if(String.valueOf(observer.getPid()).equals("-1")){
						item.addProperty("id", observer.getId());
						item.addProperty("pid", observer.getPid());
						item.addProperty("blog_id", observer.getBlog_id());
						item.addProperty("name", observer.getName());
						item.addProperty("email", observer.getEmail());
						item.addProperty("website", observer.getWebsite());
						item.addProperty("words", observer.getWords());
						item.addProperty("publish", observer.getPublish());
						item.addProperty("avatar", observer.getAvatar());
						comment.add(item);
						itResult.remove();
						item = null;
					}else{
						item = null;
						continue;
					}
				}
				
				item = null;
				Gson gson = new Gson();
				
				List parentlist = gson.fromJson(comment, new TypeToken<List<Observer>>(){}.getType());
				List childlist = result;
//				System.out.print(JSON.toJSONString(childlist));
				int count = 0;
				for(Object pobject:parentlist){
					pobserver = (Observer) pobject;
					id = pobserver.getId();
					for(Object cobject:childlist){
						cobserver = (Observer) cobject;
						pid = cobserver.getPid();
						if(pid == id){
							item = new JsonObject();
							item.addProperty("id", cobserver.getId());
							item.addProperty("pid", cobserver.getPid());
							item.addProperty("blog_id", cobserver.getBlog_id());
							item.addProperty("name", cobserver.getName());
							item.addProperty("email", cobserver.getEmail());
							item.addProperty("website", cobserver.getWebsite());
							item.addProperty("words", cobserver.getWords());
							item.addProperty("publish", cobserver.getPublish());
							item.addProperty("avatar", cobserver.getAvatar());
							children.add(item);
							item = null;
						}else{
							continue;
						}
					}
					comment.get(count).getAsJsonObject().add("children", children);
					children = null;
					children = new JsonArray();
					count++;
				}
			}
			System.out.print((comment));
			PrintWriter out = response.getWriter();
			out.write((comment.toString()));
			out.flush();
			out.close();
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
