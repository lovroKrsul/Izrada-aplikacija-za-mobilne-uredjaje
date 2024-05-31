package hr.algebra.Projectapp.adapter

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.Projectapp.PROVIDER_CONTENT_URI
import hr.algebra.Projectapp.R
import hr.algebra.Projectapp.model.Item

class ItemPagerAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemPagerAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRead=itemView.findViewById<ImageView>(R.id.ivRead)

        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvBody = itemView.findViewById<TextView>(R.id.tvBody)



        fun bind(item: Item) {

            tvTitle.text = item.title
            tvBody.text=item.body
            ivRead.setImageResource(if(item.read)
                R.drawable.red_flag
                else
                R.drawable.green_flag
            )}

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_pager, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ivRead.setOnClickListener {

            val item = items[position]
            item.read=!item.read
            context.contentResolver.update(
                ContentUris.withAppendedId(PROVIDER_CONTENT_URI, item._id!!),
                ContentValues().apply {
                    put(Item::read.name,item.read)
                },
                null,
                null
            )



            notifyItemChanged(position)


            true
        }


        holder.bind(items[position])
    }
}