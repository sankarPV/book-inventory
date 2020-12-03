package com.msys.bookinventory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.msys.bookinventory.book.Book;
import com.msys.bookinventory.book.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookinventoryApplicationTests {

	
	@Test
	void contextLoads() {
	}

	@Autowired
    private TestRestTemplate template;
	@MockBean
    private BookRepository mockRepository;
	
	@Before
    public void init() {
		
        Book book = new Book(null, "SanBook", "Sankar", new BigDecimal("100.32"));
        when(mockRepository.findById(1L)).thenReturn(Optional.of(book));
    }
	
    @Test
    public void login() throws Exception {
    	String expected = "{id:1,name:\"SanBook\",author:\"Sankar\",price:100.32}";

        ResponseEntity<String> response = template.withBasicAuth("sanuser", "sankar")
          .getForEntity("/books/1", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(HttpStatus.OK, response.getStatusCode());

        JSONAssert.assertEquals(expected, response.getBody(), false);
    }
}
