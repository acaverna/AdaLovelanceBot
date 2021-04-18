package com.github.caverna.adalovelance.persistence

import com.github.caverna.adalovelance.model.Presence
import com.github.caverna.adalovelance.util.HibernateUtil

abstract class BaseRepository<T> (val clazz:Class<T>){

    protected val sessionFactory = HibernateUtil.getSessionFactory()

    fun save(obj:T){
        val session = this.sessionFactory.openSession()
        session.beginTransaction()
        session.saveOrUpdate(obj)
        session.transaction.commit()
        session.close()
    }

    fun delete(obj:T){
        val session = this.sessionFactory.openSession()
        session.beginTransaction()
        session.delete(obj)
        session.transaction.commit()
        session.close()
    }

    fun find(id:Long):T{
        val session = this.sessionFactory.openSession()
        session.beginTransaction()
        val presence:T =  clazz.cast(session.find(Presence::class.java, id))
        session.transaction.commit()
        session.close()

        return presence
    }

    fun findAll():List<T>{
        val session = this.sessionFactory.openSession()
        session.beginTransaction()

        val presences:List<T> = session.createQuery("SELECT t FROM ${clazz.simpleName} t").resultList as List<T>

        session.transaction.commit()
        session.close()

        return presences
    }

}