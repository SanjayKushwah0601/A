package com.freight.shipper.core.persistence.network.client

import com.freight.shipper.model.UserRole
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class UserRoleDeserializer : JsonDeserializer<UserRole> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, classOfT: Type, context: JsonDeserializationContext
    ): UserRole? {
        return UserRole.fromString(json.asString)
    }
}