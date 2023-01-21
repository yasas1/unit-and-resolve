package com.yasas.unitandresolve.service.unit.controller;

import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.service.UnitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@RestController
@RequestMapping("/units")
public class UnitController {

    private final UnitService unitService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create unit",
            operationId = "createUnit",
            description = "This API is to create unit for a given owner user id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok", content = @Content(schema = @Schema(implementation = UnitDto.class))),
                    @ApiResponse(responseCode = "400", description = "Bad Request (Invalid syntax)",
                            content= @Content(examples = @ExampleObject(value = "{\"status\":\"400\",\"message\":\"Validation failure\",\"error\":\"Bad Request\"}"))),
                    @ApiResponse(responseCode = "404", description = "Content Not Found",
                            content= @Content(examples = @ExampleObject(value = "{\"status\":\"404\",\"message\":\"Content Not Found\",\"error\":\"Not Found\"}")))
            },
    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(examples = @ExampleObject(value = "{\"name\":\"name\",\"context\":\"context\",\"description\":\"description\"," +
                    "\"ownerId\":1}")) ))
    public Mono<UnitDto> createUnit(@Valid @RequestBody UnitDto unitDto){
        return unitService.createUnit(unitDto);
    }

    @PostMapping("/{id}")
    public Mono<UnitDto> updateUnit(@Valid @RequestBody UnitDto unitDto, @PathVariable(name = "id") @NotNull long id){
        unitDto.setId(id);
        return unitService.updateUnit(unitDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<UnitDto> findAllUnits(){
        return unitService.getAllUnits();
    }

    @GetMapping("/{id}")
    public Mono<UnitDto> findUnitById(@PathVariable(name = "id") long id){
        return unitService.getUnitById(id);
    }

    @GetMapping("/assigned-user/{userId}")
    public Flux<UnitDto> findAllAssignedUnitsByUserId(@PathVariable(name = "userId") long userId){
        return unitService.getAllMyUnits(userId);
    }

}
