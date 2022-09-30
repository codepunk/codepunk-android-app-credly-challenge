package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.remote.model.PersonRemote
import com.codepunk.credlychallenge.domain.model.Person

fun PersonRemote.toDomainModel(): Person = Person(
    id,
    url,
    name,
    birthday,
    deathDay,
    gender,
    images?.toDomainModel()
)