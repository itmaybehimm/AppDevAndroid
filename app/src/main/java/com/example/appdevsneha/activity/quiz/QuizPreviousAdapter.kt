import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdevsneha.activity.main.MainCardViewHolder
import com.example.appdevsneha.data.model.Note
import com.example.appdevsneha.data.model.Quiz
import com.example.appdevsneha.databinding.MainCardCellBinding
import com.example.appdevsneha.databinding.QuizPreviousRowBinding

class QuizPreviousAdapter(private val quizes: List<Quiz>,private val clickListenerDelete:(Quiz)->Unit) :
    RecyclerView.Adapter<QuizPreviousAdapter.QuizPreviousViewHolder>() {
    class QuizPreviousViewHolder(private val cellBinding: QuizPreviousRowBinding,) : RecyclerView.ViewHolder(cellBinding.root) {
        fun bindNote(quiz: Quiz, clickListenerDelete:(Quiz)->Unit){
            cellBinding.quizName.text = buildString {
                append("Quiz ")
                append(quiz.id.toString())
            }
            cellBinding.quizScore.text = buildString {
                append("Score : ")
                append(quiz.score)
            }

            cellBinding.deleteQuizButton.setOnClickListener{clickListenerDelete(quiz)}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizPreviousViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding= QuizPreviousRowBinding.inflate(from,parent,false)
        return QuizPreviousViewHolder(binding)
    }

    override fun getItemCount(): Int =quizes.size

    override fun onBindViewHolder(holder: QuizPreviousViewHolder, position: Int) {
        holder.bindNote(quizes[position],clickListenerDelete)
    }

}
