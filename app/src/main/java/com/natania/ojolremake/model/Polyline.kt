package com.natania.ojolremake.model

import com.google.gson.annotations.SerializedName

data class Polyline(

	@field:SerializedName("points")
	val points: String? = null
)