package lottery.com.model

import android.os.Parcel
import android.os.Parcelable

class MainTimeFrame(var id: Int, var detail: String?, var state: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(detail)
        parcel.writeInt(state)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainTimeFrame> {
        override fun createFromParcel(parcel: Parcel): MainTimeFrame {
            return MainTimeFrame(parcel)
        }

        override fun newArray(size: Int): Array<MainTimeFrame?> {
            return arrayOfNulls(size)
        }
    }
}