package de.ilijaz.myapp.myapp.iconconfiguration.icon.pipe

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import java.io.ByteArrayOutputStream
import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter
class IconPipeAttributeConverter : AttributeConverter<List<AbstractIconPipe>, String> {
    override fun convertToDatabaseColumn(attribute: List<AbstractIconPipe>?): String {
        if (attribute == null) {
            return "null";
        }
        val out = ByteArrayOutputStream()
        ObjectMapper().writeValue(out, attribute)
        return String(out.toByteArray());
    }

    override fun convertToEntityAttribute(dbData: String?): List<AbstractIconPipe> =
        if (dbData == null) listOf() else ObjectMapper().readValue<List<AbstractIconPipe>>(dbData);
}
