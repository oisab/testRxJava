package com.oisab.testrxjava

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TestAdapter : RecyclerView.Adapter<TestAdapter.BaseViewHolder<*>>() {
    private val testData : MutableList<BaseCell> = ArrayList()

    companion object {
        private const val TYPE_CLASS =  1
        private const val TYPE_STUDENTS =  2
    }

    fun setData(items: MutableList<BaseCell>) {
        this.testData.clear()
        this.testData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when(viewType)
        {
            TYPE_CLASS -> ClassViewHolder(layoutInflater.inflate(R.layout.cell_class, parent, false))
            TYPE_STUDENTS -> StudentViewHolder(layoutInflater.inflate(R.layout.cell_student, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = testData[position]
        when(holder) {
            is ClassViewHolder -> holder.bind(element as CellClass)
            is StudentViewHolder -> holder.bind(element as CellStudent)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(testData[position]) {
            is CellClass -> TYPE_CLASS
            is CellStudent -> TYPE_STUDENTS
            else -> throw IllegalArgumentException("Invalid type of item $position")
        }
    }

    override fun getItemCount(): Int {
        return testData.size
    }

    abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: T)
    }

    class StudentViewHolder(view: View) : BaseViewHolder<CellStudent>(view) {
        private val testTextView = view.findViewById<TextView>(R.id.studentName)

        override fun bind(item: CellStudent) {
            testTextView.text = item.studentName
        }
    }

    class ClassViewHolder(view: View) : BaseViewHolder<CellClass>(view) {
        private val testTextView = view.findViewById<TextView>(R.id.classNumber)

        override fun bind(item: CellClass) {
            testTextView.text = item.classNumber
        }
    }
}