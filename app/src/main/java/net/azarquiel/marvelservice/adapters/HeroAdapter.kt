package net.azarquiel.recyclerviewpajaros.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import net.azarquiel.marvelservice.R
import net.azarquiel.marvelservice.entities.Hero

/**
 * Created by pacopulido on 9/10/18.
 */
class HeroAdapter(val context: Context,
                  val layout: Int
) : RecyclerView.Adapter<HeroAdapter.ViewHolder>() {

    private var dataList: List<Hero> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    internal fun setHeroes(heroes: List<Hero>) {
        this.dataList = heroes
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Hero){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val ivrowhero = itemView.findViewById(R.id.ivhero) as ImageView
            val tvherorowhero = itemView.findViewById(R.id.tvhero) as TextView

            tvherorowhero.text = dataItem.name

            // foto de internet a traves de Picasso
            Picasso.get().load("${dataItem.thumbnail.path}/standard_fantastic.${dataItem.thumbnail.extension}").into(ivrowhero)


            itemView.tag = dataItem

        }

    }
}