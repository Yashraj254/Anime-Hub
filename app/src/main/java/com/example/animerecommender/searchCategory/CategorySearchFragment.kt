package com.example.animerecommender.searchCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animerecommender.R
import com.example.animerecommender.adapters.CategorySearchAdapter
import com.example.animerecommender.data.AnimeItem
import com.example.animerecommender.databinding.CategorySearchFragmentBinding
import kotlinx.android.synthetic.main.category_search_fragment.*
import kotlinx.coroutines.launch


class CategorySearchFragment : Fragment(), CategorySearchAdapter.AnimeItemListener {


    lateinit var progressBar: ProgressBar
    lateinit var toolbar: Toolbar
    private val navigationArgs: CategorySearchFragmentArgs by navArgs()
    private var isScrolling = false
    private lateinit var viewModel: CategorySearchViewModel
    var counter = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.category_search_fragment, container, false)
        toolbar = view.findViewById(R.id.toolbar3)
        progressBar = view.findViewById(R.id.progressBar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategorySearchViewModel::class.java)

        val manager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerCategoryList.layoutManager = manager
        val adapter = CategorySearchAdapter(requireContext(), this)
        val animeCategory = navigationArgs.item.category

        recyclerCategoryList.adapter = adapter
        toolbar.title = animeCategory
        viewModel.userLiveData?.observe(viewLifecycleOwner,
             { list -> list.let { adapter.updateList(it as ArrayList<AnimeItem>) } })
        viewModel.getAnimeCategorizedData(animeCategory!!)
        recyclerCategoryList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems = manager.childCount
                val totalItems = manager.itemCount
                val scrollOutItems = manager.findFirstVisibleItemPosition()

                if (isScrolling && (currentItems + scrollOutItems >= totalItems)) {
                    isScrolling = false
                    counter += 10
                    fetchData(animeCategory, counter)
                }
            }
        })
    }

    override fun onItemClicked(item: AnimeItem) {
        val action =
            CategorySearchFragmentDirections.actionCategorySearchFragmentToAnimeInfoFragment(
                item)
        findNavController().navigate(action)
    }

    private fun fetchData(animeCategory: String, counter: Int) {
        progressBar.visibility = View.VISIBLE
        viewModel.viewModelScope.launch {
            viewModel.getNextCategorizedData(animeCategory, counter)
        }
        progressBar.visibility = View.GONE
    }
}