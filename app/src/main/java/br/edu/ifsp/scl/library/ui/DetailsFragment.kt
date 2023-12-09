package br.edu.ifsp.scl.library.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.library.R
import br.edu.ifsp.scl.library.data.Book
import br.edu.ifsp.scl.library.databinding.FragmentDetailsBinding
import br.edu.ifsp.scl.library.viewmodel.BookViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    lateinit var book: Book

    lateinit var tituloEditText: EditText
    lateinit var isbnEditText: EditText
    lateinit var imgEditText: EditText
    lateinit var imgCEditText: ImageView

    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BookViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tituloEditText = binding.commonLayout.editTextTitulo
        isbnEditText = binding.commonLayout.editTextIsbn
        imgEditText = binding.commonLayout.editTextImg
        imgCEditText =  binding.commonLayout.imgC

        val idBook = requireArguments().getInt("idBook")

        viewModel.getBookById(idBook)

        viewModel

        viewModel.book.observe(viewLifecycleOwner) { result ->
            result?.let {
                book = result
                tituloEditText.setText(book.titulo)
                isbnEditText.setText(book.isbn)
                imgEditText.setText(book.img)
                var context = imgCEditText.context
                var url = book.img
                Glide.with(context).load(url).into(imgCEditText);
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.details_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_updateBook -> {
                        if (binding.commonLayout.editTextTitulo.text.isEmpty() ||
                            binding.commonLayout.editTextIsbn.text.isEmpty() ||
                            binding.commonLayout.editTextImg.text.isEmpty()
                        ) {
                            Toast.makeText(
                                binding.commonLayout.editTextIsbn.context,
                                "Complete todos os campos",
                                Toast.LENGTH_SHORT
                            ).show()
                            false
                        } else {
                            book.titulo = tituloEditText.text.toString()
                            book.isbn = isbnEditText.text.toString()
                            book.img = imgEditText.text.toString()

                            viewModel.update(book)

                            Snackbar.make(
                                binding.root,
                                "Edição realizada no livro.",
                                Snackbar.LENGTH_SHORT
                            ).show()

                            findNavController().popBackStack()
                            true
                        }
                    }
                    R.id.action_deleteBook ->{
                        viewModel.delete(book)

                        Snackbar.make(binding.root, "Livro deletado.", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}