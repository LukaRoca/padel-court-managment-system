package pt.isel.ls.storage
import pt.isel.ls.domain.*
import java.util.UUID

object DataMem : IStorage {
    // Usar mutableListOf() para permitir modificações na lista
    private val users = mutableListOf(
        User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com")),
        User(Id(2), Name("Luka Roca"), Email("luka@gmail.com"))
    )

    private var uid = 2

    // Função que cria um novo usuário
    fun createUser(name: String, email: String): User {
        val token = UUID.randomUUID().toString()
        val newUser = User(Id(uid++), Name(name), Email(email))
        users.add(newUser) // Adicionando o novo usuário à lista
        println("Usuário adicionado: $newUser")
        println("Lista atual de usuários: $users")
        return newUser
    }

    // Função que encontra um usuário pelo ID
    fun getUserById(userId: Int): User? {
        return users.find { it.uid.id == userId }
    }

    // Função para retornar a lista de usuários (mutável)
    fun getUsers(): MutableList<User> {
        return users
    }
}
