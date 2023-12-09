package br.edu.ifsp.scl.library.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.library.R
import br.edu.ifsp.scl.library.adapter.BookAdapter
import br.edu.ifsp.scl.library.databinding.FragmentListBookBinding
import br.edu.ifsp.scl.library.viewmodel.BookViewModel

class ListBookFragment : Fragment(){

    private var _binding: FragmentListBookBinding? = null

    private val binding get() = _binding!!

    lateinit var bookAdapter: BookAdapter

    lateinit var viewModel: BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBookBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listBookFragment_to_registerFragment) }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        bookAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    private fun configureRecyclerView()
    {

        viewModel = ViewModelProvider(this).get(BookViewModel::class.java)

        viewModel.allBooks.observe(viewLifecycleOwner) { list ->
            list?.let {
                bookAdapter.updateList(list)
            }
        }

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter()
        recyclerView.adapter = bookAdapter

        val listener = object : BookAdapter.BookListener {
            override fun onItemClick(pos: Int) {
                val c = bookAdapter.bookListFilterable[pos]

                val bundle = Bundle()
                bundle.putInt("idBook", c.id)

                findNavController().navigate(
                    R.id.action_listBookFragment_to_detailsFragment,
                    bundle
                )

            }
        }
        bookAdapter.setClickListener(listener)
           }

}


