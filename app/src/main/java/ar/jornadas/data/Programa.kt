package ar.jornadas.data

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import retrofit2.http.GET
import kotlinx.coroutines.flow.Flow

data class FichaRemota(val id: String, val title: String, val track: String, val day: Int,
    val time: String, val room: String, val speaker: String, val summary: String)

interface ProgramaApi {
    @GET("programa") suspend fun consultar(): List<FichaRemota>
}

@Entity(tableName = "charlas")
data class Charla(@PrimaryKey val id: String, val titulo: String, val tema: String, val dia: Int,
    val hora: String, val sala: String, val ponente: String, val resumen: String,
    val favorita: Boolean = false)

@Entity(tableName = "edicion")
data class Edicion(@PrimaryKey val id: Int = 1)

@Dao
abstract class CarteleraDao {
    @Query("SELECT * FROM charlas ORDER BY dia, hora, sala")
    abstract fun observar(): Flow<List<Charla>>

    @Insert
    abstract suspend fun insertar(fichas: List<Charla>)

    @Query("SELECT id FROM charlas WHERE favorita = 1")
    abstract suspend fun idsFavoritas(): List<String>

    @Query("DELETE FROM charlas")
    abstract suspend fun borrarTodo()

    @Query("UPDATE charlas SET favorita = NOT favorita WHERE id = :id")
    abstract suspend fun alternar(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun guardarEdicion(edicion: Edicion)

    @Query("SELECT COUNT(*) FROM edicion")
    abstract suspend fun contarEdiciones(): Int

    @Transaction
    open suspend fun reemplazarPrograma(fichas: List<Charla>) {
        val favoritas = idsFavoritas()
        borrarTodo()
        insertar(fichas.map { it.copy(favorita = it.id in favoritas) })
        guardarEdicion(Edicion())
    }
}

@Database(entities = [Charla::class, Edicion::class], version = 2, exportSchema = false)
abstract class Archivo : RoomDatabase() {
    abstract fun cartelera(): CarteleraDao
}

val MIGRACION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE IF NOT EXISTS edicion (id INTEGER NOT NULL, PRIMARY KEY(id))")
        db.execSQL("INSERT INTO edicion (id) SELECT 1 WHERE EXISTS (SELECT 1 FROM charlas)")
    }
}
