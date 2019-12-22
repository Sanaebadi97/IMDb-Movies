package sanaebadi.info.movieapp.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import sanaebadi.info.movieapp.viewModel.DetailsViewModel

val viewModelModule: Module = module {
    viewModel { DetailsViewModel(get(), get()) }
}