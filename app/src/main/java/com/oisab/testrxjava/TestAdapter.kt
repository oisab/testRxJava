package com.oisab.testrxjava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {
    private val testData : MutableList<CellModel> = ArrayList()

    fun setData(items: MutableList<CellModel>) {
        this.testData.clear()
        this.testData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return TestViewHolder(layoutInflater.inflate(R.layout.cell_test, parent, false))
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(testData[position])
    }

    override fun getItemCount(): Int {
        return testData.size
    }

    class TestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val testTextView = view.findViewById<TextView>(R.id.testText)

        fun bind(testCell: CellModel) {
            testTextView.text = testCell.studentName
        }
    }
}