package lottery.com.model

import android.os.Parcel
import android.os.Parcelable

class Booked(var detailsTimeFrame: String?, var schDate: String?, var serviceName: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(detailsTimeFrame)
        parcel.writeString(schDate)
        parcel.writeString(serviceName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Booked> {
        override fun createFromParcel(parcel: Parcel): Booked {
            return Booked(parcel)
        }

        override fun newArray(size: Int): Array<Booked?> {
            return arrayOfNulls(size)
        }
    }
}