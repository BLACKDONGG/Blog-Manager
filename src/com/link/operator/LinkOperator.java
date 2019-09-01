package com.link.operator;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.link.model.Linker;

public class LinkOperator {
	//Ôö
	public void addLink(Linker linker){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		sess.save(linker);
		tx.commit();
		sess.close();
	}
	//²é
	public List getLink(){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Linker order by publish asc";
		Query query = sess.createQuery(HQL);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	public List getByWebsite(String website){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Linker where website = :website order by publish asc";
		Query query = sess.createQuery(HQL);
		query.setMaxResults(1);
		query.setParameter("website",website);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	
	public static void main(String[] args) {
		LinkOperator o = new LinkOperator();
		List l = o.getLink();
		System.out.print(JSON.toJSONString(l));
	}
}
