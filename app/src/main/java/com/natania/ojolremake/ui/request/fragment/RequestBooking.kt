package com.natania.ojolremake.ui.request.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.natania.ojolremake.R
import com.natania.ojolremake.model.Booking
import com.natania.ojolremake.ui.request.adapter.BookingAdapter
import com.natania.ojolremake.ui.request.detail.DetailRequest
import com.natania.ojolremake.utils.Constan
import kotlinx.android.synthetic.main.fragment_item_list.*
import org.jetbrains.anko.support.v4.startActivity
import java.lang.IllegalStateException

/**
 * A simple [Fragment] subclass.
 */
class RequestBooking : Fragment() {

    private var comlumnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        exPlore()
        return view
    }

    private fun exPlore(){
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference(Constan.tb_booking)
        val data = ArrayList<Booking>()
        val query = myRef.orderByChild("driver")
            .equalTo("")

        query.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (issue in snapshot.children){
                    val dataFirebase = issue.getValue(Booking::class.java)
                    val booking = Booking()
                    booking.tanggal = dataFirebase?.tanggal
                    booking.uid = dataFirebase?.uid
                    booking.lokasiAwal = dataFirebase?.lokasiAwal
                    booking.latAwal = dataFirebase?.latAwal
                    booking.lonAwal = dataFirebase?.lonAwal
                    booking.latTujuan = dataFirebase?.latTujuan
                    booking.lonTujuan = dataFirebase?.lonTujuan
                    booking.lokasiTujuan = dataFirebase?.lokasiTujuan
                    booking.jarak = dataFirebase?.jarak
                    booking.harga = dataFirebase?.harga
                    booking.status = dataFirebase?.status

                    data.add(booking)
                    showData(data)
                }
            }

        })
    }

    private fun showData(data: ArrayList<Booking>) {

        try {
            list.adapter = BookingAdapter(data, object
                : OnListFragmentInteractionListener{

                override fun onListFragmentInteraction(item: Booking?) {
                    startActivity<DetailRequest>(
                        Constan.booking to item!!,
                        Constan.status to 1
                    )
                }
            })
            list.layoutManager = LinearLayoutManager(context)
        }catch (e : IllegalStateException){

        }


    }

    //    dipanggil saat fragment tidak lagi terhubung
//    ke sebuah activity.
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

//    This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.

    interface OnListFragmentInteractionListener{
        fun onListFragmentInteraction(item: Booking?)
    }
}

