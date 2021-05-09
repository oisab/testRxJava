package com.oisab.testrxjava

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlin.collections.ArrayList

class TestFragment : Fragment(R.layout.test_fragment) {
    private val testAdapter = TestAdapter()
    private val disposeBag = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val classesRecyclerView: RecyclerView = view.findViewById(R.id.testRecyclerView)
        classesRecyclerView.adapter = testAdapter
        classesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        disposeBag.add(generateStudents()
            .zipWith(generateClasses(),
                { t, u ->
                    val items: MutableList<CellModel> = ArrayList()
                    for ((schoolClasses, students) in t) {                // проход по ключам ассоциативного массива
                        if (schoolClasses in u) {                        // если текущий ключ содержится в списке классов
                            items.add(CellModel(schoolClasses))         // добавить в список ключ - название класса
                            for (student in students) {                // добавить в список всех студентов класса
                                items.add(CellModel(student))
                            }
                        }
                    }
                    return@zipWith items
                })
            .subscribe({
                testAdapter.setData(it)
            }, {
                Log.e("myApp", "Error generating a list of students and classes")
            })
        )
        disposeBag.clear()
    }

    private fun generateClasses(): Single<ArrayList<String>> {
        val schoolClasses = arrayListOf(
            "11а", "11б", "10а", "10б", "9а", "9б", "9в", "8а",
            "8б", "8в", "7а", "7б", "7в", "6а", "6б", "6вб", "5а", "5б", "5в"
        )
        return Single.create { subscriber ->
            subscriber.onSuccess(schoolClasses)
        }
    }

    private fun generateStudents(): Single<Map<String, List<String>>> {
        val schoolStudents: Map<String, List<String>> = mapOf(
            "11а" to listOf("Аляутдин Роман", "Никитин Никита", "Терешин Вячеслав"),
            "11б" to listOf("Кутехов Дмитрий", "Низов Роман", "Гречкин Владислав"),
            "10а" to listOf("Пашенцев Сергей", "Гневашев Александр", "Чепец Павел"),
            "9а" to listOf("Горшков Владимир", "Корячко Владимир", "Аптеев Даниил"),
            "9б" to listOf("Артамонов Александр", "Брехов Олег", "Вестяк Анатолий"),
            "8а" to listOf("Петренко Юрий", "Трембач Михаил", "Давыдов Сергей"),
            "8б" to listOf("Гагарин Андрей", "Чугаев Борис", "Судаков Владимир"),
            "7а" to listOf("Волков Владимир", "Чебатко Марина", "Копылова Дарья"),
            "6а" to listOf("Аверьянова Екатерина", "Матафонова Марина", "Колчин Леонид"),
            "5а" to listOf("Сафронов Леонид", "Исаев Осман", "Мякишева Владислава"),
        )
        return Single.create { subscribe ->
            subscribe.onSuccess(schoolStudents)
        }
    }
}