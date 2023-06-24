package com.sd.laborator.persistence.repositories

import com.sd.laborator.business.models.Book
import com.sd.laborator.business.models.Content
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class BookRowMapper : RowMapper<Book?> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, rowNum: Int): Book {
        val id = rs.getInt("id")
        val content = Content(
            rs.getString("author"),
            rs.getString("title"),
            rs.getString("publisher"),
            rs.getString("text"))
        return Book(id, content)
    }
}
