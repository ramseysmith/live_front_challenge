package com.example.data.dto

import com.example.data.constants.DataConstants


data class CharacterList(val characters: List<Character>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is CharacterList) return false

        return characters == other.characters
    }

    override fun hashCode(): Int {
        return characters.hashCode()
    }
}

data class Character(
    val title: String,
    val description: String,
    val imageUrl: String,
)

fun CharacterListDto.toDomain() = CharacterList(
    characters = relatedTopics?.map {
        Character(
            title = it.text.toTitle(),
            description = it.text.toDescription(),
            imageUrl = it.icon?.url.toImageUrl()
        )
    }?.distinctBy { it.title } ?: emptyList()
)

private fun String?.toTitle(): String = this?.substringBefore(" -") ?: ""
private fun String?.toDescription(): String = this?.substringAfter(" - ") ?: ""
private fun String?.toImageUrl(): String = "${DataConstants.IMAGE_BASE_URL}$this "