package eteration.muhammed.basketapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProductDto(
    @PrimaryKey
    val id: Int = 0,
    @ColumnInfo("brand")
    @SerializedName("brand")
    val brand: String,
    @ColumnInfo("createdAt")
    @SerializedName("createdAt")
    val createdAt: String,
    @ColumnInfo("description")
    @SerializedName("description")
    val description: String,
    @ColumnInfo("image")
    @SerializedName("image")
    val image: String,
    @ColumnInfo("model")
    @SerializedName("model")
    val model: String,
    @ColumnInfo("name")
    @SerializedName("name")
    val name: String,
    @ColumnInfo("price")
    @SerializedName("price")
    val price: String
){

}