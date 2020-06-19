package xyz.derekcsm.transformers.ui.transformers_list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_trans_list.*
import kotlinx.android.synthetic.main.toolbar_button.*
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.ui.create_transformer.CreateTransformerActivity
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransListAdapter
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransListAdapterListener

@AndroidEntryPoint
class TransListActivity : AppCompatActivity(),
    TransListView, TransListAdapterListener {

    @VisibleForTesting
    val viewModel by viewModels<TransListViewModel>()

    private val transListAdapter = TransListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.connectViewInterface(this)
        setContentView(R.layout.activity_trans_list)

        rv_transformers.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = transListAdapter
        }

        viewModel.fetchTransformers()

        fab.setOnClickListener {
            startActivity(CreateTransformerActivity.activityIntent(this, null))
        }

        btn_toolbar.text = getString(R.string.fight)
        btn_toolbar.setOnClickListener {
            // todo
        }
    }

    /*
    TODO
    Fetch transformers from DB first
     */

    override fun populateList(transformersList: List<Transformer>) {
        transListAdapter.setItems(transformersList)
    }

    override fun onTransformerClicked(transformer: Transformer) {
        startActivity(CreateTransformerActivity.activityIntent(this, transformer.id))
    }
}