package com.tagloy.tagbiz.utils;


import com.tagloy.tagbiz.models.NoticeBoard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by User on 3/24/2021.
 */

public interface ApiService {
   /* @POST("login")
    @FormUrlEncoded
    Call<login> loginData(@Field("email") String email_id,
                          @Field("password") String password,
                          @Field("organization_id") String org_id);


    @POST("feed/published")
    @FormUrlEncoded
    Call<Feed> FeedData(@Header("Authorization") String header,
                        @Field("page") String page,
                        @Field("venue_id") String org_id);

    @POST("highlight/Get")
    @FormUrlEncoded
    Call<Highlights> HighLightData(@Header("Authorization") String header,
                                   @Field("screen_type") String screen_type,
                                   @Field("organization_id") String org_id,
                                   @Field("Live") String live,
                                   @Field("Archived") String archive);

    @POST("pending/Get")
    @FormUrlEncoded
    Call<Published> PublishedData(@Header("Authorization") String header,
                                  @Field("screen_type") String screen_type,
                                  @Field("organization_id") String org_id,
                                  @Field("Live") String live,
                                  @Field("Archived") String archive);
*/

    @Headers({"Content-type: application/json", "Accept: */*"})
    @POST("venue/blackboard/update")
    Call<NoticeBoard> NoticeBoardData(@Header("Authorization") String header,
                                      @Body String bodystring);

    @FormUrlEncoded
    @POST("live/tv/blackboard")
    Call<NoticeBoard> getNoticeBoardData(@Header("Authorization") String header,
                                            @Field("venue_id") String org_id,
                                            @Field("timestamp") String screen_timeStamp);


}
