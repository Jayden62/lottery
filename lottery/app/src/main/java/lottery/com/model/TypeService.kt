package lottery.com.model

import android.os.Parcel
import android.os.Parcelable

class TypeService(
    var id: Int,
    var name: String?,
    var image: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TypeService> {
        override fun createFromParcel(parcel: Parcel): TypeService {
            return TypeService(parcel)
        }

        override fun newArray(size: Int): Array<TypeService?> {
            return arrayOfNulls(size)
        }
    }
}