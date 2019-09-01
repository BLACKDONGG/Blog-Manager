package com.blog.operator;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.chpt10.model.News;
import com.observer.model.Observer;
import com.sun.istack.internal.Nullable;

public class BlogOperator {
	//获取数据库起始的记录
	public List get(int start,int count) {
	  Configuration conf = new Configuration().configure();
	  SessionFactory sf = conf.buildSessionFactory();
	  Session sess = sf.openSession();
	  Transaction tx = sess.beginTransaction();
	  String hql = "from Blog order by blog_id desc";
	  Query query = sess.createQuery(hql);
	  query.setFirstResult(start);
	  query.setMaxResults(count);
	  List list = query.list();
	  tx.commit();
	  sess.close();
	  return list;
	}
	
	public List getBookByDate(String book){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Blog where article_classify = :article_classify order by publish desc";
		Query query = sess.createQuery(HQL);
		query.setParameter("article_classify",book);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	
	//添加进数据库
	public void add(Blog blog){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		sess.save(blog);
		tx.commit();
		sess.close();
	}
	//按id查找
	public Blog getById(Blog blog,int id){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		Blog result = (Blog) sess.get(Blog.class, id);
		tx.commit();
		sess.close();
		return result;
	}
	//查询数据库总记录数
	public List getRows(Blog blog){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		Criteria criteria = sess.createCriteria(Blog.class);
		criteria.setProjection(Projections.rowCount());
		System.out.print(criteria.list());
		List list = criteria.list();
		tx.commit();
		sess.close();
		return list;
	}
	public List getTheLast(){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Blog order by publish desc";
		Query query = sess.createQuery(HQL);
		query.setFirstResult(0);
		query.setMaxResults(7);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	//改
	public void uploadView(int blog_id,int view){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
        Blog blog = (Blog) sess.get(Blog.class,blog_id);
        blog.setView(view);
        sess.update(blog);
        tx.commit();
		sess.close();
		return;
	}
	
	public int getView(int blog_id){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		Blog blog = (Blog)sess.get(Blog.class,blog_id );
		int result = blog.getView();
		tx.commit();
		sess.close();
		return result;
	}
	
	public static void main(String[] args) {
		BlogOperator o = new BlogOperator();
		List result = o.getBookByDate("React");
		System.out.print(JSON.toJSONString(result));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
