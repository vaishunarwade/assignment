
package com.example.demo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ProductController {


@Autowired
	JdbcTemplate jtemp;
private static Map<String, Product> productRepo = new HashMap<>();

@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
public ResponseEntity<Object> delete(@PathVariable("id") String id) { 
   productRepo.remove(id);
 return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
}

@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) { 
   productRepo.put(id, product);
return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
}   
@RequestMapping(value = "/products", method = RequestMethod.POST)
public ResponseEntity<Object> createProduct(@RequestBody Product product) {
int x=jtemp.update("insert into product values(?,?,?,?,?)",product.getId(),product.getName(),product.getType(),product.getCategory(),product.getPrice());
if(x>=1)
return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
else
	return new ResponseEntity<>("Product is not created ", HttpStatus.CREATED);
	
}
@RequestMapping(value = "/products")
public ResponseEntity<Object> getProduct() {
	List<Product>l=new ArrayList<>();
	jtemp.query("select * from product", new RowMapper() {
		@Override
		public Object mapRow(ResultSet rs, int rowNum)  {
			Product b=new Product();
			try
			{
			
			b.setId(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setType(rs.getString(3));
			b.setCategory(rs.getString(4));
			b.setPrice(rs.getInt(5));
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
			l.add(b);
			return b;
		}
	});
   return new ResponseEntity<>(l, HttpStatus.OK);
}
	
	
}