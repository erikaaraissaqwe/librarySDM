package br.edu.ifsp.scl.library.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.library.R
import br.edu.ifsp.scl.library.data.Book
import br.edu.ifsp.scl.library.databinding.FragmentRegisterBinding
import br.edu.ifsp.scl.library.viewmodel.BookViewModel
import com.google.android.material.snackbar.Snackbar


class RegisterFragment : Fragment(){
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.register_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_saveBook -> {
                        if (binding.commonLayout.editTextTitulo.text.isEmpty() ||
                                binding.commonLayout.editTextIsbn.text.isEmpty() ||
                                binding.commonLayout.editTextImg.text.isEmpty()){
                            Toast.makeText(binding.commonLayout.editTextIsbn.context, "Complete todos os campos", Toast.LENGTH_SHORT).show()
                        }else{
                            val titulo = binding.commonLayout.editTextTitulo.text.toString()
                            val isbn = binding.commonLayout.editTextIsbn.text.toString()
                            var img = binding.commonLayout.editTextImg.text.toString()

                            val book = Book(0,titulo, isbn, img)

                            viewModel.insert(book)

                            Snackbar.make(binding.root, "Livro cadastrado", Snackbar.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                            true
                        }

                        false
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

}
