/*
 * Copyright (C) 2022 Scott Slater
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.codepunk.credlychallenge.data.mapper

import com.codepunk.credlychallenge.data.local.model.CastEntryLocal
import com.codepunk.credlychallenge.data.remote.model.CastEntryRemote
import com.codepunk.credlychallenge.domain.model.CastEntry
import com.codepunk.credlychallenge.domain.model.Character
import com.codepunk.credlychallenge.domain.model.Person

/**
 * A set of methods used to map between remote, local, and domain cast entries.
 * A cast entry joins a person (actor/actress) to a character they play in a show.
 */

fun CastEntryRemote.toDomainModel(): CastEntry = CastEntry(
    person.toDomainModel(),
    character.toDomainModel(),
    self,
    voice
)

fun CastEntry.toLocalModel(
    showId: Int
): CastEntryLocal = CastEntryLocal(
    showId,
    person.id,
    character.id,
    self,
    voice
)

fun CastEntryLocal.toDomainModel(
    person: Person,
    character: Character
): CastEntry = CastEntry(
    person,
    character,
    self,
    voice
)
