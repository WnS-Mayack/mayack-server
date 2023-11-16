package com.example.mayak.service

import com.example.mayak.Repository.RegionRepository
import com.example.mayak.entity.Region
import com.example.mayak.requests.RegionRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class RegionService(
        private val regionRepository: RegionRepository
) {

    fun getAll(): MutableList<Region> {
        return regionRepository.findAll()
    }

    @Transactional
    fun createAll(request: RegionRequest) {
        val regions = request.regions.map {
            Region(it)
        }.toList()

        regionRepository.saveAll(regions)
    }
}