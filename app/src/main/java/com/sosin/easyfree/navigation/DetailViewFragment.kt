package com.sosin.easyfree.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sosin.easyfree.R
import com.sosin.easyfree.navigation.model.ProductDTO
import kotlinx.android.synthetic.main.fragment_detail.*


class DetailViewFragment: Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_detail,container,false)

//        detailviewfragment_recyclerview.adapter = DetailViewRecyclerViewAdapter()
//        detailviewfragment_recyclerview.layoutManager = LinearLayoutManager(activity)

        return view
    }
//   Recycle View에 사용할 Data를 받는 부분
    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        var productDTOs : ArrayList<ProductDTO> = arrayListOf()
//        var contetntUidList : ArrayList<String> = arrayListOf()
//
//        데이터 받기
//        init {
//            firestore?.collection("images")?.addSnapshotListener { value, error ->
//                productDTOs.clear()
//                contetntUidList.clear()
//                for(snapshot in value!!.documents){
//                    var item = snapshot.toObject(ContentDTO::class.java)
//                    contentDTOs.add(item!!)
//                    contetntUidList.add(snapshot.id)
//                }
//                notifyDataSetChanged()
//            }
//        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

//        View Hodler 필요 갯수 Return
        override fun getItemCount(): Int {
            return productDTOs.size
        }

//        데이터 받아서 넣는 VIEW Holder에 넣는 부분
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as CustomViewHolder).itemView
//
//            //UserId
//            viewHolder.findViewById<TextView>(R.id.detailvieitem_profile_textview).text = contentDTOs!![position].userId
//
//            //Image
//            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUri).into(viewHolder.findViewById<ImageView>(R.id.detailviewitem_imageview_content))
//
//            // Explain
//            viewHolder.findViewById<TextView>(R.id.detailviewitem_explain_textview).text = contentDTOs!![position].explain
//
//            //likes
//            viewHolder.findViewById<TextView>(R.id.detailviewitem_favoritecounter_textview).text = "Likes " + contentDTOs!![position].favoriteCount
//
//            //Profile Image
//            Glide.with(holder.itemView.context).load(contentDTOs!![position].imageUri).into(viewHolder.findViewById<ImageView>(R.id.detailviewitem_profile_image))

        }
    }
}