package com.yasas.unitandresolve.service.unit.controller;

import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    @PostMapping()
    public Mono<UnitDto> createUnit(@Valid @RequestBody UnitDto unitDto){
        return unitService.createUnit(unitDto);
    }

    @PostMapping("/{id}")
    public Mono<UnitDto> updateUnit(@Valid @RequestBody UnitDto unitDto, @PathVariable(name = "id") @NotNull long id){
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

    @GetMapping("/{id}")
    public Flux<UnitDto> findAllAssignedUnitsByUserId(@PathVariable(name = "id") long id){
        return unitService.getAllMyUnits(id);
    }

}
