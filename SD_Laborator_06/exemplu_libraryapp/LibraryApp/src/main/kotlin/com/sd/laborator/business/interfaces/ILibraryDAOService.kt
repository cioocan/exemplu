package com.sd.laborator.business.interfaces

import com.sd.laborator.business.models.Book

interface ILibraryDAOService {
    fun createBooksTable()
    fun getBooks(): Set<Book?>
    fun addBook(name: String, author: String, content: String, publisher: String)
    fun findAllByAuthor(author: String): Set<Book?>?
    fun findAllByTitle(title: String): Set<Book?>?
    fun findAllByPublisher(publisher: String): Set<Book?>?
}