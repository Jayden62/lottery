package lottery.com.model

import android.os.Parcel
import android.os.Parcelable

class ScheduleDetail(var id: Int, var serviceId: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(serviceId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ScheduleDetail> {
        override fun createFromParcel(parcel: Parcel): ScheduleDetail {
            return ScheduleDetail(parcel)
        }

        override fun newArray(size: Int): Array<ScheduleDetail?> {
            return arrayOfNulls(size)
        }
    }
}