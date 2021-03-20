package br.com.edu.pix.registra

import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidador::class])
annotation class ValidPixKey(
    val message: String = "nao e um formato valido UUID",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)
@Singleton
class ValidPixKeyValidador : ConstraintValidator<ValidPixKey, NovaChavePix> {
    override fun isValid(
        value: NovaChavePix?,
        annotationMetadata: AnnotationValue<ValidPixKey>,
        context: ConstraintValidatorContext,
    ): Boolean {
        if(value?.tipo == null){
            return false
        }

        return value.tipo.valida(value.chave)
    }


}
