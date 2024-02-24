package com.jans.googlemap.cut.edge.app.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jans.googlemap.cut.edge.app.R
import com.jans.googlemap.cut.edge.app.adapters.ImagesAdapter
import com.jans.googlemap.cut.edge.app.databinding.BottomSheetBehaviorBinding
import com.jans.googlemap.cut.edge.app.model.urlDetailsMarker.SingleApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ModalBottomSheetDialog : BottomSheetDialogFragment() {

        private lateinit var binding: BottomSheetBehaviorBinding

        companion object {
            const val URL_DETAIL = "arg_text"
            var baseUrl = ""
            var endpoint = ""
            var urlString = ""
            var questionMarkIndex = 0


            fun newInstance(text: String): ModalBottomSheetDialog {
                val args = Bundle().apply {
                    putString(URL_DETAIL, text)
                }
                val fragment = ModalBottomSheetDialog()
                fragment.arguments = args
                return fragment
            }
        }


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = BottomSheetBehaviorBinding.inflate(
                inflater,
                container,
                false
            )
            urlString = arguments?.getString(URL_DETAIL).toString()

            questionMarkIndex = urlString.indexOf("?")
            if (questionMarkIndex != -1) {
                baseUrl = urlString.substring(0, questionMarkIndex)
                endpoint = urlString.substring(questionMarkIndex + 1)

            }

            val retrofit = Retrofit.Builder()
                .baseUrl("$baseUrl/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            val apiResponse = retrofit.create(SingleApiService::class.java)

            binding.idLoader.visibility = View.VISIBLE
            binding.nestScroll.visibility = View.GONE
            binding.expandButton.visibility = View.GONE



            lifecycleScope.launch {
                val singleItem = apiResponse.getData(urlString).singleItem
                val kategoriesList = singleItem.kategorien
                val title = singleItem.bezeichnung
                val htmlCode = singleItem.beschreibung
                val imagesList = singleItem.bilder

                binding.tvTitle.text = title
                binding.webView.loadData(htmlCode, "text/html", "UTF-8")

                binding.idLoader.visibility = View.GONE
                binding.nestScroll.visibility = View.VISIBLE
//                binding.expandButton.visibility = View.VISIBLE

                val containerKategories = binding.container

                val testItems = listOf("Category 1", "Category 2", "Category 3",
                    "Category 4","Category 2", "Category 3", "Category 4", "Category 5",
                    "Category 6", "Category 7", "Category 8", "Category 9", "Category 10")

                for (item in kategoriesList) {
                    val textView = TextView(requireContext())
                    textView.text = item.bezeichnung
                    textView.setTextColor(Color.WHITE)
                    textView.setBackgroundResource(R.drawable.bg_kategory)
                    val params = FlexboxLayout.LayoutParams(
                        FlexboxLayout.LayoutParams.WRAP_CONTENT,
                        FlexboxLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(10, 10, 0, 15)
                    textView.setPadding(10, 10, 10, 10)
                    textView.layoutParams = params
                    containerKategories.addView(textView)
                }

                // setup ImagesList
                binding.imagesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL,false)
                binding.imagesRecyclerView.adapter = ImagesAdapter(imagesList)


            }

            return binding.root
        }






        @SuppressLint("RestrictedApi")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            dialog?.setOnShowListener { it ->
                val d = it as BottomSheetDialog
                val bottomSheet =
                    d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                bottomSheet?.let {
                    val behavior = BottomSheetBehavior.from(it)


                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            return super.onCreateDialog(savedInstanceState)
        }
    }
