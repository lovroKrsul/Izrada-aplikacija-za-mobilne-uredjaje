package hr.algebra.Projectapp.adapter

import android.content.ContentUris
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.Projectapp.ItemPagerActivity
import hr.algebra.Projectapp.PROVIDER_CONTENT_URI
import hr.algebra.Projectapp.POSITION
import hr.algebra.Projectapp.R
import hr.algebra.Projectapp.framework.startActivity
import hr.algebra.Projectapp.model.Item

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>(){
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)


        fun bind(item: Item) {

            tvTitle.text = item.title

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnLongClickListener {

            val item = items[position]
            context.contentResolver.delete(
                ContentUris.withAppendedId(PROVIDER_CONTENT_URI, item._id!!),
                null,
                null
            )

            items.removeAt(position)

            notifyDataSetChanged()


            true
        }
        holder.itemView.setOnClickListener {
            // edit
            context.startActivity<ItemPagerActivity>(POSITION,position)
        }

        holder.bind(items[position])
    }
}