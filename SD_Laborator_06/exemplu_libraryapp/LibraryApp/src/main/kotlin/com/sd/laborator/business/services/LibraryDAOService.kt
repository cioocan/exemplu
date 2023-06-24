package com.sd.laborator.business.services

import com.sd.laborator.business.interfaces.ILibraryDAOService
import com.sd.laborator.business.models.Book
import com.sd.laborator.business.models.Content
import com.sd.laborator.persistence.interfaces.ILibraryAppRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class LibraryDAOService : ILibraryDAOService {
    @Autowired
    private lateinit var _libraryRepository: ILibraryAppRepository
    private var _pattern: Pattern = Pattern.compile("\\W")

    override fun createBooksTable() {
        _libraryRepository.createTable()
    }

    override fun getBooks(): Set<Book?> {
        return _libraryRepository.getAll()
    }

    override fun addBook(name: String, author: String, content: String, publisher: String) {
        if(_pattern.matcher(name).find() || _pattern.matcher(author).find() || _pattern.matcher(content).find() || _pattern.matcher(publisher).find()) {
            println("SQL Injection for beer name")
            return
        }
        _libraryRepository.add(Book(0, Content(author, content, name, publisher)))
    }

    override fun findAllByAuthor(author: String): Set<Book?>? {
        if(_pattern.matcher(author).find()){
            println("SQL Injection for beer name")
            return emptySet()
        }

        //return (this._books.filter { it.hasAuthor(author) }).toSet()
        return _libraryRepository.getBy("author", author)
    }

    override fun findAllByTitle(title: String): Set<Book?>? {
        if(_pattern.matcher(title).find()){
            println("SQL Injection for beer name")
            return emptySet()
        }
        //return (this._books.filter { it.hasTitle(title) }).toSet()
        return _libraryRepository.getBy("title", title)
    }

    override fun findAllByPublisher(publisher: String): Set<Book?>? {
        if(_pattern.matcher(publisher).find()){
            println("SQL Injection for beer name")
            return emptySet()
        }
        //return (this._books.filter { it.publishedBy(publisher) }).toSet()
        return _libraryRepository.getBy("publisher", publisher)
    }
}