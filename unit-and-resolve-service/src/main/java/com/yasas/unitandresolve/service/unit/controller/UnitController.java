package com.yasas.unitandresolve.service.unit.controller;

import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    @PostMapping()
    public Mono<UnitDto> createUnit(@RequestBody UnitDto unitDto){
        return unitService.createUnit(unitDto);
    }

    @PostMapping("/{id}")
    public Mono<UnitDto> updateUnit(@RequestBody UnitDto unitDto, @PathVariable(name = "id") long id){
        unitDto.setId(id);
        return unitService.updateUnit(unitDto);
    }

    @GetMapping()
    public Flux<UnitDto> findAllUnits(){
        return unitService.getAllUnits();
    }

    @GetMapping("/{id}")
    public Mono<UnitDto> findUnitById(@PathVariable(name = "id") long id){
        return unitService.getUnitById(id);
    }

}
