package uz.gita.dima.firebasefirestorepractise.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.dima.firebasefirestorepractise.data.Image
import uz.gita.dima.firebasefirestorepractise.databinding.EachItemBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor() : ListAdapter<Image, ImagesAdapter.ImagesViewHolder>(
    DIFF_CALL_BACK
) {

    private var longClickItem: ((Image) -> Unit)? = null
    fun clickLong(l: (Image) -> Unit) {
        longClickItem = l
    }
    inner class ImagesViewHolder(var binding: EachItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(image: Image) {
//            with(binding) {
//                with(image.image) {
//                    Log.d("TTT","image uri -> ${image.image}")
//                    Picasso.get().load(this).into(imageView)
//                }
//            }

            Glide.with(itemView.context).load(image.image).into(binding.imageView)

            binding.imageView.setOnLongClickListener {
                longClickItem?.invoke(image)
                true
            }
        }
    }

    companion object {
        private val DIFF_CALL_BACK = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem.idDoc == newItem.idDoc
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val binding = EachItemBinding.inflate(LayoutInflater.from(parent.context) , parent , false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
//        with(holder.binding){
//            with(mList[position]){
//                Picasso.get().load(this).into(imageView)
//            }
//        }

        holder.bind(getItem(position))
    }
}