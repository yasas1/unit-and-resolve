package com.yasas.unitandresolve.service.unit.controller;

import com.yasas.unitandresolve.service.unit.entity.dto.UnitDto;
import com.yasas.unitandresolve.service.unit.entity.dto.UserUnitDto;
import com.yasas.unitandresolve.service.unit.service.UserUnitService;
import com.yasas.unitandresolve.service.user.entity.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
@RequestMapping("/units-users")
public class UserUnitController {

    private final UserUnitService userUnitService;

    @PostMapping("/{unitId}/assign")
    public Mono<UserUnitDto> assignUserToUnit(@RequestBody UserUnitDto userUnitDto,
                                       @PathVariable(name = "unitId") long unitId) {
        userUnitDto.setUnitId(unitId);
        return userUnitService.assignUserToUnit(userUnitDto);
    }

    @GetMapping
    public Flux<UserUnitDto> findAllUserUnits() {
        return userUnitService.findAllUserUnits();
    }

    @GetMapping("/{unitId}/users")
    public Flux<UserDto> findAssignedAllUserToUnitByUnitId(@PathVariable(name = "unitId") long unitId) {
        return userUnitService.findAssignedAllUserToUnitByUnitId(unitId);
    }

    @GetMapping("/{userId}/units")
    public Flux<UnitDto> findAssignedAllUnitsToUserByUserId(@PathVariable(name = "userId") long userId) {
        return userUnitService.findAssignedAllUnitsToUserByUserId(userId);
    }
}
