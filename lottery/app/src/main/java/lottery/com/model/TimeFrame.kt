package lottery.com.model

import android.os.Parcel
import android.os.Parcelable

class TimeFrame(var id: Int, var detail: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(detail)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TimeFrame> {
        override fun createFromParcel(parcel: Parcel): TimeFrame {
            return TimeFrame(parcel)
        }

        override fun newArray(size: Int): Array<TimeFrame?> {
            return arrayOfNulls(size)
        }
    }

}