package com.example.data.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterListDto(
    @SerializedName("RelatedTopics")
    val relatedTopics: List<RelatedTopicsItem>? = null,
) : Parcelable

@Parcelize
data class Icon(
    @SerializedName("URL")
    val url: String? = null
) : Parcelable

@Parcelize
data class RelatedTopicsItem(
    @SerializedName("Text")
    val text: String? = null,
    @SerializedName("Icon")
    val icon: Icon? = null,
) : Parcelable