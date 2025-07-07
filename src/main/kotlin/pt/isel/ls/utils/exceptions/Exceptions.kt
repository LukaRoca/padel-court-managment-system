package pt.isel.ls.utils.exceptions

class AuthorizationException(message: String) : Exception(message)

class TokenNotFoundException(message: String) : Exception(message)

class BadRequestException(message: String) : Exception(message)

class ForbiddenException(message: String) : Exception(message)