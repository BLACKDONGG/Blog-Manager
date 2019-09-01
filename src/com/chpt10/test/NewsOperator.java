package com.chpt10.test;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

import com.chpt10.model.News;
public class NewsOperator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		News news = new News();
		news.setTitle("first");
		news.setContent("first");
		sess.save(news);
		tx.commit();
		sess.close();
	}

}
