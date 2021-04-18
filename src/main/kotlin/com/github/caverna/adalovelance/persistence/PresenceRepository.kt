package com.github.caverna.adalovelance.persistence

import com.github.caverna.adalovelance.model.Presence
import com.github.caverna.adalovelance.util.HibernateUtil

class PresenceRepository: BaseRepository<Presence>(Presence::class.java) {

    fun findByName(name:String): Presence?{
        val session = this.sessionFactory.openSession()
        session.beginTransaction()

        val cb =  session.criteriaBuilder
        val cr = cb.createQuery(Presence::class.java)
        val root = cr.from(Presence::class.java)

        cr.apply {
            select(root)
            where(cb.equal(root.get<Presence>("name"), name))
            orderBy(cb.desc(root.get<Presence>("date")))
        }

        val presence = session.createQuery(cr).uniqueResult()

        session.transaction.commit()
        session.close()

        return presence

    }

    fun countFrequencyByName(name:String): Int{
        val session = this.sessionFactory.openSession()
        session.beginTransaction()

        val cb =  session.criteriaBuilder
        val cr = cb.createQuery(Presence::class.java)
        val root = cr.from(Presence::class.java)

        cr.apply {
            select(root)
            where(cb.equal(root.get<Presence>("name"), name))
            orderBy(cb.desc(root.get<Presence>("date")))
        }

        val count = session.createQuery(cr).resultList.size

        session.transaction.commit()
        session.close()

        return count

    }

}