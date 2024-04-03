package tech.lyuku.englishmanual.data.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.UUID

/**
 * In book detail screen, user can add new book item to MyBook list.
 * stored the data in MyBookItem
 */
open class MyBookItem() : RealmObject() {

    constructor(bookId: String, time: Long) : this() {
        this.bookId = bookId
        this.time = time
    }

    @PrimaryKey
    var id = UUID.randomUUID().toString()
    var bookId: String = ""
    var time: Long = 0
}