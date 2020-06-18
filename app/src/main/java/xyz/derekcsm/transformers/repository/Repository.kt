package xyz.derekcsm.transformers.repository

import androidx.databinding.ObservableBoolean

interface Repository {

    // this override property is for saving network loading status.
    var isLoading: ObservableBoolean
}