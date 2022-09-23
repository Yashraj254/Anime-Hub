package com.example.animerecommender.infoPage

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.animerecommender.R
import com.example.animerecommender.TrailerActivity
import com.google.android.material.shape.CornerFamily
import kotlinx.android.synthetic.main.anime_info_fragment.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class   AnimeInfoFragment : Fragment() {

    private val navigationArgs: AnimeInfoFragmentArgs by navArgs()
    private lateinit var viewModel: AnimeInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.anime_info_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(AnimeInfoViewModel::class.java)

        val item = navigationArgs.item
        // ImageHelper.getRoundedCornerBitmap(imageView2.bitmap,200)
        imageView2.shapeAppearanceModel.toBuilder().setBottomLeftCorner(CornerFamily.ROUNDED, 200f)
            .setBottomRightCorner(CornerFamily.ROUNDED, 200f).build()
        val image = item.imgUrl
        val videoId = item.videoId
        var ageRating = item.ratingGuide
        var ratings = "Not Available"
        if(item.avgRating!=null){
            ratings= item.avgRating.toString()
        }
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var date = LocalDate.parse(item.startedAt, formatter)

        if (ageRating.isNullOrEmpty()){
            ageRating = "NaN"
        }
        animeName.text = item.animeTitle
        country.text = "Age Rating:\n"+ageRating
        length.text = "Length\n" + item.epCount.toString()+"ep"
        description.text = item.animeDesc
        year.text = "Started at: ${date}\nEnded at: ${item.endedAt} "
        ratingsView.text = "Avg. Ratings: $ratings"

        try {
            ratingBar.rating = (ratings.toFloat() * 5) /100
        }catch (e: Exception){
            ratings = "Not Available"
        }

        Glide.with(requireContext()).load(image).into(imageView2)
        button.setOnClickListener {
            val intent = Intent(requireContext(),TrailerActivity::class.java)
            intent.putExtra("Url",videoId)
            if(!videoId.isNullOrEmpty())
            startActivity(intent)
            else{
                Toast.makeText(requireContext(),"Trailer not available.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
