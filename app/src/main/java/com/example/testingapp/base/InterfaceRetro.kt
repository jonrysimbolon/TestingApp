package com.example.testingapp.base

import retrofit.Callback
import retrofit.client.Response
import retrofit.http.*
import retrofit.mime.TypedFile

interface InterfaceRetro {

    @FormUrlEncoded
    @POST("/leasinglogin.php")
    fun leasinglogin(
        @Field("key") key: String,
        @Field("user") user: String,
        @Field("pass") pass: String,
        callback: Callback<Response>
    )

    @FormUrlEncoded
    @POST("/leasinglistpooling.php")
    fun leasinglistpooling(
        @Field("key") key: String,
        @Field("uuid_staff") uuid_staff: String,
        @Field("tgl_awal") tgl_awal: String,
        @Field("tgl_akhir") tgl_akhir: String,
        @Field("status") status: String,
        @Field("mds") mds: String,
        @Field("customer") customer: String,
        @Field("sort") sort: String,
        callback: Callback<Response>
    )

    @FormUrlEncoded
    @POST("/leasingdetailpooling.php")
    fun leasingdetailpooling(
        @Field("key") key: String,
        @Field("uuid_staff") uuid_staff: String,
        @Field("uuid_pooling") uuid_pooling: String,
        callback: Callback<Response>
    )

    @Multipart
    @POST("/pleditimage.php")
    fun pleditimage(
        @Part("key") key: String,
        @Part("uuid_staff") uuid_staff: String,
        @Part("uuid_pooling") uuid_pooling: String,
        @Part("jenis_image") jenis_image: String,
        @Part("image_edit") image_edit: TypedFile,
        callback: Callback<Response>
    )

    @Multipart
    @POST("/leasingtombolaksi.php")
    fun leasingtombolaksi(
        @Part("key") key: String,
        @Part("uuid_staff") uuid_staff: String,
        @Part("uuid_pooling") uuid_pooling: String,
        @Part("aksi") aksi: String,
        @Part("alasan") alasan: String,
        @Part("catatan") catatan: String,
        @Part("dp_gross") dp_gross: String,
        @Part("tenor") tenor: String,
        @Part("cicilan") cicilan: String,
        @Part("file_leasing_") file_leasing_: Any,
        callback: Callback<Response>
    )


    /*@FormUrlEncoded
    @POST("/leasingtombolaksi.php")
    fun leasingtombolaksi2(
        @Field("key") key: String,
        @Field("uuid_staff") uuid_staff: String,
        @Field("uuid_pooling") uuid_pooling: String,
        @Field("aksi") aksi: String,
        @Field("alasan") alasan: String,
        @Field("catatan") catatan: String,
        @Field("dp_gross") dp_gross: String,
        @Field("tenor") tenor: String,
        @Field("cicilan") cicilan: String,
        @Field("file_leasing_") file_leasing_: String,
        callback: Callback<Response>
    )*/
}
