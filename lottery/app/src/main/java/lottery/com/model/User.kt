package lottery.com.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class User(
    var name: String,
    var phoneNumber: String,
    var passWord: String,
    var sex: String,
    var address: String,
    var accessToken: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(phoneNumber)
        parcel.writeString(passWord)
        parcel.writeString(sex)
        parcel.writeString(address)
        parcel.writeString(accessToken)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}