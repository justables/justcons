package de.ilijaz.myapp.myapp.cat

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "cats")
data class Cat(
  @Id
  val id: Long,
  val name: String,
  val age: Int,
  val imageUrl: String,
)
