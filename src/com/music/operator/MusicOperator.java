package com.music.operator;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;

import com.blog.model.Blog;
import com.music.model.Music;

public class MusicOperator {
	public void updateMusicInfo(int id,String name,String singer,String src,String cover){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
	    Music music=(Music) sess.get(Music.class, id);
	    music.setName(name);
	    music.setSinger(singer);
	    music.setCover(cover);
	    music.setSrc(src);
	    sess.update(music);
	    tx.commit();
		sess.close();
    }
	public void add(Music music){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		sess.save(music);
		tx.commit();
		sess.close();
	}
	public List getRows(Music music){
		Configuration conf = new Configuration().configure();
		SessionFactory sf = conf.buildSessionFactory();
		Session sess = sf.openSession();
		Transaction tx = sess.beginTransaction();
		Criteria criteria = sess.createCriteria(Music.class);
		criteria.setProjection(Projections.rowCount());
		System.out.print(criteria.list());
		List list = criteria.list();
		tx.commit();
		sess.close();
		return list;
	}
	public List getMusicPagination(int start,int count) {
		  Configuration conf = new Configuration().configure();
		  SessionFactory sf = conf.buildSessionFactory();
		  Session sess = sf.openSession();
		  Transaction tx = sess.beginTransaction();
		  String hql = "from Music";
		  Query query = sess.createQuery(hql);
		  query.setFirstResult(start);
		  query.setMaxResults(count);
		  List list = query.list();
		  tx.commit();
		  sess.close();
		  return list;
		}
	public static void main(String[] args) {
		MusicOperator o = new MusicOperator();
		o.updateMusicInfo(1, "asd", "asdasd", "asdasd", "asdasd");
	}
	
}
