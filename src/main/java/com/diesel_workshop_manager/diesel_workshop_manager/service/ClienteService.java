package com.diesel_workshop_manager.diesel_workshop_manager.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.Cliente;
import com.diesel_workshop_manager.diesel_workshop_manager.models.cliente.ClienteDTO;
import com.diesel_workshop_manager.diesel_workshop_manager.models.endereco.Endereco;
import com.diesel_workshop_manager.diesel_workshop_manager.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository repository;
    @Autowired
    EnderecoService enderecoService;

    ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Cliente saveCliente(ClienteDTO dto) {
        Cliente cliente = converter(dto, null);
        return repository.save(cliente);
    }

    @Transactional
    public Cliente atualizarCliente(Long id, ClienteDTO clienteDTO) {
        Optional<Cliente> optional = repository.findById(id);

        if (!optional.isPresent()) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }

        Cliente cliente = converter(clienteDTO, optional);
        return repository.save(cliente);
    }

    @Transactional
    public void deletarCliente(Long id) {
        Optional<Cliente> optional = repository.findById(id);
        Cliente cliente = optional.orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        repository.delete(cliente);
    }

    public List<Cliente> listarClientes() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    // ------------------------------- converter -------------------------------

    private Cliente converter(ClienteDTO dto, Optional<Cliente> optional) {

        Cliente cliente = Objects.nonNull(optional) ? optional.get() : new Cliente();

        cliente.setNomeCliente(dto.getNomeCliente());
        cliente.setCpf(dto.getCpf());
        cliente.setCNPJ(dto.getCnpj());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        Endereco endereco = enderecoService.findById(dto.getEndereco());

        if (endereco != null) {
            cliente.setEndereco(endereco);
        } else {
            cliente.setEndereco(null);
        }

        return cliente;
    }
}
