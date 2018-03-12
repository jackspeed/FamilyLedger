package ycj.com.familyledger.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.jetbrains.anko.find
import ycj.com.familyledger.R
import ycj.com.familyledger.bean.CalculateBean


/**
 * @author: ycj
 * @date: 2017-06-14 10:53
 * @version V1.0 <>
 */
class KCalculateAdapter : RecyclerView.Adapter<KCalculateAdapter.MyViewHolder> {
    private var mContext: Context
    private var dataList: ArrayList<CalculateBean>

    constructor(dataList: ArrayList<CalculateBean>, context: Context) {
        this.mContext = context
        this.dataList = dataList
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tvName.text = dataList[position].userName
        holder.tvPhone.text = dataList[position].phoneNumber
        val value = dataList [position].valueMoney.toDouble()
        holder.tvResult.text = if (value >= 0) mContext.getString(R.string.income) + value + mContext.getString(R.string.money) else mContext.getString(R.string.pay) + value + mContext.getString(R.string.money)
        holder.tvExpend.text = mContext.getString(R.string.expend) + dataList[position].expendMoney + mContext.getString(R.string.money)
        holder.tvAverage.text = mContext.getString(R.string.average) + dataList[position].average + mContext.getString(R.string.money)
        holder.tvTotal.text = mContext.getString(R.string.total_cash) + dataList[position].totalMoney + mContext.getString(R.string.money)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_calculate, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView!!.find(R.id.tv_name_calcul)
        var tvPhone: TextView = itemView!!.find(R.id.tv_phone_calcul)
        var tvExpend: TextView = itemView!!.find(R.id.t_expend_calcul)
        var tvResult: TextView = itemView!!.find(R.id.t_value_calcul)
        var tvAverage: TextView = itemView!!.find(R.id.tv_average_calcul)
        var tvTotal: TextView = itemView!!.find(R.id.tv_total_calcul)
    }


    private var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    interface ItemClickListener {
        fun onItemClickListener(position: Int)
        fun onItemLongClickListener(position: Int)
    }

    fun addNewItem(position: Int, data: CalculateBean) {
        if (position >= dataList.size) return
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun setDatas(data: List<CalculateBean>) {
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