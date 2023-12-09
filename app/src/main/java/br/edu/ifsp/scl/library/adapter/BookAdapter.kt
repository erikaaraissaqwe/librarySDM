package br.edu.ifsp.scl.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.library.data.Book
import br.edu.ifsp.scl.library.databinding.TileBookBinding
import com.bumptech.glide.Glide;

class BookAdapter: RecyclerView.Adapter<BookAdapter.BookViewHolder>(),
    Filterable {

    var listener: BookListener?=null

    var bookList = ArrayList<Book>()
    var bookListFilterable = ArrayList<Book>()

    private lateinit var binding: TileBookBinding

    fun updateList(newList: List<Book> ){
        bookList = newList as ArrayList<Book>
        bookListFilterable = bookList
        notifyDataSetChanged()
    }

    fun setClickListener(listener: BookListener)
    {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookViewHolder {

        binding = TileBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.tituloVH.text = bookListFilterable[position].titulo
        holder.isbnVH.text = bookListFilterable[position].isbn
        var context = holder.imgVH.context
        var url = bookListFilterable[position].img
        Glide.with(context).load(url).into(holder.imgVH);
    }

    override fun getItemCount(): Int {
        return bookListFilterable.size
    }

    inner class BookViewHolder(view: TileBookBinding): RecyclerView.ViewHolder(view.root)
    {
        val tituloVH = view.tituloTv
        val isbnVH = view.isbnTv
        val imgVH = view.imgIv

        init {
            view.root.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

    }

    interface BookListener
    {
        fun onItemClick(pos: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                bookListFilterable = if(p0.toString().isEmpty())
                    bookList
                else {
                    val resultList = ArrayList<Book>()
                    for (row in bookList)
                        if (row.titulo.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = bookListFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                bookListFilterable = p1?.values as ArrayList<Book>
                notifyDataSetChanged()
            }

        }
    }

}