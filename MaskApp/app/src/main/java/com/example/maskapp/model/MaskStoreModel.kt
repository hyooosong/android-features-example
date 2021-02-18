package com.example.maskapp.model

import retrofit2.Call

data class MaskStoreModel(
    val count: Int,
    val stores: List<StoreModel>
)