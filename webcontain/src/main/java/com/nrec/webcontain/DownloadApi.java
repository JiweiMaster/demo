package com.nrec.webcontain;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by 18099 on 2019/1/11.
 */

public interface DownloadApi {
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
