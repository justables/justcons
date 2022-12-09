package de.ilijaz.myapp.myapp.iconconfiguration.icon

import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.AbstractIconPipe
import de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe.IconPipeAttributeConverter
import java.util.*
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "icons")
class Icon(
    @Id val id: UUID,
    val name: String,
    @Convert(converter = IconPipeAttributeConverter::class)
    val pipes: List<AbstractIconPipe>,
)