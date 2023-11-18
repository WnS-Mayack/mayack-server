package com.example.mayak.utils

import com.querydsl.core.types.Predicate
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.NumberPath
import com.querydsl.core.types.dsl.StringPath
import kotlin.math.max

class QueryDslUtils {
    companion object {
        fun eq(source: StringPath, value: String?): BooleanExpression? {
            return value?.let { source.eq(it) }
        }

        fun contains(source: StringPath, value: String?): BooleanExpression? {
            return value?.let { source.contains(it) }
        }


        fun between(source: NumberPath<Int>, minValue: Int?, maxValue: Int?): BooleanExpression? {
            if (minValue == null && maxValue == null)
                return null
            return source.between(minValue, maxValue)
        }
    }
}