package pt.isel.ls.storage
import pt.isel.ls.domain.*
import java.util.UUID

object DataMem : IStorage {
    val users = mutableListOf(
        User(Id(1), Name("Michael Jackson"), Email("michael@gmail.com"))
    )

    private var uid = 1

    fun createUser(name: String, email: String) : User {
        val token = UUID.randomUUID().toString()
        ++uid
        val newUser = User(Id(uid), Name(name), Email(email) )
        users.add(newUser)
        println("Usuário adicionado: $newUser") // DEBUG: Verificar se está a ser adicionado
        println("Lista atual de usuários: $users") // DEBUG: Verificar o estado da lista
        return newUser
    }
    /*
    override fun getUsers(): List<User> = emptyList()
    override fun getUserById(userId: Int): User? {
        TODO("Not yet implemented")

    }

     */


}