package de.ilijaz.myapp.myapp.cat

import org.springframework.data.repository.CrudRepository

interface CatRepository : CrudRepository<Cat, Int>
