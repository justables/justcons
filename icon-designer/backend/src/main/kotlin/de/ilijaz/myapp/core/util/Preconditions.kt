package de.ilijaz.myapp.core.util

class Preconditions(override val message: String) : RuntimeException(message) {
    companion object {
        fun <T> notNull(obj: T?): T = if (obj == null) {
            throw Preconditions("not null assertion exception")
        } else {
            obj
        }

        inline fun <reified T> type(obj: Any) {
            if (obj !is T) {
                throw Preconditions("not of type ${T::class} assertion exception")
            }
        }
    }
}