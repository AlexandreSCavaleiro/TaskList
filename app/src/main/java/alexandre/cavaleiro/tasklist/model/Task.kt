package alexandre.cavaleiro.tasklist.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    var id: Int,
    var descricao: String,
    var completa: Boolean
): Parcelable
