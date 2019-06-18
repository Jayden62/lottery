package lottery.com.model

import android.os.Parcel
import android.os.Parcelable

class Schedule(
    var userId: Int,
    var frameId: Int,
    var day: String,
    var date: String,
    var qrCode: String,
    var names: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userId)
        parcel.writeInt(frameId)
        parcel.writeString(day)
        parcel.writeString(date)
        parcel.writeString(qrCode)
        parcel.writeString(names)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Schedule> {
        override fun createFromParcel(parcel: Parcel): Schedule {
            return Schedule(parcel)
        }

        override fun newArray(size: Int): Array<Schedule?> {
            return arrayOfNulls(size)
        }
    }
}