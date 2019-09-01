package com.timeline.operator;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.alibaba.fastjson.JSON;
import com.blog.model.Blog;
import com.timeline.model.Timeline;

public class TimelineOperator {
	public List get(int start,int count) {
		  Configuration conf = new Configuration().configure();
		  SessionFactory sf = conf.buildSessionFactory();
		  Session sess = sf.openSession();
		  Transaction tx = sess.beginTransaction();
		  String hql = "from Timeline order by time desc";
		  Query query = sess.createQuery(hql);
		  query.setFirstResult(start);
		  query.setMaxResults(count);
		  List list = query.list();
		  tx.commit();
		  sess.close();
		  return list;
		}
	public void add(Timeline timeline){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		sess.save(timeline);
		tx.commit();
		sess.close();
	}
	public List getAll(){
		Configuration conf = new Configuration().configure();
	    SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		String HQL = "from Timeline order by time desc";
		Query query = sess.createQuery(HQL);
		List result = query.list();
		tx.commit();
		sess.close();
		return result;
	}
	public void updateTimelineInfo(int id,String time,String content,String status){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		Timeline timeline=(Timeline) sess.get(Timeline.class, id);
	    timeline.setTime(time);
	    timeline.setContent(content);
	    timeline.setStatus(status);
	    sess.update(timeline);
	    tx.commit();
		sess.close();
    }
	public static void main(String[] args) {
		TimelineOperator operator = new TimelineOperator();
		List result = operator.getAll();
//		Timeline t = (Timeline)result.get(0);
//		System.out.print(t.getContent());
		System.out.print(JSON.toJSONString(result));
	}
}
