package ycj.com.familyledger.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import org.jetbrains.anko.find
import ycj.com.familyledger.R
import ycj.com.familyledger.utils.ItemTouchCallBack


/**
 * @author: ycj
 * @date: 2017-06-14 10:53
 * @version V1.0 <>
 */
class KHomeAdapter : RecyclerView.Adapter<KHomeAdapter.MyViewHolder>, ItemTouchCallBack, View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN && dx > 0) {
            notifyDataSetChanged()
            return true
        }
        return false
    }

    var dx = 0
    override fun onScroll(dx: Int) {
        this.dx = dx
    }

    override fun onMove(fromPosition: Int, toPosition: Int) {
        TODO("移动拖拽返回处理")
    }

    override fun onSwiped(position: Int) {
//        dataList.removeAt(position)
//        notifyDataSetChanged()
    }

    private var mContext: Context
    private var dataList: ArrayList<String>

    constructor(dataList: ArrayList<String>, context: Context) {
        this.mContext = context
        this.dataList = dataList
    }


    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
        holder!!.tvContent.text = dataList[position]
        holder.tvName.text = dataList[position]
        holder.fy.setOnClickListener {
            listener!!.onItemDelClickListener(position)
        }
        holder.ly.setOnClickListener {
            Log.i("ycj*******", "dx:   " + dx)
            if (dx > 0) {
                notifyDataSetChanged()
            } else if (listener != null) {
                listener!!.onItemClickListener(position)
            }
        }
        holder.ly.setOnTouchListener(this)
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
        var fy: FrameLayout = itemView!!.find(R.id.fy_list_item_home)
    }


    private var listener: ItemClickListener? = null

    fun setOnItemClickListener(listener: ItemClickListener) {
        this.listener = listener
    }

    interface ItemClickListener {
        fun onItemClickListener(position: Int)
        fun onItemDelClickListener(position: Int)
    }

    fun addNewItem(position: Int) {
        if (position >= dataList.size) return
        dataList.add(position, "new Item")
        notifyItemInserted(position)
    }

    fun deleteItem(position: Int) {
        if (position >= dataList.size) return
        dataList.removeAt(position)
        notifyItemRemoved(position)
    }
}