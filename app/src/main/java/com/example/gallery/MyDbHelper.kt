package com.example.gallery

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build.VERSION
import androidx.core.content.contentValuesOf
import com.example.gallery.models.MyImage

class MyDbHelper(context: Context) :SQLiteOpenHelper(context, DB_NAME , null , VERSION) , MyDbInterface{

    companion object{
        const val DB_NAME="my_images_db"
        const val TABLE_NAME="image_table"
        const val ID="id"
        const val NAME="name"
        const val IMAGE_URI="image"
        const val VERSION=1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "create table $TABLE_NAME($ID integer not null primary key autoincrement unique, $NAME text not null, $IMAGE_URI text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addImage(myImage: MyImage) {
        val dataBase = this.writableDatabase
        val c = ContentValues()
        c.put(NAME, myImage.name)
        c.put(IMAGE_URI, myImage.imageURI)
        dataBase.insert(TABLE_NAME ,null , c)
        dataBase.close()
    }

    override fun getImage(): ArrayList<MyImage> {
        val list = ArrayList<MyImage>()
        val dataBase = this.readableDatabase
        val cursor = dataBase.rawQuery("select * from $TABLE_NAME", null, )

        if (cursor.moveToFirst()){
            do {
                list.add(
                    MyImage(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                    )
                )
            }while (cursor.moveToNext())
        }
        return list
    }

}