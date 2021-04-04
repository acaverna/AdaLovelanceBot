package com.github.caverna.adalovelance.util

import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

object HibernateUtil {

    private val HIBERNATE_PRODUCTION_CFG = "hibernate.cfg.xml"
    private val HIBERNATE_DEVELOPMENT_CFG = "hibernate-dev.cfg.xml"

    private var sessionFactory: SessionFactory? = null

    fun getSessionFactory(production:Boolean = false): SessionFactory {
        if(this.sessionFactory == null){
            when(production){
                true -> this.sessionFactory = Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory()

                false -> this.sessionFactory = Configuration()
                    .configure("hibernate-dev.cfg.xml")
                    .buildSessionFactory()
            }

        }
        return this.sessionFactory!!
    }

}