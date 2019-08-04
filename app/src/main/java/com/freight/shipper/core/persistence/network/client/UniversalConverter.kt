package com.freight.shipper.core.persistence.network.client

import com.google.gson.GsonBuilder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class UniversalConverter : Converter.Factory() {

    var acceptNullConvertor: Converter.Factory
    var declineNullConvertor: Converter.Factory

    init {
        declineNullConvertor = GsonConverterFactory.create(GsonBuilder().create())

        val gson = GsonBuilder().serializeNulls().create()
        val gsonConverter = GsonBuilder().serializeNulls().create()
        acceptNullConvertor = GsonConverterFactory.create(gsonConverter)
    }

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *>? {
        if (type == Unit::class.java) {
            return UnitConverter
        } else {
            return acceptNullConvertor.responseBodyConverter(type, annotations, retrofit)
        }
    }

    override fun requestBodyConverter(type: Type, parameterAnnotations: Array<Annotation>,
                                      annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        if (annotations.get(0).annotationClass == DeclineNullValue::class) {
            return declineNullConvertor.requestBodyConverter(type, parameterAnnotations, annotations, retrofit)
        } else {
            return acceptNullConvertor.requestBodyConverter(type, parameterAnnotations, annotations, retrofit)
        }
    }

    //A converter factory that handles an empty response body or else kicks it off to the next converter
    //Necessary because the GSON converter factory will throw an error if it gets a null body
    private object UnitConverter : Converter<ResponseBody, Unit> {
        override fun convert(value: ResponseBody) {
            value.close()
        }
    }
}

// Always put this annotation at the first position
@Retention(AnnotationRetention.RUNTIME)
annotation class DeclineNullValue
