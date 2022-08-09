package br.com.letscode.spring.controller;


import br.com.letscode.spring.model.Client;
import br.com.letscode.spring.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping({"/client"})
public class ClientController {

    private ClientRepository repository;

    ClientController(ClientRepository clientRepository) {
        this.repository = clientRepository;
    }


    @GetMapping("/list")
    public List<Client> findAll(){
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody @Valid Client client){
        return repository.save(client);
    }

    @PutMapping(value="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody @Valid Client client) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(client.getName());
                    record.setAge(client.getAge());
                    record.setVat(client.getVat());
                    record.setEmail(client.getEmail());

                    Client updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity <?> delete(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(e -> {
                    String fieldName = ((FieldError)e).getField();
                    String errorMessage = e.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });
        return errors;
    }

}