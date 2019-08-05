package org.yzjt.sdk.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by LT on 2019/7/31.
 */
data class DownloadEntity(var total:Long,var current:Long,var tag:String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(total)
        parcel.writeLong(current)
        parcel.writeString(tag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DownloadEntity> {
        override fun createFromParcel(parcel: Parcel): DownloadEntity {
            return DownloadEntity(parcel)
        }

        override fun newArray(size: Int): Array<DownloadEntity?> {
            return arrayOfNulls(size)
        }
    }
}