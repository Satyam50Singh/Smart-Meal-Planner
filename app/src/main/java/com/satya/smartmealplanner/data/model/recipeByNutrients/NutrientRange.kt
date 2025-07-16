package com.satya.smartmealplanner.data.model.recipeByNutrients

import android.os.Parcel
import android.os.Parcelable

data class NutrientRange(
    var carbs: ClosedFloatingPointRange<Float> = 10f..40f,
    var protein: ClosedFloatingPointRange<Float> = 40f..80f,
    var calories: ClosedFloatingPointRange<Float> = 200f..500f,
    var fat: ClosedFloatingPointRange<Float> = 30f..50f
) : Parcelable {

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeFloat(carbs.start)
        dest.writeFloat(carbs.endInclusive)
        dest.writeFloat(protein.start)
        dest.writeFloat(protein.endInclusive)
        dest.writeFloat(calories.start)
        dest.writeFloat(calories.endInclusive)
        dest.writeFloat(fat.start)
        dest.writeFloat(fat.endInclusive)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<NutrientRange> {
        override fun createFromParcel(parcel: Parcel): NutrientRange {
            val carbsStart = parcel.readFloat()
            val carbsEnd = parcel.readFloat()
            val proteinStart = parcel.readFloat()
            val proteinEnd = parcel.readFloat()
            val caloriesStart = parcel.readFloat()
            val caloriesEnd = parcel.readFloat()
            val fatStart = parcel.readFloat()
            val fatEnd = parcel.readFloat()

            return NutrientRange(
                carbs = carbsStart..carbsEnd,
                protein = proteinStart..proteinEnd,
                calories = caloriesStart..caloriesEnd,
                fat = fatStart..fatEnd
            )
        }

        override fun newArray(size: Int): Array<NutrientRange?> = arrayOfNulls(size)
    }
}
