package lottery.com.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity(tableName = "service")
class Service(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "price")
    var price: Int,
    @ColumnInfo(name = "time_todo")
    var timeTodo: Int,
    @ColumnInfo(name = "detail")
    var detail: String,
    @ColumnInfo(name = "type_id")
    var typeId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeInt(price)
        parcel.writeInt(timeTodo)
        parcel.writeString(detail)
        parcel.writeInt(typeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Service> {
        override fun createFromParcel(parcel: Parcel): Service {
            return Service(parcel)
        }

        override fun newArray(size: Int): Array<Service?> {
            return arrayOfNulls(size)
        }
    }

    override fun equals(other: Any?): Boolean {
        super.equals(other)

        val that = other as Service?
        return this.id == that?.id

    }
}