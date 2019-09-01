package com.manager.operator;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.blog.operator.BlogOperator;
import com.manager.model.Manager;

public class ManagerOperator {
		//∞¥username≤È’“
	public List getUsername(String username){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Manager where username = :username";
		Query query = sess.createQuery(HQL);
		query.setParameter("username",username);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
		
		public static void main(String[] args) {
			ManagerOperator operator = new ManagerOperator();
			List m = operator.getUsername("WEID");
			System.out.print(JSON.toJSONString(m));
		}
}
