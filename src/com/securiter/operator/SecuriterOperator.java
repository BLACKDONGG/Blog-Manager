package com.securiter.operator;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.securiter.model.Securiter;

public class SecuriterOperator {
	public void add(Securiter s){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		sess.save(s);
		tx.commit();
		sess.close();
	}
	
	public List getAsList(){
		Configuration conf = new Configuration().configure();
		  SessionFactory sf = conf.buildSessionFactory();
		  Session sess = sf.openSession();
		  Transaction tx = sess.beginTransaction();
		  String hql = "from Securiter order by publish desc";
		  Query query = sess.createQuery(hql);
		  query.setFirstResult(0);
		  query.setMaxResults(1);
		  List list = query.list();
		  tx.commit();
		  sess.close();
		  return list;
		
	}
}
