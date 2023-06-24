package com.sd.laborator.persistence.repositories

import com.sd.laborator.business.models.Book
import com.sd.laborator.persistence.interfaces.ILibraryAppRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.UncategorizedSQLException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class LibraryAppRepository:ILibraryAppRepository {
    @Autowired
    private lateinit var _jdbcTemplate: JdbcTemplate
    private var _rowMapper: RowMapper<Book?> = BookRowMapper()

    override fun createTable() {
        _jdbcTemplate.execute("""CREATE TABLE IF NOT EXISTS books(
                                        id INTEGER PRIMARY KEY AUTOINCREMENT,
                                        author VARCHAR(100),
                                        title VARCHAR(100) UNIQUE,
                                        publisher VARCHAR(100),
                                        text VARCHAR(1000))""")
    }

    override fun add(book: Book) {
        try {
            _jdbcTemplate.update("INSERT INTO books(author, title, publisher, text) VALUES (?, ?, ?, ?)", book.author, book.name, book.publisher, book.content)
        } catch (e: UncategorizedSQLException){
            println("An error has occurred in ${this.javaClass.name}.add")
        }
    }

    override fun getAll(): Set<Book?> {
        return _jdbcTemplate.query("SELECT * FROM books", _rowMapper).toSet()
    }

    override fun getBy(searchBy: String,data: String): Set<Book?>? {
        return try {
            val sql = "SELECT * FROM books WHERE $searchBy = ?"
            val bookList = _jdbcTemplate.query(sql, arrayOf(data), _rowMapper)
            bookList.toSet()
        }catch (e: UncategorizedSQLException){
            null
        }
    }
}