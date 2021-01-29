package dev.chu.chulibrary.di

import javax.inject.Qualifier

@Qualifier
annotation class ForApplication

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.VALUE_PARAMETER
)
@Qualifier
annotation class ViewModelInject