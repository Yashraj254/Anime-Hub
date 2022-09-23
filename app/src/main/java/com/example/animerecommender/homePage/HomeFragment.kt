package com.example.animerecommender.homePage

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.animerecommender.R
import com.example.animerecommender.Utils
import com.example.animerecommender.adapters.AnimeAdapter
import com.example.animerecommender.adapters.AutoSlideAdapter
import com.example.animerecommender.adapters.GridViewAdapter
import com.example.animerecommender.data.AnimeItem
import com.example.animerecommender.data.CategoryGrid
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.home_fragment.*
import org.json.JSONArray
import org.json.JSONObject


class HomeFragment : Fragment(), AnimeAdapter.AnimeItemListener,AutoSlideAdapter.OnSlideClickListener,
    GridViewAdapter.CategoryGridListener {
    private val gridViewList = ArrayList<CategoryGrid>()
    val util = Utils()
    private lateinit var viewModel: HomeViewModel
    private lateinit var sliderView: SliderView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar1)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)

        sliderView = view.findViewById(R.id.sliderView)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION)
        sliderView.startAutoCycle()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        gridView.layoutManager =
            GridLayoutManager(requireContext(), 2)
        val slidesAdapter = AutoSlideAdapter(requireContext(),this)
        sliderView.sliderPager.adapter = slidesAdapter

        val adapter = AnimeAdapter(requireContext(), this)
        recyclerView.adapter = adapter

        val jsonObject = JSONObject(util.getAssetJsonData(requireContext()))
        val events: JSONArray = jsonObject.getJSONArray("data")

        for (j in 0 until events.length()) {

            val cit = events.getJSONObject(j)
            val image = cit.getJSONObject("posterImage")
            val genre = cit.getString("genre")
            val posterImage = image.getString("small")

            val grid = CategoryGrid(posterImage, genre)
            gridViewList.add(grid)
        }
        val gridViewAdapter = GridViewAdapter(gridViewList, requireContext(), this)
        gridView.adapter = gridViewAdapter
        viewModel.userLiveData?.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.updateList(it as ArrayList<AnimeItem>)
                slidesAdapter.updateList(it)
            }
        })
    }

    override fun onAnimeClicked(item: AnimeItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToAnimeInfoFragment(item)
        findNavController().navigate(action)
    }

    override fun onItemClicked(item: CategoryGrid) {
        val action = HomeFragmentDirections.actionHomeFragmentToCategorySearchFragment(item)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val search = menu.findItem(R.id.order_menu_search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(query!!)
                findNavController().navigate(action)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }



    override fun onSlideClick(item: AnimeItem) {
        val action = HomeFragmentDirections.actionHomeFragmentToAnimeInfoFragment(item)
        findNavController().navigate(action)    }
}

