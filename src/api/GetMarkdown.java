package api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


 
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class GetMarkdown extends HttpServlet {
	
	private boolean isMultipart;
	private int maxFileSize = 1024 * 1024 * 10;
	private int maxMenSize = 100 * 1024;
    
	/**
	 * Constructor of the object.
	 */
	public GetMarkdown() {
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
		isMultipart = ServletFileUpload.isMultipartContent(request);
		String result = "";
		response.setContentType("text/html;charset=utf-8");
		if (!isMultipart) {
			result = "未找到文件";
			response.getWriter().println(result);
			return;
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		//The file size of max will save in ram
		factory.setSizeThreshold(maxMenSize);
		
		String path = getServletContext().getRealPath("/markdown")+"/";
		System.out.print("path"+path);
		
		factory.setRepository(new File(path));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(maxFileSize);
		
		try {
			//解析请求获取文件项
			List  fileItems = upload.parseRequest(request);
			//处理上传的文件项
			java.util.Iterator i = fileItems.iterator();
			while(i.hasNext()){
				FileItem file = (FileItem) i.next();
				if (!file.isFormField()) {
					//获取上传文件的参数
					String filedName = file.getFieldName();
					String fileName = file.getName();
					System.out.print(fileName);
					String contentType = file.getContentType();
					boolean isInMemory = file.isInMemory();
					long sizeInBytes = file.getSize();
					//写入文件
					File fileIn = new File(path + fileName);
					file.write(fileIn);
				}
			}
		}catch(Exception e){
			System.out.print("e:"+e.getMessage());
			result = "上传失败";
		}
		response.getWriter().println(result);
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
