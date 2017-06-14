package ycj.com.familyledger.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import ycj.com.familyledger.R


/**
 * @author: ycj
 * @date: 2017-06-14 10:53
 * @version V1.0 <>
 */
class KHomeAdapter : RecyclerView.Adapter<KHomeAdapter.MyViewHolder> {
    private var mContext: Context
    private var dataList: ArrayList<String>

    constructor(dataList: ArrayList<String>, context: Context) {
        this.mContext = context
        this.dataList = dataList
    }


    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tvContent.text = dataList[position]
        holder.tvName.text = dataList[position]
        holder.ly.setOnClickListener { if (listener != null) listener!!.onItemClickListener(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView!!.find(R.id.t_title_home)
        var tvContent: TextView = itemView!!.find(R.id.t_content_home)
        var ly: LinearLayout = itemView!!.find(R.id.ly_list_item_home)

    }


    private var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener;
    }

    interface ItemClickListener {
        fun onItemClickListener(position: Int)
    }
}