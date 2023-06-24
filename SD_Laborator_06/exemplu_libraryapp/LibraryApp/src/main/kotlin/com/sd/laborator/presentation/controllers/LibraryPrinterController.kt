package com.sd.laborator.presentation.controllers

import com.sd.laborator.business.interfaces.ILibraryDAOService
import com.sd.laborator.business.interfaces.ILibraryPrinterService
import com.sd.laborator.business.models.Book
import com.sd.laborator.business.models.Content
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class LibraryPrinterController {
    @Autowired
    private lateinit var _libraryDAOService: ILibraryDAOService

    @Autowired
    private lateinit var _libraryPrinterService: ILibraryPrinterService


    @RequestMapping("/create_table", method = [RequestMethod.GET])
    @ResponseBody
    fun createTable(): ResponseEntity<Unit>{
        _libraryDAOService.createBooksTable()
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }

    @RequestMapping(value = ["/book"], method = [RequestMethod.GET])
    fun createPerson(
        @RequestParam(required = false, name = "author", defaultValue = "") author: String,
        @RequestParam(required = false, name = "title", defaultValue = "") title: String,
        @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String,
        @RequestParam(required = false, name = "content", defaultValue = "") content: String,
    ): ResponseEntity<Unit> {
        _libraryDAOService.addBook(title, author, content, publisher)
        return ResponseEntity(Unit, HttpStatus.CREATED)
    }


    @RequestMapping("/print", method = [RequestMethod.GET])
    @ResponseBody
    fun customPrint(@RequestParam(required = true, name = "format", defaultValue = "") format: String): String {
        return when (format) {
            "html" -> _libraryPrinterService.printHTML(_libraryDAOService.getBooks())
            "json" -> _libraryPrinterService.printJSON(_libraryDAOService.getBooks())
            "raw" -> _libraryPrinterService.printRaw(_libraryDAOService.getBooks())
            else -> "Not implemented"
        }
    }

    @RequestMapping("/find", method = [RequestMethod.GET])
    @ResponseBody
    fun customFind(
        @RequestParam(required = false, name = "author", defaultValue = "") author: String,
        @RequestParam(required = false, name = "title", defaultValue = "") title: String,
        @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String
    ): String {
        if (author != "")
            return this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByAuthor(author))
        if (title != "")
            return this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByTitle(title))
        if (publisher != "")
            return this._libraryPrinterService.printJSON(this._libraryDAOService.findAllByPublisher(publisher))
        return "Not a valid field"
    }

    @RequestMapping("/find-and-print", method = [RequestMethod.GET])
    @ResponseBody
    fun findAndPrint(
        @RequestParam(required = false, name = "author", defaultValue = "") author: String,
        @RequestParam(required = false, name = "title", defaultValue = "") title: String,
        @RequestParam(required = false, name = "publisher", defaultValue = "") publisher: String,
        @RequestParam(required = true, name = "format", defaultValue = "") format: String
    ): String {
        if (author != "")
            return printByType(format, this._libraryDAOService.findAllByAuthor(author)!!)
        if (title != "")
            return printByType(format, this._libraryDAOService.findAllByTitle(author)!!)
        if (publisher != "")
            return printByType(format, this._libraryDAOService.findAllByPublisher(author)!!)
        return "Not a valid field"
    }

    private fun printByType(format: String, toPrint: Set<Book?>): String {
        return when (format) {
            "html" -> _libraryPrinterService.printHTML(toPrint)
            "json" -> _libraryPrinterService.printJSON(toPrint)
            "raw" -> _libraryPrinterService.printRaw(toPrint)
            else -> "Not implemented"
        }
    }
}