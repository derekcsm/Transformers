package xyz.derekcsm.transformers.ui.transformers_list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_trans_list.*
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.ui.create_transformer.CreateTransformerActivity
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransListAdapter
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransListAdapterListener

@AndroidEntryPoint
class TransListActivity : AppCompatActivity(),
    TransListView, TransListAdapterListener, LifecycleObserver {

    @VisibleForTesting
    val viewModel by viewModels<TransListViewModel>()

    private val transListAdapter = TransListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.connectViewInterface(this)
        setContentView(R.layout.activity_trans_list)
        lifecycle.addObserver(this)
        rv_transformers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transListAdapter
        }

        fab.setOnClickListener {
            startActivity(CreateTransformerActivity.activityIntent(this, null))
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun reload() {
        viewModel.fetchTransformers()
    }

    override fun populateList(transformersList: List<Transformer>) {
        transListAdapter.setItems(transformersList)
    }

    override fun onTransformerClicked(transformer: Transformer) {
        startActivity(CreateTransformerActivity.activityIntent(this, transformer.id))
    }

    override fun onDeleteTransformerClicked(transformer: Transformer) {
        transListAdapter.removeTransformer(transformer.id)
        viewModel.deleteTransformer(transformer.id)
    }
}