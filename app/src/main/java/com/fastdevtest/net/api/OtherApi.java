
package com.fastdevtest.net.api;

import retrofit2.Response;
import retrofit2.http.GET;

/**
 * Created by guojun on 16/4/22 17:04.
 */
public interface OtherApi {
    /**
     * 下载文件
     * 
     * @return
     */
    @GET("app/apk/dongqiudi_website.apk")
    Response downLoadFile();
}
