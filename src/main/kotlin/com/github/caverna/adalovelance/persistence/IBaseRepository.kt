package com.github.caverna.adalovelance.persistence

interface IBaseRepository<T> {

    fun save(obj:T)

    fun delete(obj:T)

    fun find(id:Long):T

    fun findAll():List<T>

}