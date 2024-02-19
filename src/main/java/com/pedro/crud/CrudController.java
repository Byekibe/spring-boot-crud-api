package com.pedro.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/v1/persons")
public class CrudController {
    private final CrudRepository crudRepository;

    @Autowired
    public CrudController(CrudRepository crudRepository) {
        this.crudRepository = crudRepository;
    }
    @GetMapping
    public List<CrudModel> getAllPersons() {
        return crudRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CrudModel> getPersonById(@PathVariable Long id) {
        Optional<CrudModel> crudOptional = crudRepository.findById(id);
        return crudOptional.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CrudModel> createPerson(@RequestBody CrudModel crudmodel) {
        CrudModel savedPerson = crudRepository.save(crudmodel);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CrudModel> updatePerson(@PathVariable Long id, @RequestBody CrudModel person) {
        Optional<CrudModel> personOptional = crudRepository.findById(id);
        if (personOptional.isPresent()) {
            CrudModel existingPerson = personOptional.get();
            existingPerson.setName(person.getName());
            existingPerson.setEmail(person.getEmail());
            CrudModel updatedPerson = crudRepository.save(existingPerson);
            return ResponseEntity.ok(updatedPerson);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        crudRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Or HttpStatus.NO_CONTENT if preferred
    }
}
