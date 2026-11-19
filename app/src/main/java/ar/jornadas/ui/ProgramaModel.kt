package ar.jornadas.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import ar.jornadas.Servicios
import ar.jornadas.data.Charla
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

sealed class Carga {
    object Lista : Carga()
    object Cargando : Carga()
    object SinSenal : Carga()
    object SinDatos : Carga()
}

data class ProgramaUiState(
    val charlas: List<Charla> = emptyList(),
    val carga: Carga = Carga.Lista,
    val hayEdicion: Boolean = false
)

class ProgramaModel(app: Application) : AndroidViewModel(app) {
    companion object {
        private var servicios: Servicios? = null
        private var yaCargo = false
    }

    private val compartidos = servicios ?: Servicios(app).also { servicios = it }
    val mesa = compartidos.mesa
    private val repo = compartidos.agenda

    private val _ui = MutableStateFlow(ProgramaUiState())
    val ui: StateFlow<ProgramaUiState> = _ui

    init {
        viewModelScope.launch {
            repo.programa.collect { lista -> _ui.update { it.copy(charlas = lista) } }
        }
        viewModelScope.launch(Dispatchers.IO) {
            val hay = repo.hayEdicion()
            _ui.update { it.copy(hayEdicion = hay) }
        }
    }

    fun cargaInicial() {
        if (yaCargo) return
        yaCargo = true
        recargar()
    }

    private var otraVez = false

    fun recargar() {
        if (_ui.value.carga == Carga.Cargando) {
            otraVez = true
            return
        }
        _ui.update { it.copy(carga = Carga.Cargando) }
        viewModelScope.launch {
            var exito: Boolean
            do {
                otraVez = false
                exito = withContext(Dispatchers.IO) {
                    try {
                        repo.sincronizar()
                        true
                    } catch (e: IOException) {
                        false
                    }
                }
                if (exito) _ui.update { it.copy(hayEdicion = true) }
            } while (otraVez)
            _ui.update {
                it.copy(carga = when {
                    exito -> Carga.Lista
                    it.hayEdicion -> Carga.SinSenal
                    else -> Carga.SinDatos
                })
            }
        }
    }

    fun marcar(id: String) {
        viewModelScope.launch(Dispatchers.IO) { repo.cambiarFavorita(id) }
    }
}
