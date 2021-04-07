package com.example.username.myscheduler

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Subscription : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var serviceName = ""
    var money = ""
}