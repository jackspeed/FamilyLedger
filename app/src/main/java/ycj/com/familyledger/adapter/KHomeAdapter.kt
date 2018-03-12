package ycj.com.familyledger.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import ycj.com.familyledger.R
import ycj.com.familyledger.bean.LedgerBean


/**
 * @author: ycj
 * @date: 2017-06-14 10:53
 * @version V1.0 <>
 */
class KHomeAdapter : RecyclerView.Adapter<KHomeAdapter.MyViewHolder> {
    private var mContext: Context
    private var dataList: ArrayList<LedgerBean>

    constructor(dataList: ArrayList<LedgerBean>, context: Context) {
        this.mContext = context
        this.dataList = dataList
    }


    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tvTitle.text = mContext.getString(R.string.date) + "  " + dataList[position].consume_date
        holder.tvContent.text = mContext.getString(R.string.cash) + "  " + dataList[position].consume_money
        holder.tvPhone.text = dataList[position].user_id.toString()

        holder.ly.setOnClickListener {
            if (listener != null) {
                listener!!.onItemClickListener(position)
            }
        }
        holder.ly.setOnLongClickListener({
            if (listener != null) {
                listener!!.onItemLongClickListener(position)
            }
            true
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView!!.find(R.id.t_title_home)
        var tvContent: TextView = itemView!!.find(R.id.t_content_home)
        var tvPhone: TextView = itemView!!.find(R.id.tv_phone_home)
        var ly: LinearLayout = itemView!!.find(R.id.ly_list_item_home)
        var fy: FrameLayout = itemView!!.find(R.id.fy_list_item_home)

    }


    private var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    interface ItemClickListener {
        fun onItemClickListener(position: Int)
        fun onItemLongClickListener(position: Int)
    }

    fun addNewItem(position: Int, data: LedgerBean) {
        if (position >= dataList.size) return
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun setDatas(data: List<LedgerBean>) {
        if (data.size == 0) return
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData() {
        dataList.clear()
        notifyDataSetChanged()
    }
}