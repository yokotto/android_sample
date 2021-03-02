package com.example.expenses

import java.io.Serializable

data class RecyclerData (
        val image : Int,
        val number : Int,
        val listText : String
)

class StudentData() : Serializable {

        var id :Int = 0
        var grade :Int= 0
        var studentNumber: Int = 0
        var studentImage: Int = 0
        var age: Int = 0
        var gender: String = ""
        var birthday: String = ""
        var birthplace: String = ""
        var bloodType: String = ""
        var firstName: String = ""
        var lastName: String = ""
        var department: String = ""
        var club: String = ""
        var skill: String = ""
        var hobby: String = ""
        var favoriteFood: String = ""
        var memo: String = ""

        constructor(
                id: Int,
                grade: Int,
                studentNumber: Int,
                studentImage: Int,
                age: Int,
                gender: String,
                birthday: String,
                birthplace: String,
                bloodType: String,
                firstName: String,
                lastName: String,
                department: String,
                club: String,
                skill: String,
                hobby: String,
                favoriteFood: String,
                memo: String
        ) : this() {
                this.id = id
                this.grade = grade
                this.studentNumber = studentNumber
                this.studentImage = studentImage
                this.grade = grade
                this.studentNumber = studentNumber
                this.studentImage = studentImage
                this.age = age
                this.gender = gender
                this.birthday = birthday
                this.birthplace = birthplace
                this.bloodType = bloodType
                this.firstName = firstName
                this.lastName = lastName
                this.department = department
                this.club = club
                this.skill = skill
                this.hobby = hobby
                this.favoriteFood = favoriteFood
                this.memo = memo
        }

        fun castToStudentData(studentEntity:StudentEntity):StudentData{
                return StudentData(
                id = studentEntity.id,
                grade = studentEntity.grade,
                studentNumber = studentEntity.student_number,
                studentImage = studentEntity.student_image,
                age = studentEntity.age,
                gender = studentEntity.gender,
                birthday = studentEntity.birthday,
                birthplace = studentEntity.birthplace,
                bloodType = studentEntity.blood_type,
                firstName = studentEntity.first_name,
                lastName =studentEntity.last_name,
                department = studentEntity.department,
                club = studentEntity.club,
                skill = studentEntity.skill,
                hobby = studentEntity.hobby,
                favoriteFood = studentEntity.favorite_food,
                memo = studentEntity.memo)
        }

        fun castToStudentEntity(studentData:StudentData):StudentEntity{
                return StudentEntity(
                        id = studentData.id,
                        grade = studentData.grade,
                        student_number = studentData.studentNumber,
                        student_image = studentData.studentImage,
                        age = studentData.age,
                        gender = studentData.gender,
                        birthday = studentData.birthday,
                        birthplace = studentData.birthplace,
                        blood_type = studentData.bloodType,
                        first_name = studentData.firstName,
                        last_name =studentData.lastName,
                        department = studentData.department,
                        club = studentData.club,
                        skill = studentData.skill,
                        hobby = studentData.hobby,
                        favorite_food = studentData.favoriteFood,
                        memo = studentData.memo)
        }
}