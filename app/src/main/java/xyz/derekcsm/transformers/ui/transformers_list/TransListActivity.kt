package xyz.derekcsm.transformers.ui.transformers_list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_trans_list.*
import kotlinx.android.synthetic.main.zero_state.*
import xyz.derekcsm.transformers.R
import xyz.derekcsm.transformers.model.Transformer
import xyz.derekcsm.transformers.ui.create_transformer.CreateTransformerActivity
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransListAdapter
import xyz.derekcsm.transformers.ui.transformers_list.adapter.TransListAdapterListener

@AndroidEntryPoint
class TransListActivity : AppCompatActivity(),
    TransListView, TransListAdapterListener, LifecycleObserver {

    private val TAG = "TransListActivity"
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

        swipe_refresh_layout.setOnRefreshListener {
            showLoading()
            reload()
        }

        fab.setOnClickListener {
            startActivity(CreateTransformerActivity.activityIntent(this, null))
        }

        transListAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkShowZeroState()
            }

            override fun onChanged() {
                super.onChanged()
                checkShowZeroState()
            }
        })
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

    override fun getItemCount(): Int {
        return transListAdapter.itemCount
    }

    override fun showLoading() {
        swipe_refresh_layout.isRefreshing = true
    }

    override fun hideLoading() {
        swipe_refresh_layout.isRefreshing = false
    }


    fun checkShowZeroState() {
        if (transListAdapter.itemCount > 0) {
            hideZeroState()
        } else {
            showZeroState()
        }
    }

    fun hideZeroState() {
        zero_state.visibility = View.GONE
    }

    fun showZeroState() {
        zero_state.visibility = View.VISIBLE
        tv_zero_state.text = getString(R.string.transformers_zero_state)
    }

    override fun getContext(): Context {
        return this
    }
}