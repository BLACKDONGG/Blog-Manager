package com.observer.operator;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.observer.model.Observer;

public class ObserverOperator {
	//按ID获取评论内容
	public List getByBlogID(int blog_id){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Observer where blog_id = :blog_id order by publish asc";
		Query query = sess.createQuery(HQL);
		query.setParameter("blog_id",blog_id);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	public List getLastRating(){
		Configuration conf = new Configuration().configure();
		  SessionFactory sf = conf.buildSessionFactory();
		  Session sess = sf.openSession();
		  Transaction tx = sess.beginTransaction();
		  String hql = "from Observer order by publish desc";
		  Query query = sess.createQuery(hql);
		  query.setFirstResult(0);
		  query.setMaxResults(7);
		  List list = query.list();
		  tx.commit();
		  sess.close();
		  return list;
	}
	//添加评论
	public void addComment(Observer observer){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		sess.save(observer);
		tx.commit();
		sess.close();
	}
	//按website获取
	public List getByWebsite(String website){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Observer where website = :website order by publish asc";
		Query query = sess.createQuery(HQL);
		query.setMaxResults(1);
		query.setParameter("website",website);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	//按列查询
	public List getAvatarList(int blog_id){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "select avatar from Observer where blog_id = :blog_id";
		Query query = sess.createQuery(HQL);
		query.setParameter("blog_id", blog_id);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	
	
	public static void main(String[] args) {
		String fl = "avatar5.jpg";
		String al = "http://localhost:8080/thisblog/images/avatar/avatar5.jpg";
		if(al.contains(fl)){
			System.out.print("true");
		}
	}
	
}
