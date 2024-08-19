package com.generation.hooda.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.generation.hooda.model.Categoria;
import com.generation.hooda.repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping
    ResponseEntity<List<Categoria>> findAll(){
        return ResponseEntity.ok(categoriaRepository.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById (@PathVariable Long id){
    	return categoriaRepository.findById(id)
    			.map(resposta -> ResponseEntity.ok(resposta))
    			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @GetMapping("/nome/{nome}")
    public ResponseEntity<List<Categoria>> getByNome(@PathVariable String nome) {
        return ResponseEntity.ok(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
    }
    
    @PostMapping
    public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria categoria){
    	return ResponseEntity.status(HttpStatus.CREATED)
    			.body(categoriaRepository.save(categoria));
    }
    
    @PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categoria) {
		if (categoriaRepository.existsById(categoria.getId())) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(categoriaRepository.save(categoria));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); 
    }
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> categoria= categoriaRepository.findById(id);
		
		if (categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		
		categoriaRepository.deleteById(id);
	}
	
}

    



