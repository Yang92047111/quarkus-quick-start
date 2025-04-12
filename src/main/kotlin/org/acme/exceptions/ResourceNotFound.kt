package org.acme.exceptions

class ResourceNotFound(
    val resourceName: String,
    val fieldName: String
) : RuntimeException("$resourceName not found with $fieldName")