package br.com.edu.pix.shared.grpc.handlers

import io.grpc.BindableService
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import org.slf4j.LoggerFactory
import javax.inject.Inject

class ExceptionHandlerInterceptor(@Inject private val resover: ExceptionHandlerResover, Any):
                        MethodInterceptor<BindableService>{
      private  val LOGGER = LoggerFactory.getLogger(this.javaClass)
    override fun intercept(context: MethodInvocationContext<BindableService, Any>): Any?{
        try {

        }catch (){

        }
    }
}