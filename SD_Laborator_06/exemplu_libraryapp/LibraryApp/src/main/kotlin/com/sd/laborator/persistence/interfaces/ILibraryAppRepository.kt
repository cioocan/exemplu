package com.sd.laborator.persistence.interfaces

import com.sd.laborator.business.models.Book

interface ILibraryAppRepository {
    fun createTable()
    fun add(book: Book)
    fun getAll(): Set<Book?>
    fun getBy(searchBy: String,data: String): Set<Book?>?
}