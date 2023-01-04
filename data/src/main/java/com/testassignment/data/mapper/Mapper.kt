package com.testassignment.data.mapper

interface Mapper<E, D> {
    fun mapToEntity(type: D): E
}
