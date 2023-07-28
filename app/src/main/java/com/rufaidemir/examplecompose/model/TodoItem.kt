package com.rufaidemir.examplecompose.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "todotable")
data class TodoItem(
    val title:String,
    val hasColor: Boolean,
    val color: Int? =null,
    val hasTime: Boolean,
    val time: Long? =null,
    val hasInterval:Boolean,
    val interval: Long? =null,
    val intervalText: String? =null,
    val hasTag: Boolean,
    val tag:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var uuid:Int=0
}
