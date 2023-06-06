package com.example.test.net.bean

import rxhttp.wrapper.annotation.DefaultDomain


object Url {

    @JvmField
    @DefaultDomain //设置为默认域名
    var baseUrl = ""

    const val UPLOAD_URL = ""

    const val DOWNLOAD_URL = ""
}