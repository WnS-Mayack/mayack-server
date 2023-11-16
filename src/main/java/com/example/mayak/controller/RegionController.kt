package com.example.mayak.controller

import com.example.mayak.entity.Region
import com.example.mayak.requests.RegionRequest
import com.example.mayak.service.RegionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/regions")
class RegionController(
        private val regionService: RegionService
) {

    @GetMapping
    fun getAll(): MutableList<Region> {
        return regionService.getAll()
    }

    @PostMapping
    fun createAll(
            @RequestBody regionRequest: RegionRequest
    ) {
        return regionService.createAll(regionRequest)
    }
}