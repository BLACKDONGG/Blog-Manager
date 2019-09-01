package api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

public class PublishComment extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public PublishComment() {
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
		out.write("Illegal Request");
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
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		
		String pid = request.getParameter("pid");
		String blog_id = request.getParameter("blog_id");
		String name = request.getParameter("name");
		String email = request.getParameter("email").toLowerCase();
		String website = request.getParameter("website").toLowerCase();
		String words = request.getParameter("words");
		String publish = request.getParameter("publish");
		String avatar = "http://www.ditto.ink/images/avatar/avatar0.jpg";
		String DEFAULT_AVATAR = "http://www.ditto.ink/images/avatar/avatar_DEFAULT.jpg";
		String avatarManager = "http://www.ditto.ink/images/avatar/avatar_manager.jpg";
		
		PrintWriter out = response.getWriter();
		if(pid.equals("")||blog_id.equals("")||name.equals("")||email.equals("")||words.equals("")||publish.equals("")){
			out.write("Illegal Request");
			out.flush();
			out.close();
			return;
		}else{
			
			Observer observer = new Observer();
			ObserverOperator operator = new ObserverOperator();
			observer.setPid(Integer.parseInt(pid));
			observer.setBlog_id((Integer.parseInt(blog_id)));
			observer.setName(name);
			observer.setEmail(email);
			observer.setWebsite(website);
			
			observer.setPublish(publish);
			if(name.equals("WEID")){
				if(words.startsWith("BINGO:")){
					observer.setAvatar(avatarManager);
					words = words.substring(6);
				}else{
					observer.setAvatar(DEFAULT_AVATAR);
				}
			}else{
				List websiteList = operator.getByWebsite(website);
				if(websiteList.size() > 0){
					avatar =((Observer)(websiteList.get(0))).getAvatar();
					observer.setAvatar(avatar);
				}else{
					List avatarlist = operator.getAvatarList(Integer.parseInt(blog_id));
					String basePath = request.getSession().getServletContext().getRealPath("/images/avatar");
					System.out.print(basePath);
				    String[] filelist = new File(basePath).list();
				    for(Object al: avatarlist ){
				    	int count = 0;
				    	for(Object fl: filelist ){
				    		if((al.toString()).contains(fl.toString())){
				    			filelist[count] = "";
				    		}
				    		count++;
				    	}
				    }
				    int trigger = 0;
				    for(Object a : filelist){
				    	if(a.toString().equals("")){
				    		trigger++;
				    		continue;
				    	}else{
				    		avatar = "http://www.ditto.ink/images/avatar/"+a.toString();
				    		observer.setAvatar(avatar);
				    		break;
				    	}
				    }
				    if(trigger == filelist.length){
			    		observer.setAvatar(DEFAULT_AVATAR);
				    }
				}
			}
			observer.setWords(words);
			operator.addComment(observer);
//			System.out.print(JSON.toJSONString(observer));
			if(blog_id == null || blog_id.equals("")){
				return;
			}else{
				int i = Integer.parseInt(blog_id);
				List result = operator.getByBlogID(i);
				Observer observer2 = new Observer();
				Observer pobserver = new Observer();
				Observer cobserver = new Observer();
				int pid_ = 0;
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
						observer2 = (Observer) itResult.next();
						item = new JsonObject();
						if(String.valueOf(observer2.getPid()).equals("-1")){
							item.addProperty("id", observer2.getId());
							item.addProperty("pid", observer2.getPid());
							item.addProperty("blog_id", observer2.getBlog_id());
							item.addProperty("name", observer2.getName());
							item.addProperty("email", observer2.getEmail());
							item.addProperty("website", observer2.getWebsite());
							item.addProperty("words", observer2.getWords());
							item.addProperty("publish", observer2.getPublish());
							item.addProperty("avatar", observer2.getAvatar());
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
//					System.out.print(JSON.toJSONString(childlist));
					int count = 0;
					for(Object pobject:parentlist){
						pobserver = (Observer) pobject;
						id = pobserver.getId();
						for(Object cobject:childlist){
							cobserver = (Observer) cobject;
							pid_ = cobserver.getPid();
//							System.out.print(cobserver.getId());
//							System.out.print(id + "&"+ pid);
//							System.out.print("---");
							if(pid_ == id){
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
					
					System.out.print((comment));
					out.write((comment.toString()));
					out.flush();
					out.close();
				}
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
