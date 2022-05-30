package com.javadistancecalculationcep.distancecalculation.rest;

import com.javadistancecalculationcep.distancecalculation.model.ViaCep;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viacep", url ="https://viacep.com.br/ws/")
public interface ViaCepClient {
    @GetMapping("{cep}/json")
    ViaCep getCep(@PathVariable String cep);
}