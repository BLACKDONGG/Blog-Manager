package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.securiter.model.Securiter;
import com.securiter.operator.SecuriterOperator;
import com.util.md5.MD5;

public class GetNecessaryInformation extends HttpServlet {
	private String CODE_ILLEGAL = "0";
	private String CODE_ERROR = "1";
	private String CODE_SUCCESS = "2";
	private String CODE_CHANGE = "4";
	private String CODE_EMPTY = "5";
	/**
	 * Constructor of the object.
	 */
	public GetNecessaryInformation() {
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
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SecuriterOperator operator = new SecuriterOperator();
		Securiter securiter = null;
		MD5 MD = new MD5();
		JsonObject info = new JsonObject();
		String[] s = {"Y","E","W","U","M","I","N","G","Y","U","E","H","U","D","U","W","U","F","U","Y","O","U","S","H","I","S","H","U","Q","I","Z","I","H","U","A"};
		//获取网络时间并格式化
	   	String webURL = "http://www.ntsc.ac.cn";
		URL url = null;
		Date pDate = null;
		Date cDate = null;
		try {
			url = new URL(webURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URLConnection con = null;
		try {
			con = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			con.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long currentDateL = con.getDate();
		Date currentDate = new Date(currentDateL);
		String now = dateFormat.format(currentDate);
		//获取数据库中publish列的最新时间
		List publish = operator.getAsList();
		if(publish.size() > 0){
			securiter = (Securiter)publish.get(0);
			String publishDate = securiter.getPublish();
			
			try {
				pDate = dateFormat.parse(publishDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				cDate = dateFormat.parse(now);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Calendar c = Calendar.getInstance();
			c.setTime(cDate);
			int day1 = c.get(Calendar.DAY_OF_YEAR);
			c.setTime(pDate);
			int day2 = c.get(Calendar.DAY_OF_YEAR);
			int divDayCount = Math.abs(day2-day1);
			//七天更换一次口令
			if(divDayCount >= 7){
				String PPK = "";
				Random random = new Random();
				for(int i = 0;i <= 7;i++){
					PPK += s[random.nextInt(33)];
				}
				String keycode = MD.encryptByMD5(PPK);
				Securiter se = new Securiter();
				se.setKeycode(keycode);
				se.setPublish(dateFormat.format(cDate));
				operator.add(se);
				
				//构造json
				info.addProperty("publicKey", keycode);
				info.addProperty("CODE",CODE_CHANGE);
			}else{
				
				info.addProperty("publicKey", securiter.getKeycode());
				info.addProperty("CODE",CODE_SUCCESS);
			}
	}else{
		String PPK = "";
		Random random = new Random();
		for(int i = 0;i <= 7;i++){
			PPK += s[random.nextInt(33)];
		}
		try {
			cDate = dateFormat.parse(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String keycode = MD.encryptByMD5(PPK);
		Securiter se = new Securiter();
		se.setKeycode(keycode);
		se.setPublish(dateFormat.format(cDate));
		operator.add(se);
		
		info.addProperty("publicKey", keycode);
		info.addProperty("CODE",CODE_EMPTY);
	}
		out.write(info.toString());
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
