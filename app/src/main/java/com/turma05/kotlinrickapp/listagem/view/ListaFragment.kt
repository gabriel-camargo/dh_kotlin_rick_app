package com.turma05.kotlinrickapp.listagem.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.turma05.kotlinrickapp.R
import com.turma05.kotlinrickapp.listagem.model.PersonagemModel
import com.turma05.kotlinrickapp.listagem.repository.PersonagemRepository
import com.turma05.kotlinrickapp.listagem.viewmodel.PersonagemViewModel

class ListaFragment : Fragment() {
    lateinit var _viewModel: PersonagemViewModel
    lateinit var _view: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view

        val lista = view.findViewById<RecyclerView>(R.id.lista)
        val manager = LinearLayoutManager(view.context)

        var listaDePersonagens = mutableListOf<PersonagemModel>()
        val listaAdapter = ListaAdapter(listaDePersonagens)

        lista.apply {
            setHasFixedSize(true)

            layoutManager = manager
            adapter = listaAdapter
        }

        _viewModel = ViewModelProvider(
            this,
            PersonagemViewModel.PersonagemViewModelFactory(PersonagemRepository())
        ).get(PersonagemViewModel::class.java)

        _viewModel.personagens.observe(this, Observer {
            showLoading(false)
            notfound(it.isNotEmpty())

            listaDePersonagens.clear()
            listaDePersonagens.addAll(it)

            listaAdapter.notifyDataSetChanged()
        })

        _viewModel.obterLista()
        showLoading(true)

        initSearch()
    }

    fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    fun notfound(notfound: Boolean) {
        if (notfound) {
            _view.findViewById<View>(R.id.notfoundLayout).visibility = View.GONE
        } else {
            _view.findViewById<View>(R.id.notfoundLayout).visibility = View.VISIBLE
        }
    }

    private fun initSearch() {
        _view.findViewById<SearchView>(R.id.searchView)
            .setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                _viewModel.buscar(p0)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if(p0.isNullOrBlank()) {
                    _viewModel.cancelarBusca()
                }
                return true
            }

        })
    }
}