package com.securiter.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.securiter.operator.SecuriterOperator;

public class Securiter {
	private int id;
	private String keycode;
	private String publish;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getKeycode() {
		return keycode;
	}
	public void setKeycode(String keycode) {
		this.keycode = keycode;
	}
	public String getPublish() {
		return publish;
	}
	public void setPublish(String publish) {
		this.publish = publish;
	}
	public static void main(String[] args) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SecuriterOperator operator = new SecuriterOperator();
		Securiter securiter = null;
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
			System.out.print(day2-day1);
		}
	}
}
