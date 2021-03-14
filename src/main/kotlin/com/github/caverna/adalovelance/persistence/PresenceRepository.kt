package com.github.caverna.adalovelance.persistence

import com.github.caverna.adalovelance.model.Presence
import com.github.caverna.adalovelance.util.HibernateUtil

class PresenceRepository: IBaseRepository<Presence> {

    private val sessionFactory = HibernateUtil.getSessionFactory()

    override fun save(obj: Presence) {
        val session = this.sessionFactory.openSession()
        session.beginTransaction()
        session.saveOrUpdate(obj)
        session.transaction.commit()
        session.close()
    }

    override fun delete(obj: Presence) {
        val session = this.sessionFactory.openSession()
        session.beginTransaction()
        session.delete(obj)
        session.transaction.commit()
        session.close()
    }

    override fun find(id: Long): Presence {
        val session = this.sessionFactory.openSession()
        session.beginTransaction()
        val presence =  session.find(Presence::class.java, id)
        session.transaction.commit()
        session.close()

        return presence
    }

    override fun findAll(): List<Presence> {
        val session = this.sessionFactory.openSession()
        session.beginTransaction()

        val criteria =  session.criteriaBuilder.createQuery(Presence::class.java)
        val root = criteria.from(Presence::class.java)
        criteria.select(root)

        val presences = session.createQuery(criteria).resultList

        session.transaction.commit()
        session.close()

        return presences
    }

}