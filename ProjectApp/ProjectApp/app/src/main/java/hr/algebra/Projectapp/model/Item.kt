package hr.algebra.Projectapp.model

data class Item(
    val _id: Long?,
    val _userId: String,
    var title: String,
    var body: String,
    var read: Boolean
)
