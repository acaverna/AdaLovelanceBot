package com.github.caverna.adalovelance.util

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateUtil {

    private var sessionFactory: SessionFactory? = null

    fun getSessionFactory(): SessionFactory {
        if(this.sessionFactory == null){
            this.sessionFactory = Configuration().configure().buildSessionFactory()
        }
        return this.sessionFactory!!
    }

}