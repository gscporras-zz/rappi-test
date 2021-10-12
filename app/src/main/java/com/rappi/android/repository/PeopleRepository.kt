package com.rappi.android.repository

import androidx.annotation.WorkerThread
import com.rappi.android.network.service.PeopleService
import com.rappi.android.room.PeopleDao
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class PeopleRepository constructor(
    private val peopleService: PeopleService,
    private val peopleDao: PeopleDao
) : Repository {

    init {
        Timber.d("Injection PeopleRepository")
    }

    @WorkerThread
    fun loadPeople(page: Int, success: () -> Unit, error: () -> Unit) = flow {
        var people = peopleDao.getPeople(page)
        if (people.isEmpty()) {
            val response = peopleService.fetchPopularPeople(page)
            response.suspendOnSuccess {
                people = data.results
                people.forEach { it.page = page }
                peopleDao.insertPeople(people)
                emit(people)
            }.onError {
                error()
            }.onException { error() }
        } else {
            emit(people)
        }
    }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadPersonDetail(id: Long, success: () -> Unit) = flow {
        val person = peopleDao.getPerson(id)
        var personDetail = person.personDetail
        if (personDetail == null) {
            val response = peopleService.fetchPersonDetail(id)
            response.suspendOnSuccess {
                personDetail = data
                person.personDetail = personDetail
                peopleDao.updatePerson(person)
                emit(personDetail)
            }
        } else {
            emit(personDetail)
        }
    }.onCompletion { success() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun loadPersonById(id: Long) = flow {
        val person = peopleDao.getPerson(id)
        emit(person)
    }.flowOn(Dispatchers.IO)
}