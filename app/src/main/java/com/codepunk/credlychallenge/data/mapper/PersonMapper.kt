package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.PersonLocal
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

fun Person.toLocalModel(): PersonLocal = PersonLocal(
    id,
    url,
    name,
    birthday,
    deathDay,
    gender,
    images?.toLocalModel()
)

fun PersonLocal.toDomainModel(): Person = Person(
    id,
    url,
    name,
    birthday,
    deathDay,
    gender,
    images?.toDomainModel()
)