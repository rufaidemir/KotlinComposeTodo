package com.rufaidemir.examplecompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todotable")
data class TodoItem(
    val title:String,
    var hasColor: Boolean,
    var color: Int? =null,
    val hasTime: Boolean,
    val time: Long,
    val hasInterval:Boolean,
    val interval: Long? =null,
    val intervalText: String? =null,
    var hasTag: Boolean,
    var tag:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}
