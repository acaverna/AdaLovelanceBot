package com.github.caverna.adalovelance.persistence

import com.github.caverna.adalovelance.model.Command

class CommandRepository:BaseRepository<Command>(Command::class.java) {

}