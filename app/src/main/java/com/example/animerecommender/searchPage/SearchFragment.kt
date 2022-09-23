package com.example.animerecommender.searchPage

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animerecommender.R
import com.example.animerecommender.adapters.CategorySearchAdapter
import com.example.animerecommender.data.AnimeItem
import kotlinx.android.synthetic.main.category_search_fragment.*

class SearchFragment : Fragment(), CategorySearchAdapter.AnimeItemListener {

    private val navigationArgs: SearchFragmentArgs by navArgs()


    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar2)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        // TODO: Use the ViewModel
        recyclerCategoryList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = CategorySearchAdapter(requireContext(), this)
        val animeTitle = navigationArgs.title
        recyclerCategoryList.adapter = adapter

        viewModel.userLiveData?.observe(viewLifecycleOwner,
             { list -> list.let { adapter.updateList(it as ArrayList<AnimeItem>) } })
        viewModel.getAnimeByTitle(animeTitle)
    }

    override fun onItemClicked(item: AnimeItem) {
        val action =
            SearchFragmentDirections.actionSearchFragmentToAnimeInfoFragment(
                item)
        findNavController().navigate(action)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.order_menu_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val action = SearchFragmentDirections.actionSearchFragmentSelf(query!!)
                findNavController().navigate(action)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}

