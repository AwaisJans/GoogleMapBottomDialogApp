package com.jans.googlemap.cut.edge.app.adapters

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jans.googlemap.cut.edge.app.R
import com.jans.googlemap.cut.edge.app.model.urlDetailsMarker.Bild

class ImagesAdapter(private val imagesList: List<Bild>) :
            RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
                val holder: RecyclerView.ViewHolder

                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.images_item, parent, false)
                holder = ImageViewHolder(view)

                return holder
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                holder as ImageViewHolder

                val context = holder.itemView.context
                val item = imagesList[position]
                val url = item.url

                holder.imageName.text = item.bezeichnung
                Log.d("picture123",url)

                if(item.bezeichnung.equals("")){
                    holder.imageName.visibility = View.GONE
                }

                Glide.with(context)
                    .load(url)
                    .placeholder(R.drawable.loading)
                    .into((holder.imageHolder))



                val drawable = holder.imageHolder.drawable
                val imageHeight = getImageHeight(drawable)

                val maxSize = 28 // max font size
                val density = Resources.getSystem().displayMetrics.density
                val fontScale = Resources.getSystem().configuration.fontScale
                val fontSizeInDp = fontScale * density
                val newImageHeightInDp = fontSizeInDp * 2
                val newImageHeightInPixels = (newImageHeightInDp * density + 0.5f).toInt()
                val layoutParams = holder.imageHolder.layoutParams
                layoutParams.height = newImageHeightInPixels+imageHeight

                if(newImageHeightInPixels >= 20){
                    layoutParams.height = newImageHeightInPixels+imageHeight
                }
                else{
                    layoutParams.height = newImageHeightInPixels-imageHeight
                }

                holder.imageHolder.layoutParams = layoutParams

            }
            private fun getImageHeight(drawable: Drawable): Int {
                return drawable.intrinsicHeight
            }
            class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val imageHolder: ImageView = itemView.findViewById(R.id.imageViewer)
                val imageName: TextView = itemView.findViewById(R.id.imgName)
            }
            override fun getItemCount(): Int {
                return imagesList.size
            }
        }
