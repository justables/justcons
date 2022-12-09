package de.ilijaz.myapp.myapp.cat

import de.ilijaz.myapp.myapp.model.AbstractDtoWithId
import de.ilijaz.myapp.myapp.name.NameDTO

data class CatDTO(
    override val id: Long,
    val name: NameDTO,
    val age: Int,
    val imageUrl: String,
) : AbstractDtoWithId()
