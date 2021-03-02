package com.example.expenses

import androidx.room.*
import java.io.Serializable

//Entityを追加したら,をつけてここに追記
@Database(entities = [SpendingEntity::class, PaymentEntity::class , StudentEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase(){
    //daoを追加したらここに追記
    abstract fun spendingDao() : SpendingDao
    abstract fun paymentDao() : PaymentDao
    abstract fun studentDao() : StudentDao

    companion object {}
}

//家計簿項目と合計額
@Entity(tableName = "t_spending")
data class SpendingEntity(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "entries") val entries : String,
    @ColumnInfo(name = "amount") val amount : Int,
    @ColumnInfo(name = "display_flag") val display_flag : Int
)

//家計簿項目と合計額
@Entity(tableName = "t_payment")
data class PaymentEntity(
        @PrimaryKey(autoGenerate = true) val id:Int,
        @ColumnInfo(name = "entries") val entries : String,
        @ColumnInfo(name = "amount") val amount : Int,
        @ColumnInfo(name = "display_flag") val display_flag : Int
)

@Entity(tableName = "t_student")
data class StudentEntity  (
        @PrimaryKey(autoGenerate = true) val id:Int,
        @ColumnInfo(name = "grade") val grade:Int,
        @ColumnInfo(name = "student_number") val student_number:Int,
        @ColumnInfo(name = "student_image") val student_image:Int,
        @ColumnInfo(name = "age") val age:Int,
        @ColumnInfo(name = "gender") val gender:String,
        @ColumnInfo(name = "birthday") val birthday:String,
        @ColumnInfo(name = "birthplace") val birthplace:String,
        @ColumnInfo(name = "blood_type") val blood_type:String,
        @ColumnInfo(name = "first_name") val first_name:String,
        @ColumnInfo(name = "last_name") val last_name:String,
        @ColumnInfo(name = "department") val department:String,
        @ColumnInfo(name = "club") val club:String,
        @ColumnInfo(name = "skill") val skill:String,
        @ColumnInfo(name = "hobby") val hobby:String,
        @ColumnInfo(name = "favorite_food") val favorite_food:String,
        @ColumnInfo(name = "memo") val memo:String
)

@Dao
interface SpendingDao {
    @Query("SELECT * from t_spending")
    fun getAll():List<SpendingEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(topicEntity:SpendingEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(topicEntity:SpendingEntity)

    @Delete
    fun delete(topicEntity: SpendingEntity)

    @Query("delete from t_spending")
    fun deleteAll()
}


@Dao
interface PaymentDao {
    @Query("SELECT * from t_payment")
    fun getAll():List<PaymentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(paymentEntity:PaymentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(paymentEntity:PaymentEntity)

    @Delete
    fun delete(paymentEntity:PaymentEntity)

    @Query("delete from t_payment")
    fun deleteAll()
}

@Dao
interface StudentDao {
    @Query("SELECT * from t_student")
    fun getAll():List<StudentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(studentEntity:StudentEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(studentEntity:StudentEntity)

    @Delete
    fun delete(studentEntity:StudentEntity)

    @Query("delete from t_student")
    fun deleteAll()
}