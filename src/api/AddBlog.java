package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.blog.operator.BlogOperator;
import com.blog.util.DateFormat;

public class AddBlog extends HttpServlet {
	private String CODE_ILLEGAL = "0";
	private String CODE_ERROR = "1";
	private String CODE_SUCCESS = "2";
	/**
	 * Constructor of the object.
	 */
	public AddBlog() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String title = request.getParameter("title");
		String src = request.getParameter("src");
		String classify = request.getParameter("classify");
		String logo = request.getParameter("logo");
		String content = request.getParameter("content");
		content = "/markdown/"+content;
		String href = request.getParameter("href");
		String publish = request.getParameter("publish");
		String preface = request.getParameter("preface");
		String article_classify = request.getParameter("article_classify");
		String tag = request.getParameter("tag");
		String imgSRCPRE = "https://www.ditto.ink/images";
		String displayBgSRC = "https://www.ditto.ink/images/display/display";
		
		if(title.equals("")||classify.equals("")||publish.equals("")||preface.equals("")||content.equals("")||article_classify.equals("")||tag.equals("")){
			out.write(JSON.toJSONString(CODE_ILLEGAL));
			out.flush();
			out.close();
		}else{
			Blog blog = new Blog();
			blog.setTitle(title);
			blog.setClassify(classify);
			blog.setContent(content);
			blog.setHref(href);
			blog.setPublish(publish);
			blog.setPreface(preface);
			blog.setArticle_classify(article_classify);
			blog.setTag(tag);
			//∑÷¿‡Õº∆¨
			if(classify.equals("Note")){
				blog.setLogo(imgSRCPRE+"/"+classify.toLowerCase()+".png");
			}else if(classify.equals("Blog")){
				blog.setLogo(imgSRCPRE+"/"+classify.toLowerCase()+".png");
			}else if(classify.equals("Recent")){
				blog.setLogo(imgSRCPRE+"/"+classify.toLowerCase()+".png");
			}else if(classify.equals("Link")){
				blog.setLogo(imgSRCPRE+"/"+classify.toLowerCase()+".png");
			}else if(classify.equals("Lab")){
				blog.setLogo(imgSRCPRE+"/"+classify.toLowerCase()+".png");
			}
			
			//±≥æ∞Õº∆¨
			Random random = new Random();
			int bgnum = random.nextInt(25);
			int displaynum = random.nextInt(20);
			blog.setSrc(imgSRCPRE+"/bg"+bgnum+".png");
			blog.setDisplay(displayBgSRC+displaynum+".jpg");
			//‘ˆ
			BlogOperator operator = new BlogOperator();
			operator.add(blog);
			out.write(JSON.toJSONString(CODE_SUCCESS));
//			out.write("<script>history.go(-1)</script>");
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
